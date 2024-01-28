package net.goose.lifesteal.util;

import net.goose.lifesteal.LifeSteal;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ModTags {
    public static TagKey<Biome> OVERWORLD_ORE = makeBiome(LifeSteal.MOD_ID, "overworld_ore");
    public static TagKey<Biome> NETHER_ORE = makeBiome(LifeSteal.MOD_ID, "nether_ore");
    public static TagKey<Biome> BASE_MINERS_LOCATION = makeBiome(LifeSteal.MOD_ID, "has_structure/base_miners_location");
    public static TagKey<Biome> MINERS_LOCATION_1 = makeBiome(LifeSteal.MOD_ID, "has_structure/miners_location_1");
    public static TagKey<Biome> MINERS_LOCATION_2 = makeBiome(LifeSteal.MOD_ID, "has_structure/miners_location_2");
    public static TagKey<Biome> MINERS_LOCATION_3 = makeBiome(LifeSteal.MOD_ID, "has_structure/miners_location_3");
    public static TagKey<Biome> MINERS_LOCATION_4 = makeBiome(LifeSteal.MOD_ID, "has_structure/miners_location_4");
    public static TagKey<Item> ORIGIN_IGNORE_DIET = makeItem("origins", "ignore_diet");

    private static TagKey<Item> makeItem(String domain, String path) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(domain, path));
    }

    private static TagKey<Block> makeBlock(String domain, String path) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(domain, path));
    }

    private static TagKey<EntityType<?>> makeEntityType(String domain, String path) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(domain, path));
    }

    private static TagKey<Structure> makeStructure(String domain, String path) {
        return TagKey.create(Registries.STRUCTURE, new ResourceLocation(domain, path));
    }

    private static TagKey<Biome> makeBiome(String domain, String path) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(domain, path));
    }
}
