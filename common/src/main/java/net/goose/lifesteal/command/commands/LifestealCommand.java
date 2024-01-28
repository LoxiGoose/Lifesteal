package net.goose.lifesteal.command.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.advancements.Advancement;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class LifestealCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("ls")
                        .then(Commands.literal("withdraw")
                                .requires((commandSource) -> commandSource.hasPermission(LifeSteal.config.permissionLevelForWithdraw.get()))
                                .executes((command) -> withdraw(command.getSource(), 1))
                                .then(Commands.argument("Amount", IntegerArgumentType.integer(1))
                                        .executes((command) -> withdraw(command.getSource(), IntegerArgumentType.getInteger(command, "Amount")))))
                        .then(Commands.literal("get-hitpoints")
                                .requires((commandSource) -> commandSource.hasPermission(LifeSteal.config.permissionLevelForGettingHitPoints.get()))
                                .executes((command) -> getHitPoint(command.getSource()))
                                .then(Commands.argument("Player(s)", EntityArgument.players())
                                        .executes((command) -> getHitPoint(command.getSource(), EntityArgument.getPlayers(command, "Player"))))
                        )
                        .then(Commands.literal("set-hitpoints")
                                .requires((commandSource) -> commandSource.hasPermission(LifeSteal.config.permissionLevelForSettingHitPoints.get()))
                                .then(Commands.argument("Amount", IntegerArgumentType.integer())
                                        .executes((command) -> setHitPoint(command.getSource(), IntegerArgumentType.getInteger(command, "Amount"))))
                                .then(Commands.argument("Player(s)", EntityArgument.players())
                                        .then(Commands.argument("Amount", IntegerArgumentType.integer())
                                                .executes((command) -> setHitPoint(command.getSource(), EntityArgument.getPlayers(command, "Player"), IntegerArgumentType.getInteger(command, "Amount")))))));
    }

    private static int withdraw(CommandSourceStack source, int amount) throws CommandSyntaxException {
        ServerPlayer serverPlayer = source.getPlayerOrException();

        String advancementUsed = (String) LifeSteal.config.advancementUsedForWithdrawing.get();
        if (serverPlayer.getAdvancements().getOrStartProgress(Advancement.Builder.advancement().build(new ResourceLocation(advancementUsed))).isDone() || advancementUsed.isEmpty() || serverPlayer.isCreative()) {
            HealthData.get(serverPlayer).ifPresent(healthData -> healthData.withdrawHearts(amount));
        } else {
            String text = (String) LifeSteal.config.textUsedForRequirementOnWithdrawing.get();
            if (!text.isEmpty()) {
                serverPlayer.displayClientMessage(Component.literal((String) LifeSteal.config.textUsedForRequirementOnWithdrawing.get()), true);
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int getHitPoint(CommandSourceStack source) throws CommandSyntaxException {
        LivingEntity playerthatsentcommand = source.getPlayerOrException();
        HealthData.get(playerthatsentcommand).ifPresent(iHeartData -> source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.get_hit_point_for_self", iHeartData.getHealthDifference(false)), false));
        return Command.SINGLE_SUCCESS;
    }

    private static int getHitPoint(CommandSourceStack source, Collection<ServerPlayer> serverPlayers) throws CommandSyntaxException {
        serverPlayers.forEach(serverPlayer ->
            HealthData.get(serverPlayer).ifPresent(iHeartData ->
                    source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.get_hit_point_for_player", serverPlayer.getName().getString(), iHeartData.getHealthDifference(false)), false)
            )
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int setHitPoint(CommandSourceStack source, int amount) throws CommandSyntaxException {
        LivingEntity playerthatsentcommand = (LivingEntity) source.getEntityOrException();
        HealthData.get(playerthatsentcommand).ifPresent(IHeartCap -> {
            IHeartCap.setHealthDifference(amount);
            IHeartCap.refreshHearts(false);
        });

        source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.set_hit_point_for_self", amount), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int setHitPoint(CommandSourceStack source, Collection<ServerPlayer> serverPlayers, int amount) throws CommandSyntaxException {
        serverPlayers.forEach(serverPlayer -> {
            HealthData.get(serverPlayer).ifPresent(IHeartCap -> {
                IHeartCap.setHealthDifference(amount);
                IHeartCap.refreshHearts(false);
            });

            source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.set_hit_point_for_player", serverPlayer.getName().getString(), amount), true);

            if (LifeSteal.config.tellPlayersIfHitPointChanged.get()) {
                serverPlayer.sendSystemMessage(Component.translatable("chat.message.lifesteal.set_hit_point_for_self", amount));
            }
        });

        return Command.SINGLE_SUCCESS;
    }
}
