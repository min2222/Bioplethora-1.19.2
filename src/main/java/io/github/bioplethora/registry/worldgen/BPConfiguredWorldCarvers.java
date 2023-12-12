package io.github.bioplethora.registry.worldgen;

import net.minecraft.util.registry.LevelGenRegistries;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class BPConfiguredLevelCarvers {

    public static final ConfiguredCarver<?> END_SPRINGS_CARVER = register("end_springs_carver", BPLevelCarvers.END_SPRINGS.get().configured(new ProbabilityConfig(0.2F)));
    public static final ConfiguredCarver<?> CAERI_FORMERS = register("caeri_formers", BPLevelCarvers.CAERI_FORMERS.get().configured(new ProbabilityConfig(0.3F)));

    private static <CC extends ICarverConfig> ConfiguredCarver<CC> register(String pId, ConfiguredCarver<CC> pConfiguredCarver) {
        return Registry.register(LevelGenRegistries.CONFIGURED_CARVER, pId, pConfiguredCarver);
    }
}
