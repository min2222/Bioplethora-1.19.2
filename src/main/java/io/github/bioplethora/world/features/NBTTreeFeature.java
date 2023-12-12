package io.github.bioplethora.world.features;

import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import io.github.bioplethora.Bioplethora;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.chunk.ChunkGenerator;

public abstract class NBTTreeFeature extends Feature<NoFeatureConfig> {

    public NBTTreeFeature(Codec<NoFeatureConfig> config) {
        super(config);
    }

    public abstract ImmutableList<String> getPossibleNBTTrees();

    public abstract boolean lowerYLevel(Random rand);

    public boolean getSpawningCondition(ISeedReader world, Random random, BlockPos pos) {
        return true;
    }

    public String getRandomNBTTree(Random rand) {
        return getPossibleNBTTrees().get(rand.nextInt(getPossibleNBTTrees().size()));
    }

    public boolean place(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos().set(pos);

        TemplateManager tManager = world.getLevel().getStructureManager();
        Template template = tManager.get(new ResourceLocation(Bioplethora.MOD_ID, "features/" + getRandomNBTTree(random)));

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
            PlacementSettings placementsettings = new PlacementSettings().setRotation(rotation).setRotationPivot(halfOfNBT).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR).setIgnoreEntities(true);
            template.placeInLevelChunk(world, placementLocation, placementsettings, random);

            return true;
        } else {
            return false;
        }
    }

    public boolean defaultTreeCanPlace(ISeedReader world, Random random, BlockPos pos) {
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
