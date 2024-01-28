package net.goose.lifesteal.fabric.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.server.level.ServerPlayer;

public class ModEvents {
    public static void COPY_FROM(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
        HealthData.get(oldPlayer).ifPresent(oldData -> HealthData.get(newPlayer).ifPresent(newData ->
        {
            newData.setHealthDifference(oldData.getHealthDifference(false));
            newData.setWithdrawalAmount(oldData.getWithdrawalAmount());
            newData.refreshHearts(true);
        }));
    }

    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModEvents for " + LifeSteal.MOD_ID);
        ServerPlayerEvents.COPY_FROM.register(((oldPlayer, newPlayer, alive) -> COPY_FROM(oldPlayer, newPlayer, alive)));
    }
}
