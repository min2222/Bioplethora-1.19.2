package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.api.world.WorldgenUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class BPNBTTrees {

    public static class EnivileNBTTree extends WorldgenUtils.NBTTree {

        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getTree(RandomSource random) {
            return BPConfiguredFeatures.ENIVILE_TREE_CONFIG;
        }
    }

    public static class CaerulwoodNBTTree extends WorldgenUtils.NBTTree {

        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getTree(RandomSource random) {
            return BPConfiguredFeatures.CAERULWOOD_TREE_CONFIG;
        }
    }
}
