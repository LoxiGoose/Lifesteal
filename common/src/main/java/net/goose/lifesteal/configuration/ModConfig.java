package net.goose.lifesteal.configuration;

import net.minecraft.commands.Commands;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

public class ModConfig {

    public final ModConfigSpec.IntValue startingHealthDifference;
    public final ModConfigSpec.BooleanValue shouldAllMobsGiveHearts;
    public final ModConfigSpec.BooleanValue loseHealthWhenKilledByPlayer;
    public final ModConfigSpec.BooleanValue loseHealthWhenKilledByMob;
    public final ModConfigSpec.BooleanValue loseHealthWhenKilledByEnvironment;
    public final ModConfigSpec.BooleanValue onlyLoseHealthGained;
    public final ModConfigSpec.IntValue amountOfHealthLostUponLoss;
    public final ModConfigSpec.IntValue maximumHealthGainable;
    public final ModConfigSpec.IntValue maximumHealthLoseable;
    public final ModConfigSpec.BooleanValue disableLifesteal;
    public final ModConfigSpec.BooleanValue preventFromUsingCrystalIfMax;
    public final ModConfigSpec.BooleanValue preventFromUsingCoreIfMax;
    public final ModConfigSpec.BooleanValue disableHeartCrystals;
    public final ModConfigSpec.BooleanValue disableUnnaturalHeartCrystals;
    public final ModConfigSpec.BooleanValue disableHeartCores;
    public final ModConfigSpec.BooleanValue disableReviveCrystals;
    public final ModConfigSpec.BooleanValue playersGainHeartsifKillednoHeart;
    public final ModConfigSpec.IntValue heartCrystalAmountGain;
    public final ModConfigSpec.BooleanValue crystalInstantUse;
    public final ModConfigSpec.DoubleValue heartCoreHeal;
    public final ModConfigSpec.BooleanValue coreInstantUse;
    public final ModConfigSpec.BooleanValue disableStatusEffects;
    public final ModConfigSpec.BooleanValue disableLightningEffect;
    public final ModConfigSpec.BooleanValue customHeartDifferenceWhenRevived;
    public final ModConfigSpec.IntValue startingHeartDifferenceFromCrystal;
    public final ModConfigSpec.BooleanValue tellPlayersIfHitPointChanged;
    public final ModConfigSpec.ConfigValue advancementUsedForWithdrawing;
    public final ModConfigSpec.ConfigValue textUsedForRequirementOnWithdrawing;
    public final ModConfigSpec.BooleanValue tellPlayersIfReachedMaxHealth;
    public final ModConfigSpec.BooleanValue silentlyRevivePlayer;
    public final ModConfigSpec.BooleanValue playersSpawnHeadUponDeath;
    public final ModConfigSpec.BooleanValue playerDropsHeartCrystalWhenKilled;
    public final ModConfigSpec.BooleanValue playerDropsHeartCrystalWhenKillerHasMax;
    public final ModConfigSpec.BooleanValue uponDeathBanned;
    public final ModConfigSpec.IntValue permissionLevelForWithdraw;
    public final ModConfigSpec.IntValue permissionLevelForSettingHitPoints;
    public final ModConfigSpec.IntValue permissionLevelForGettingHitPoints;
    public final ModConfigSpec.BooleanValue unbreakableReviveHeads;

