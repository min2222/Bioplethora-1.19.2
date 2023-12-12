package io.github.bioplethora.world.features.treefeatures;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import io.github.bioplethora.world.features.NBTTreeFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EnivileTreeFeature extends NBTTreeFeature {

    public EnivileTreeFeature(Codec<NoneFeatureConfiguration> config) {
        super(config);
    }

    @Override
    public ImmutableList<String> getPossibleNBTTrees() {
        return ImmutableList.of(
                "enivile_tree_small_1",
                "enivile_tree_small_2",
                "enivile_tree_small_3",
                "enivile_tree_medium_1",
                "enivile_tree_medium_2",
                "enivile_tree_large_1",
                "enivile_tree_large_2"
        );
    }

    @Override
    public boolean lowerYLevel(RandomSource rand) {
        return false;
    }

    @Override
    public boolean getSpawningCondition(WorldGenLevel world, RandomSource random, BlockPos pos) {
        return defaultTreeCanPlace(world, random, pos);
    }
}
