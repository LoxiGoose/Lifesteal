package net.goose.lifesteal.data.neoforge;

import net.goose.lifesteal.api.IHealthData;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;

public class HealthDataImpl {

    public static Optional<IHealthData> get(final LivingEntity entity) {
        return Optional.ofNullable(entity.getCapability(ModCapabilities.HEALTH_CAP));
    }

    public static Optional<IHealthData> get(final Entity entity) {
        return Optional.ofNullable(entity.getCapability(ModCapabilities.HEALTH_CAP));
    }

    public static int getHealthDifference(HealthData healthData) {
        return healthData.getLivingEntity().getData(ModDataAttachments.HEALTH_DATA.get());
    }

    public static void setHealthDifference(HealthData healthData, int health) {
        healthData.getLivingEntity().setData(ModDataAttachments.HEALTH_DATA.get(), health);
    }

}