package net.goose.lifesteal.mixin;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.api.PlayerImpl;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, priority = 1)
public abstract class PlayerMixin extends LivingEntity implements PlayerImpl {
    private boolean revived;
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    public void dropKilledHeartCrystal(LivingEntity killedPlayer) {
        ItemStack itemStack = new ItemStack(ModItems.HEART_CRYSTAL.get());
        CompoundTag compoundTag = itemStack.getOrCreateTagElement("lifesteal");
        compoundTag.putBoolean("dropped", true);
        compoundTag.putBoolean("Unfresh", true);
        itemStack.setHoverName(Component.translatable("item.lifesteal.heart_crystal.named", killedPlayer.getName().getString()));

        ServerPlayer serverPlayer = (ServerPlayer) killedPlayer;
        serverPlayer.drop(itemStack, true, false);
    }

    public void increaseHearts(HealthData data, int hitpoint, LivingEntity killedPlayer) {
        final int maximumhitpointsGainable = LifeSteal.config.maximumHealthGainable.get();
        boolean alreadyGiven = false;

        if (maximumhitpointsGainable > -1 && LifeSteal.config.playerDropsHeartCrystalWhenKillerHasMax.get() && !LifeSteal.config.playerDropsHeartCrystalWhenKilled.get()) {
            if (data.getHealthDifference(true) + hitpoint > maximumhitpointsGainable) {
                dropKilledHeartCrystal(killedPlayer);
                alreadyGiven = true;
            }
        }

        if (!alreadyGiven) {
            if (!LifeSteal.config.playerDropsHeartCrystalWhenKilled.get()) {
                data.setHealthDifference(data.getHealthDifference(false) + hitpoint);
                data.refreshHearts(false);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickMethod(final CallbackInfo info){
        HealthData.get(this).ifPresent(healthData -> {
            // Are we at the amount where player should be banned based on their stats?
            if(healthData.getHealthDifference(false) <= healthData.getHPDifferenceRequiredForBan()){
                healthData.banForDeath();
            }
        });
    }
    @Inject(method = "dropEquipment", at = @At("HEAD"))
    private void onDeath(final CallbackInfo info) {

        final int maximumheartsLoseable = LifeSteal.config.maximumHealthLoseable.get();
        final int startingHitPointDifference = LifeSteal.config.startingHealthDifference.get();
        final int amountOfHealthLostUponLossConfig = LifeSteal.config.amountOfHealthLostUponLoss.get();
        final boolean playersGainHeartsifKillednoHeart = LifeSteal.config.playersGainHeartsifKillednoHeart.get();
        final boolean disableLifesteal = LifeSteal.config.disableLifesteal.get();
        final boolean loseHeartsWhenKilledByPlayer = LifeSteal.config.loseHealthWhenKilledByPlayer.get();
        final boolean loseHeartsWhenKilledByMob = LifeSteal.config.loseHealthWhenKilledByMob.get();
        final boolean loseHeartsWhenKilledByEnvironment = LifeSteal.config.loseHealthWhenKilledByEnvironment.get();

        LivingEntity killedEntity = this;

        HealthData.get(killedEntity).ifPresent(healthData -> {
            if (killedEntity instanceof ServerPlayer) {
                if (!killedEntity.isAlive()) {
                    int HeartDifference = healthData.getHealthDifference(false);

                    LivingEntity killerEntity = killedEntity.getLastHurtByMob();
                    boolean killerEntityIsPlayer = killerEntity instanceof ServerPlayer;
                    ServerPlayer killerPlayer = killerEntityIsPlayer ? (ServerPlayer) killerEntity: null;

                    int amountOfHealthLostUponLoss;

                    if (maximumheartsLoseable < 0) {
                        if (20 + HeartDifference - amountOfHealthLostUponLossConfig >= 0 || playersGainHeartsifKillednoHeart) {
                            amountOfHealthLostUponLoss = amountOfHealthLostUponLossConfig;
                        } else {
                            amountOfHealthLostUponLoss = 20 + HeartDifference;
                        }
                    } else {
                        if (20 + HeartDifference - amountOfHealthLostUponLossConfig >= (20 + startingHitPointDifference) - maximumheartsLoseable || playersGainHeartsifKillednoHeart) {
                            amountOfHealthLostUponLoss = amountOfHealthLostUponLossConfig;
                        } else {
                            amountOfHealthLostUponLoss = HeartDifference + maximumheartsLoseable;
                        }
                    }

                    // THE CODE BELOW IS FOR INCREASING THE KILLER ENTITY HITPOINTDIFFERENCE IF THEY EXIST
                    if (killerEntity != null) { // IF THERE IS A KILLER ENTITY
                        if (killerEntity != killedEntity) { // IF IT'S NOT THEMSELVES (Shooting themselves with an arrow lol)
                            // EVERYTHING BELOW THIS COMMENT IS CODE FOR MAKING THE KILLER PERSON'S HEART DIFFERENCE GO UP.
                            if (killerEntityIsPlayer && !disableLifesteal) {
                                HealthData.get(killerEntity).ifPresent(killerData -> {
                                    if (playersGainHeartsifKillednoHeart) {
                                        increaseHearts(killerData, amountOfHealthLostUponLoss, killedEntity);
                                    } else {
                                        if (maximumheartsLoseable > -1) {
                                            if (startingHitPointDifference + HeartDifference > -maximumheartsLoseable) {
                                                increaseHearts(killerData, amountOfHealthLostUponLoss, killedEntity);
                                            } else {
                                                killerPlayer.sendSystemMessage(Component.translatable("chat.message.lifesteal.no_more_hearts_to_steal"));
                                            }

                                        } else {
                                            increaseHearts(killerData, amountOfHealthLostUponLoss, killedEntity);
                                        }
                                    }

                                });
                            }
                        }
                    }

                    // THE CODE BELOW IS FOR REDUCING THE KILLED ENTITY'S HITPOINTDIFFERENCE

                    if (loseHeartsWhenKilledByPlayer || loseHeartsWhenKilledByMob || loseHeartsWhenKilledByEnvironment) {
                        if (killerEntity != null) { // IF KILLER ENTITY EXISTS
                            if (killedEntity != killerEntity) { // IF KILLER ENTITY ISNT SELF/ IF A KILLER KILLED OUR GUY
                                if (killerEntityIsPlayer) { // IF THEY ARE A PLAYER
                                    if (!loseHeartsWhenKilledByPlayer) {
                                        return;
                                    }
                                } else if (!loseHeartsWhenKilledByMob) {
                                    return;
                                }
                            } else if (!loseHeartsWhenKilledByPlayer) {
                                return;
                            }
                        } else if (!loseHeartsWhenKilledByEnvironment) {
                            return;
                        }
                    } else {
                        return;
                    }

                    healthData.setHealthDifference(healthData.getHealthDifference(false) - amountOfHealthLostUponLoss);
                    healthData.refreshHearts(false);
                    if (LifeSteal.config.playerDropsHeartCrystalWhenKilled.get()) {
                        dropKilledHeartCrystal(killedEntity);
                    }
                }
            }
        });
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void addOurDataTooLol(CompoundTag compoundTag, final CallbackInfo info){
        compoundTag.putBoolean("Revived", this.getRevived());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void loadOurDataTooLol(CompoundTag compoundTag, final CallbackInfo info){
       this.setRevived(compoundTag.getBoolean("Revived"));
    }

    @Override
    public void setRevived(boolean bool) {
        this.revived = bool;
    }

    @Override
    public boolean getRevived() {
        return this.revived;
    }
}
