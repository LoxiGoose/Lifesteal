package net.goose.lifesteal.data.fabric;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.goose.lifesteal.LifeSteal;
import net.minecraft.resources.ResourceLocation;

public class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<HealthDataImpl> HEALTH_DATA =
            ComponentRegistryV3.INSTANCE.getOrCreate(new ResourceLocation(LifeSteal.MOD_ID, "healthdata"), HealthDataImpl.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        // Add the component to every PlayerEntity instance
        registry.registerForPlayers(HEALTH_DATA, HealthDataImpl::new, RespawnCopyStrategy.NEVER_COPY);
    }
}