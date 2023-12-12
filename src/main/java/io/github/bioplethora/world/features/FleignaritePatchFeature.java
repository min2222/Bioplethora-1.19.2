package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.creatures.CavernFleignarEntity;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.world.BPVanillaBiomeFeatureGeneration;
import io.github.bioplethora.world.featureconfigs.FleignariteSplotchConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class FleignaritePatchFeature extends Feature<FleignariteSplotchConfig> {

    public FleignaritePatchFeature(Codec<FleignariteSplotchConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<FleignariteSplotchConfig> context) {
    	FleignariteSplotchConfig config = context.config();
    	WorldGenLevel reader = context.level();
    	BlockPos pos = context.origin();
        if (config.placeOn.contains(reader.getBlockState(pos.below())) && config.placeIn.contains(reader.getBlockState(pos)) && config.placeUnder.contains(reader.getBlockState(pos.above()))) {
            if (BPVanillaBiomeFeatureGeneration.isFleignariteChunk(pos, reader)) {
                if (Math.random() <= 0.15 && BPConfig.COMMON.spawnCavernFleignar.get()) {
                    if (reader.getBlockState(pos.above()).isAir() && reader.getBlockState(pos.above(2)).isAir() && reader.getBlockState(pos.above(3)).isAir()) {
                        CavernFleignarEntity fleignar = BPEntities.CAVERN_FLEIGNAR.get().create(reader.getLevel());
                        fleignar.moveTo(pos, 0.0F, 0.0F);
                        reader.addFreshEntity(fleignar);
                    }
                }
                if (Math.random() < 0.2) {
                    reader.setBlock(pos, config.toPlaceRare, 2);
                } else {
                    reader.setBlock(pos, config.toPlace, 2);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
