package io.github.bioplethora.registry.worldgen;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BPConfiguredWorldCarvers {
    public static final DeferredRegister<ConfiguredWorldCarver<?>> CONFIGURED_CARVER = DeferredRegister.create(Registry.CONFIGURED_CARVER_REGISTRY, Bioplethora.MOD_ID);
    
    public static final ResourceKey<ConfiguredWorldCarver<?>> CAERI_FORMERS_KEY = createKey("caeri_formers");
    		
    public static final RegistryObject<ConfiguredWorldCarver<CaveCarverConfiguration>> END_SPRINGS_CARVER = CONFIGURED_CARVER.register("end_springs_carver", 
    		() -> BPWorldCarvers.END_SPRINGS.get().configured(new CaveCarverConfiguration(0.2F, UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.absolute(127)), ConstantFloat.of(0.5F), VerticalAnchor.aboveBottom(10), Registry.BLOCK.getOrCreateTag(BPTags.Blocks.END_SPRINGS_REPLACEABLE), ConstantFloat.of(1.0F), ConstantFloat.of(1.0F), ConstantFloat.of(-0.7F))));
    public static final RegistryObject<ConfiguredWorldCarver<CaveCarverConfiguration>> CAERI_FORMERS = CONFIGURED_CARVER.register("caeri_formers", 
    		() -> BPWorldCarvers.CAERI_FORMERS.get().configured(new CaveCarverConfiguration(0.3F, UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.absolute(127)), ConstantFloat.of(0.5F), VerticalAnchor.aboveBottom(10), Registry.BLOCK.getOrCreateTag(BPTags.Blocks.CAERI_REPLACEABLE), ConstantFloat.of(1.0F), ConstantFloat.of(1.0F), ConstantFloat.of(-0.7F))));
   
    private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String name) {
        ResourceLocation id = new ResourceLocation(Bioplethora.MOD_ID, name);
        ResourceKey<ConfiguredWorldCarver<?>> key = ResourceKey.create(Registry.CONFIGURED_CARVER_REGISTRY, id);
        return key;
    }
}
