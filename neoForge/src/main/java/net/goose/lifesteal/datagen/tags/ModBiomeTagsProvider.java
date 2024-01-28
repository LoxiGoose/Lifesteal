package net.goose.lifesteal.datagen.tags;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider {
    public ModBiomeTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, LifeSteal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.OVERWORLD_ORE).addTags(BiomeTags.IS_OVERWORLD, Tags.Biomes.IS_UNDERGROUND);
        tag(ModTags.NETHER_ORE).addTags(BiomeTags.IS_NETHER, Tags.Biomes.IS_DRY_NETHER, Tags.Biomes.IS_HOT_NETHER);

        tag(ModTags.BASE_MINERS_LOCATION).add(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.SNOWY_PLAINS, Biomes.MEADOW);

        tag(ModTags.MINERS_LOCATION_1).add(Biomes.FOREST);
        tag(ModTags.MINERS_LOCATION_1).addTag(ModTags.BASE_MINERS_LOCATION);

        tag(ModTags.MINERS_LOCATION_2).add(Biomes.TAIGA);
        tag(ModTags.MINERS_LOCATION_2).addTag(ModTags.BASE_MINERS_LOCATION);

        tag(ModTags.MINERS_LOCATION_3).addTags(ModTags.BASE_MINERS_LOCATION, ModTags.MINERS_LOCATION_2, ModTags.MINERS_LOCATION_1);

        tag(ModTags.MINERS_LOCATION_4).add(Biomes.TAIGA, Biomes.DARK_FOREST);

    }
}
