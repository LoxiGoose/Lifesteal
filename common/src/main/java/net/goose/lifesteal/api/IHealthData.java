package net.goose.lifesteal.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;


public interface IHealthData {
    void revivedTeleport();
    boolean withdrawHearts(int heartCount);
    BlockPos spawnPlayerHead();
    boolean dropPlayerHead();
    LivingEntity getLivingEntity();
    double getHealthModifiedTotal(boolean includeHealthDifference);
    double getHPDifferenceRequiredForBan();
    double getAmountOfHealthCanLose();
    void banForDeath();
    int getHealthDifference(boolean excludeStartingHealthDifference);
    void setHealthDifference(int hearts);
    void refreshHearts(boolean healtoMax);
}


