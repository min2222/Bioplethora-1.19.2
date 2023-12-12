package io.github.bioplethora.world.features.treefeatures;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import io.github.bioplethora.world.features.NBTTreeFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CaerulwoodTreeFeature extends NBTTreeFeature {

    public CaerulwoodTreeFeature(Codec<NoneFeatureConfiguration> config) {
        super(config);
    }

    @Override
    public ImmutableList<String> getPossibleNBTTrees() {
        return ImmutableList.of(
                "caerulwood_tree_small_1",
                "caerulwood_tree_small_2",
                "caerulwood_tree_small_3",
                "caerulwood_tree_medium_1",
                "caerulwood_tree_medium_2",
                "caerulwood_tree_large_1",
                "caerulwood_tree_large_2",
                "caerulwood_tree_large_3",
                "caerulwood_tree_large_4"
        );
    }

    @Override
    public boolean lowerYLevel(RandomSource rand) {
        return getRandomNBTTree(rand).equals("caerulwood_tree_large_1") || getRandomNBTTree(rand).equals("caerulwood_tree_large_2") || getRandomNBTTree(rand).equals("caerulwood_tree_large_3") || getRandomNBTTree(rand).equals("caerulwood_tree_large_4");
    }

    @Override
    public boolean getSpawningCondition(WorldGenLevel world, RandomSource random, BlockPos pos) {
        return defaultTreeCanPlace(world, random, pos);
    }
}
