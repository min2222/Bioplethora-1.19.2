package io.github.bioplethora.registry.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;

public class BPConfiguredWorldCarvers {

    public static final ConfiguredWorldCarver<?> END_SPRINGS_CARVER = register("end_springs_carver", BPWorldCarvers.END_SPRINGS.get().configured(new CaveCarverConfiguration(0.2F)));
    public static final ConfiguredWorldCarver<?> CAERI_FORMERS = register("caeri_formers", BPWorldCarvers.CAERI_FORMERS.get().configured(new CaveCarverConfiguration(0.3F)));

    private static <CC extends CarverConfiguration> ConfiguredWorldCarver<CC> register(String pId, ConfiguredWorldCarver<CC> pConfiguredCarver) {
        return Registry.register(BuiltinRegistries.CONFIGURED_CARVER, pId, pConfiguredCarver);
    }
}
