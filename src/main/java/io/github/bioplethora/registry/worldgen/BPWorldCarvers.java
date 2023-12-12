package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.worldcarvers.CaeriFormersWorldCarver;
import io.github.bioplethora.world.worldcarvers.EndSpringWorldCarver;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPWorldCarvers {
    public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, Bioplethora.MOD_ID);

    public static final RegistryObject<WorldCarver<CaveCarverConfiguration>> END_SPRINGS = WORLD_CARVERS.register("end_springs", () -> new EndSpringWorldCarver(CaveCarverConfiguration.CODEC));
    public static final RegistryObject<WorldCarver<CaveCarverConfiguration>> CAERI_FORMERS = WORLD_CARVERS.register("caeri_formers", () -> new CaeriFormersWorldCarver(CaveCarverConfiguration.CODEC));
}
