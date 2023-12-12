package io.github.bioplethora.world.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import io.github.bioplethora.Bioplethora;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public abstract class NBTTreeFeature extends Feature<NoneFeatureConfiguration> {

    public NBTTreeFeature(Codec<NoneFeatureConfiguration> config) {
        super(config);
    }

    public abstract ImmutableList<String> getPossibleNBTTrees();

    public abstract boolean lowerYLevel(RandomSource rand);

    public boolean getSpawningCondition(WorldGenLevel world, RandomSource random, BlockPos pos) {
        return true;
    }

    public String getRandomNBTTree(RandomSource rand) {
        return getPossibleNBTTrees().get(rand.nextInt(getPossibleNBTTrees().size()));
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
    	WorldGenLevel world = pContext.level();
    	RandomSource random = pContext.random();
    	BlockPos pos = pContext.origin();
    	
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos().set(pos);

        StructureTemplateManager tManager = world.getLevel().getStructureManager();
        StructureTemplate template = tManager.get(new ResourceLocation(Bioplethora.MOD_ID, "features/" + getRandomNBTTree(random))).get();

        if (template == null) {
            Bioplethora.LOGGER.warn("NBT does not exist!: " + new ResourceLocation(Bioplethora.MOD_ID, "features/" + getRandomNBTTree(random)));
            return false;
        }

        BlockPos halfOfNBT = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
        BlockPos.MutableBlockPos placementLocation = lowerYLevel(random) ?
                mutablePos.set(pos).move(-halfOfNBT.getX(), -2, -halfOfNBT.getZ()) :
                mutablePos.set(pos).move(-halfOfNBT.getX(), -1, -halfOfNBT.getZ()
                );

        if (getSpawningCondition(world, random, placementLocation)) {

            Rotation rotation = Rotation.getRandom(random);
            StructurePlaceSettings placementsettings = new StructurePlaceSettings().setRotation(rotation).setRotationPivot(halfOfNBT).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR).setIgnoreEntities(true);
            template.placeInWorld(world, placementLocation, placementLocation, placementsettings, random, 2);

            return true;
        } else {
            return false;
        }
    }

    public boolean defaultTreeCanPlace(WorldGenLevel world, RandomSource random, BlockPos pos) {
        int move = lowerYLevel(random) ? -1 : 0;
        int checkRad = 2;
        for (int x = -checkRad; x < checkRad; x++) {
            for (int z = -checkRad; z < checkRad; z++) {
                BlockPos.MutableBlockPos checkPos = pos.mutable().move(x, move, z);
                if (world.isEmptyBlock(checkPos) || world.isWaterAt(checkPos) || world.getBlockState(checkPos).getBlock() instanceof LeavesBlock) {
                    return false;
                }
            }
        }
        return true;
    }
}
