package net.goose.lifesteal.data.fabric;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;
import java.util.Optional;

public class HealthDataImpl extends HealthData implements ComponentV3 {
    private int healthDifference = LifeSteal.config.startingHealthDifference.get();
    public HealthDataImpl(LivingEntity livingEntity) {
        super(livingEntity);
    }

    public static Optional<HealthData> get(LivingEntity livingEntity) {
        try {
            return Optional.of(ModComponents.HEALTH_DATA.get(livingEntity));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Optional<HealthData> get(Entity entity) {
        try {
            return Optional.of(ModComponents.HEALTH_DATA.get(entity));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static int getHealthDifference(HealthData healthData) {
        return ((HealthDataImpl) healthData).healthDifference;
    }

    public static void setHealthDifference(HealthData healthData, int health) {
        ((HealthDataImpl) healthData).healthDifference = health;
    }
    @Override
    public void readFromNbt(CompoundTag tag) {
        setHealthDifference(tag.getInt("heartdifference"));
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putInt("heartdifference", getHealthDifference(false));
        for (String key : tag.getAllKeys()) {
            tag.put(key, Objects.requireNonNull(tag.get(key)));
        }
    }
}