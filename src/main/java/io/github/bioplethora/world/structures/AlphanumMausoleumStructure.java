package io.github.bioplethora.world.structures;

import java.util.Optional;

import com.mojang.serialization.Codec;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.worldgen.BPStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

public class AlphanumMausoleumStructure extends Structure {

	public static final Codec<AlphanumMausoleumStructure> CODEC = simpleCodec(AlphanumMausoleumStructure::new);
	
    public AlphanumMausoleumStructure(StructureSettings pCodec) {
        super(pCodec);
    }

    @Override
    public Decoration step() {
        return Decoration.SURFACE_STRUCTURES;
    }

    //TODO
    /*@Override
    protected boolean isFeatureChunk(ChunkGenerator generator, BiomeProvider provider, long seed, SharedSeedRandom seedRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoneFeatureConfiguration noFeatureConfig) {
        BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 16, 0, (chunkZ << 4) + 16);

        int landHeight = generator.getBaseHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG);

        IBlockReader columnOfBlocks = generator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));

        return topBlock.getFluidState().isEmpty();
    }*/
    
    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext pContext) {
    	ChunkPos chunkpos = pContext.chunkPos();
        int x = chunkpos.getBlockX(16);
        int z = chunkpos.getBlockZ(16);
        BlockPos blockpos = new BlockPos(x, 0, z);
        StructurePiecesBuilder structurepiecesbuilder = new StructurePiecesBuilder();
        Optional<GenerationStub> optional = JigsawPlacement.addPieces(pContext, pContext.registryAccess().registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).getHolderOrThrow(null), Optional.of(new ResourceLocation(Bioplethora.MOD_ID, "alphanum_mausoleum/start_pool")), 10, blockpos, false, Optional.of(Heightmap.Types.WORLD_SURFACE_WG), 80);

        structurepiecesbuilder.offsetPiecesVertically(1);
        //TODO
        //structurepiecesbuilder.getBoundingBox().minY -= 1;
        
        //LogManager.getLogger().log(Level.DEBUG, "House at " + this.pieces.get(0).getBoundingBox().x0 + " " + this.pieces.get(0).getBoundingBox().y0 + " " + this.pieces.get(0).getBoundingBox().z0);
    	return optional;
    }

	@Override
	public StructureType<?> type() {
		return BPStructures.ALPHANUM_MAUSOLEUM_TYPE.get();
	}
}
