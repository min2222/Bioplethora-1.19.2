package io.github.bioplethora.registry.worldgen;

import java.util.Locale;

import com.google.common.collect.ImmutableList;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.world.featureconfigs.PendentBlocksFeatureConfig;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

//TODO CUSTOMIZED FEATURES not implemented yet
public class BPConfiguredFeatures {
		
	public static final Holder<ConfiguredFeature<PendentBlocksFeatureConfig, ?>> GLACYNTH = makePendentConfig("glacynth", 
            Blocks.ICE, BPBlocks.GLACYNTH_PLANT.get(), BPBlocks.GLACYNTH.get(),
            ImmutableList.of(Blocks.ICE, Blocks.BLUE_ICE, Blocks.PACKED_ICE, Blocks.FROSTED_ICE),
            3, 9);
	
	public static final Holder<ConfiguredFeature<PendentBlocksFeatureConfig, ?>> PINK_TWI = makePendentConfig("pink_twi", 
            BPBlocks.ENIVILE_LEAVES_PINK.get(), BPBlocks.PINK_TWI_PLANT.get(), BPBlocks.PINK_TWI.get(),
            ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 6);
	
    public static final Holder<ConfiguredFeature<PendentBlocksFeatureConfig, ?>> RED_TWI = makePendentConfig("red_twi", 
            BPBlocks.ENIVILE_LEAVES_RED.get(), BPBlocks.RED_TWI_PLANT.get(), BPBlocks.RED_TWI.get(),
            ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 8);
    
    public static final Holder<ConfiguredFeature<PendentBlocksFeatureConfig, ?>> SPINXELTHORN = makePendentConfig("spinxelthorn", 
            Blocks.END_STONE, BPBlocks.SPINXELTHORN_PLANT.get(), BPBlocks.SPINXELTHORN.get(),
            ImmutableList.of(Blocks.END_STONE),
            1, 8);
    
    public static final Holder<ConfiguredFeature<PendentBlocksFeatureConfig, ?>> SPIRIT_DANGLER = makePendentConfig("spirit_dangler", 
            Blocks.SOUL_SOIL, BPBlocks.SPIRIT_DANGLER_PLANT.get(), BPBlocks.SPIRIT_DANGLER.get(),
            ImmutableList.of(Blocks.SOUL_SOIL, Blocks.SOUL_SAND, Blocks.NETHERRACK, Blocks.BLACKSTONE),
            1, 8);
    
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> LAVA_SPIRE = register("lava_spire", BPFeatures.LAVA_EDGE_CLUSTER.get(), new RandomPatchConfiguration(64, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BPBlocks.LAVA_SPIRE.get())))));
	
	public static Holder<ConfiguredFeature<PendentBlocksFeatureConfig, ?>> makePendentConfig(String name, Block top, Block middle, Block end, ImmutableList<Block> whitelist, int minLength, int maxLength) {
		return register(name, BPFeatures.PENDENT_BLOCKS.get(), new PendentBlocksFeatureConfig.Builder()
                .setTopBlock(top).setMiddleBlock(middle)
                .setFruitedBlock(middle).setEndBlock(end.defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, 23))
                .setWhitelist(whitelist)
                .setMinLength(minLength)
                .setMaxLength(maxLength)
                .build());
	}
	
	public static Holder<ConfiguredFeature<PendentBlocksFeatureConfig, ?>> makeFruitablePendentConfig(String name, Block top, Block middle, Block fruited, Block end, ImmutableList<Block> whitelist, int minLength, int maxLength) {
		return register(name, BPFeatures.PENDENT_BLOCKS.get(), new PendentBlocksFeatureConfig.Builder()
                .setTopBlock(top).setMiddleBlock(middle)
                .setFruitedBlock(fruited).setEndBlock(end.defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, 23))
                .setWhitelist(whitelist)
                .setMinLength(minLength)
                .setMaxLength(maxLength)
                .build());
	}
	
	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC featureConfiguration) {
		return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, prefix(name).toString(), new ConfiguredFeature<>(feature, featureConfiguration));
	}
	
	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(Bioplethora.MOD_ID, name.toLowerCase(Locale.ROOT));
	}
}
