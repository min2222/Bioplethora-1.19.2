package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraftforge.registries.DeferredRegister;

public class BPConfiguredWorldCarvers {
    public static final DeferredRegister<ConfiguredWorldCarver<?>> CONFIGURED_CARVER = DeferredRegister.create(Registries.CONFIGURED_CARVER, Bioplethora.MOD_ID);

    public static final ResourceKey<ConfiguredWorldCarver<?>> END_SPRINGS = createKey("end_springs");
    public static final ResourceKey<ConfiguredWorldCarver<?>> CAERI_FORMERS = createKey("caeri_formers");

    private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String pName) {
    	return ResourceKey.create(Registries.CONFIGURED_CARVER, new ResourceLocation(Bioplethora.MOD_ID, pName));
    }

	public static void bootstrap(BootstapContext<ConfiguredWorldCarver<?>> context) {
    	HolderGetter<Block> holdergetter = context.lookup(Registries.BLOCK);
    	context.register(END_SPRINGS, BPWorldCarvers.END_SPRINGS.get().configured(new CaveCarverConfiguration(0.2F, UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.absolute(127)), ConstantFloat.of(0.5F), VerticalAnchor.aboveBottom(10), holdergetter.getOrThrow(BPTags.Blocks.END_SPRINGS_REPLACEABLE), ConstantFloat.of(1.0F), ConstantFloat.of(1.0F), ConstantFloat.of(-0.7F))));
    	context.register(CAERI_FORMERS, BPWorldCarvers.CAERI_FORMERS.get().configured(new CaveCarverConfiguration(0.3F, UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.absolute(127)), ConstantFloat.of(0.5F), VerticalAnchor.aboveBottom(10), holdergetter.getOrThrow(BPTags.Blocks.CAERI_REPLACEABLE), ConstantFloat.of(1.0F), ConstantFloat.of(1.0F), ConstantFloat.of(-0.7F))));
    }
}
