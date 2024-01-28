package net.goose.lifesteal.datagen.tags;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, LifeSteal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.HEART_ORE.get(), ModBlocks.DEEPSLATE_HEART_ORE.get(), ModBlocks.NETHERRACK_HEART_ORE.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.HEART_ORE.get(), ModBlocks.DEEPSLATE_HEART_ORE.get());
    }
}
