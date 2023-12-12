package io.github.bioplethora.world.features;

import com.mojang.serialization.Codec;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.featureconfigs.NBTFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class NBTFeature extends Feature<NBTFeatureConfig> {

    public NBTFeature(Codec<NBTFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<NBTFeatureConfig> pContext) {
    	WorldGenLevel world = pContext.level();
    	RandomSource random = pContext.random();
    	BlockPos pos = pContext.origin();
    	NBTFeatureConfig config = pContext.config();
    	
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos().set(pos);

        int rad = 2;
        for (int x = -rad; x <= rad; x++) {
            for (int z = -rad; z <= rad; z++) {
                if (Math.abs(x * z) > rad && Math.abs(x * z) < rad * 2) {
                    mutablePos.set(pos).move(-x, -1, -z);
                    if (!world.getBlockState(mutablePos).canOcclude()) {
                        return false;
                    }
                }
            }
        }

        StructureTemplateManager tManager = world.getLevel().getStructureManager();
        StructureTemplate template = tManager.get(new ResourceLocation(Bioplethora.MOD_ID, "features/" + config.getFeature())).get();

        if (template == null) {
            Bioplethora.LOGGER.warn("NBT does not exist!: " + new ResourceLocation(Bioplethora.MOD_ID, "features/" + config.getFeature()));
            return false;
        }

        BlockPos halfOfNBT = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
        BlockPos.MutableBlockPos placementLocation = mutablePos.set(pos).move(-halfOfNBT.getX(), config.getYOffset(), -halfOfNBT.getZ());

        Rotation rotation = Rotation.getRandom(random);
        StructurePlaceSettings placementsettings = new StructurePlaceSettings().setRotation(rotation).setRotationPivot(halfOfNBT).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR).setIgnoreEntities(true);
        template.placeInWorld(world, placementLocation, placementLocation, placementsettings, random, 2);

        return true;
    }
}