    public ModConfig(final ModConfigSpec.Builder builder) {
        builder.comment("IMPORTANT NOTE: 2 Hitpoints = 1 Heart");
        builder.comment("We recommend editing the config BEFORE starting Minecraft. Most configs will work fine, even if changed in-game, but some require restarting Minecraft to work correctly.");
        builder.comment("This category holds general values that most people will want to change.");
        builder.push("General Settings");
        this.startingHealthDifference = buildInt(builder, "Starting additional Hitpoints:", 0, -19, Integer.MAX_VALUE, "Determines the number of additional Hitpoints beyond the 20 you should start with.");
        this.amountOfHealthLostUponLoss = buildInt(builder, "Number of HitPoints lost/given upon death/kill:", 2, 1, Integer.MAX_VALUE, "How many Hitpoints are lost/gained upon death or being killed.");
        this.playersSpawnHeadUponDeath = buildBoolean(builder, "Spawn Revive Head upon player elimination:", true, "Determines if Revive Heads should spawn upon a player being eliminated (no more hearts). NOTE: Always false in Singleplayer");
        this.uponDeathBanned = buildBoolean(builder, "Ban players upon losing all hearts:", true, "Determines if players who have lost all hearts get banned, or simply go into Spectator mode. Singleplayer will always go into Spectator");
        this.playerDropsHeartCrystalWhenKilled = buildBoolean(builder, "Players drop a Heart Crystal on death:", false, "Determines if players should drop a heart crystal upon death.");
        builder.pop();

        builder.comment("This category is for how players should lose hearts. If you want players to not be able to lose hearts at all, disable all the config options below.");
        builder.comment("Settings for when/how hearts are lost");
        builder.push("Losing Hearts");
        this.loseHealthWhenKilledByPlayer = buildBoolean(builder, "Lose hitpoint(s) when killed by a player:", true, "Determines if players should lose hearts when killed by another player.");
        this.loseHealthWhenKilledByMob = buildBoolean(builder, "Lose hitpoint(s) when killed by a mob:", true, "Determines if players should lose hearts when killed by mobs");
        this.loseHealthWhenKilledByEnvironment = buildBoolean(builder, "Lose hitpoint(s) when killed by the environment:", true, "Determines if players lose hearts when killed by the environment. (Lava, fall damage, etc)");
        this.onlyLoseHealthGained = buildBoolean(builder, "Lose hitpoint(s) gained only:", false, "Determines if a player should lose hearts past the amount they gained.");
        builder.pop();

        builder.comment("Settings for Items and Blocks");
        builder.push("Items and Blocks");
        builder.push("Heart Cores");
        this.disableHeartCores = buildBoolean(builder, "Disable Heart Cores:", false, "Determines if Heart Cores are disabled.");
        this.heartCoreHeal = buildDouble(builder, "Percentage of max health recovered from Heart Cores", 0.33, 0.01, 1, "The percentage of health recovered when a Heart Core is used.");
        this.preventFromUsingCoreIfMax = buildBoolean(builder, "Prevent players at max hp from using Heart Cores:", true, "Determines if Heart Cores can be used by players already at maximum health");
        this.coreInstantUse = buildBoolean(builder, "Instantly use Heart Cores:", false, "Determines if heart cores should be used instantly, or eaten.");
        builder.pop();
        builder.push("Heart Crystals");
        this.disableHeartCrystals = buildBoolean(builder, "Disable Heart Crystals:", false, "Determines if Natural Heart Cores should be disabled. (Doesn't affect Unnatural Heart Cores)");
        this.disableUnnaturalHeartCrystals = buildBoolean(builder, "Disable Unnatural Heart Crystals:", false, "Determines if Unnatural Heart Cores should be disabled. (Doesn't affect Natural Heart Cores)");
        this.heartCrystalAmountGain = buildInt(builder, "Number of hitpoint(s) Heart Crystal(s) Permanently Give:", 2, 1, Integer.MAX_VALUE, "Determines how many Hitpoints are given when a Heart Crystal is used.");
        this.preventFromUsingCrystalIfMax = buildBoolean(builder, "Prevent players at max Hitpoints from using Heart Crystals:", true, "Determines if Heart Crystals can be used by players at maximum health (if a max is set)");
        this.crystalInstantUse = buildBoolean(builder, "Instantly use Heart Crystals:", false, "Determines if Heart Cores should be used instantly or eaten.");
        builder.pop();
        builder.push("Revive Crystals");
        this.disableReviveCrystals = buildBoolean(builder, "Disable Revive Crystals:", false, "Determines if Revive Crystals should be disabled. (Always disabled in Singleplayer)");
        this.silentlyRevivePlayer = buildBoolean(builder, "Silently Revive Players:", false, "Determines if a chat message should NOT be sent when a player is revived.");
        this.disableLightningEffect = buildBoolean(builder, "Disable Lightning Effect:", false, "Determines if lightning should be summoned upon player revive.");
        this.disableStatusEffects = buildBoolean(builder, "Disable Status Effects:", false, "Determines if revived players receiving status effects should be disabled. (Leave enabled if you plan to keep the lightning effect)");
        this.customHeartDifferenceWhenRevived = buildBoolean(builder, "Use custom additional Hitpoint value:", false, "Determines if the custom Hitpoint value should be used for revived players.");
        this.startingHeartDifferenceFromCrystal = buildInt(builder, "Number of additional Hitpoints upon revival:", 0, -19, Integer.MAX_VALUE, "Determines the number of additional Hitpoints revived players should have. (2 Hitpoints = 1 Heart, use a negative number to start them with <20)");
        builder.pop();
        builder.push("Revive Heads");
        this.unbreakableReviveHeads = buildBoolean(builder, "Indestructible Revive Heads:", false, "Determines if Revive Heads should be indestructible. WARNING: This setting is highly destructive, and makes Revive Heads truly un-removable, even with /setblock and creative mode!");

        builder.pop();
        builder.pop();
        builder.comment("Lifesteal Settings");
        builder.push("Lifesteal Related");
        this.disableLifesteal = buildBoolean(builder, "Disable Lifesteal:", false, "Disables gaining hearts from killing players. (Does not affect losing hearts)");
        this.playersGainHeartsifKillednoHeart = buildBoolean(builder, "Players gain Hearts from killing players at minimum HP:", false, "Determines if killing a player with the minimum hp will still give heart(s)");

        builder.pop();
        builder.comment("Contains various maximum values.");
        builder.push("Maximums");
        this.maximumHealthGainable = buildInt(builder, "Maximum number of additional Hitpoints:", -1, -1, Integer.MAX_VALUE, "Determines the maximum Hitpoints beyond 20. (Remember, 2 Hitpoints = 1 Heart.) Set to -1 to disable.");
        this.maximumHealthLoseable = buildInt(builder, "Maximum number of Hitpoints you can Lose:", -1, -1, Integer.MAX_VALUE, "Determines the maximum number of Hitpoints a player can lose before being eliminated. Set to -1 to disable.");
        this.tellPlayersIfReachedMaxHealth = buildBoolean(builder, "Notify players if they have max HP:", true, "Determines if players attempting to use a Heart Crystal should be notified if they are already at the maximum.");
        this.playerDropsHeartCrystalWhenKillerHasMax = buildBoolean(builder, "Players drop a Heart Crystal when killer has max HP", false, "Determines players should drop a Heart Crystal even if the killer has the maximum HP. NOTE: This requires Maximum Hitpoints to be enabled.");
        builder.pop();
        builder.comment("Settings related to commands. Permission Levels range from 0 to 4, 0: Everyone, 1: Moderators, 2: Gamemasters, 3: Admins, 4: Owners");
        builder.push("Commands");
        this.tellPlayersIfHitPointChanged = buildBoolean(builder, "Notify players if their HP is changed:", true, "Notify players when their HP has been changed by an admin.");
        builder.push("Withdrawing");
        this.advancementUsedForWithdrawing = buildString(builder, "Advancement needed to unlock Withdrawing:", "lifesteal:get_heart_crystal", "Determines which achievement must be obtained before the player may use the withdraw command. Leave the quotations empty to have the feature unlocked by default. You can use /advancement to figure out advancement IDs.");
        this.textUsedForRequirementOnWithdrawing = buildString(builder, "Text to display if Withdrawing isn't unlocked:", "You need to at least have gotten one heart crystal in this world to withdraw", "Determines the message sent to players who try using the withdraw command before they have unlocked it.");
        this.permissionLevelForWithdraw = buildInt(builder, "Permission Level:", Commands.LEVEL_ALL, Commands.LEVEL_ALL, Commands.LEVEL_OWNERS, null);
        builder.pop();
        builder.push("Set-Hitpoints");
        this.permissionLevelForSettingHitPoints = buildInt(builder, "Permission Level:", Commands.LEVEL_GAMEMASTERS, Commands.LEVEL_ALL, Commands.LEVEL_OWNERS, null);
        builder.pop();
        builder.push("Get-Hitpoints");
        this.permissionLevelForGettingHitPoints = buildInt(builder, "Permission Level:", Commands.LEVEL_GAMEMASTERS, Commands.LEVEL_ALL, Commands.LEVEL_OWNERS, null);
        builder.pop();

        builder.pop();
        builder.comment("Settings which don't fit anywhere else, or add non-standard gameplay mechanics");
        builder.push("Misc/Fun");
        this.shouldAllMobsGiveHearts = buildBoolean(builder, "Killing mobs gives hearts:", false, "Determines if killing mobs will give Hearts.");
        builder.pop();
    }

    private static ModConfigSpec.IntValue buildInt(ModConfigSpec.Builder builder, String name, int defaultValue, int min, int max, @Nullable String comment) {
        return comment == null ? builder.translation(name).defineInRange(name, defaultValue, min, max) : builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }

    private static ModConfigSpec.DoubleValue buildDouble(ModConfigSpec.Builder builder, String name, double defaultValue, double min, double max, String comment) {
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }

    private static ModConfigSpec.ConfigValue buildString(ModConfigSpec.Builder builder, String name, String defaultValue, String comment) {
        return builder.comment(comment).translation(name).define(name, defaultValue);
    }

    private static ModConfigSpec.BooleanValue buildBoolean(ModConfigSpec.Builder builder, String name, boolean defaultValue, String comment) {
        return builder.comment(comment).translation(name).define(name, defaultValue);
    }

    private static ModConfigSpec.EnumValue buildEnum(ModConfigSpec.Builder builder, String name, Enum defaultValue, String comment) {
        return builder.comment(comment).translation(name).defineEnum(name, defaultValue);
    }

}