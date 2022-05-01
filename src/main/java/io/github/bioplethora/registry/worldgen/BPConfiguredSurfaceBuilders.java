package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.world.surfacebuilderconfigs.NoisySurfaceBuilderConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BPConfiguredSurfaceBuilders {

    public static ConfiguredSurfaceBuilder<?> ROCKY_WOODLANDS_SURFACE = register("rocky_woodlands",
            SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(
                    Blocks.GRASS_BLOCK.defaultBlockState(), // Surface Block
                    BPBlocks.MIRESTONE.get().defaultBlockState(), // Underground Block
                    Blocks.STONE.defaultBlockState() // Underwater Block
            )));

    public static ConfiguredSurfaceBuilder<?> END_HIGHLANDS_SURFACE = register("end_highlands",
            BPSurfaceBuilders.NOISY.get().configured(new NoisySurfaceBuilderConfig(
                    Blocks.WARPED_NYLIUM.defaultBlockState(), // Surface Block
                    BPBlocks.MIRESTONE.get().defaultBlockState(), // Surface Block Uncommon
                    Blocks.OBSIDIAN.defaultBlockState(), // Underground Block
                    BPBlocks.CYRA.get().defaultBlockState() // Underwater Block
            )));

    private static <SC extends ISurfaceBuilderConfig>ConfiguredSurfaceBuilder<SC> register(String name, ConfiguredSurfaceBuilder<SC> csb) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, new ResourceLocation(Bioplethora.MOD_ID, name), csb);
    }
}
