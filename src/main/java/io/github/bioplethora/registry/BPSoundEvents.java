package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Bioplethora.MOD_ID);

    public static final RegistryObject<SoundEvent> CREPHOXL_IDLE = SOUNDS.register("crephoxl_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "crephoxl_idle")));
    public static final RegistryObject<SoundEvent> CREPHOXL_HURT = SOUNDS.register("crephoxl_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "crephoxl_hurt")));
    public static final RegistryObject<SoundEvent> CREPHOXL_DEATH = SOUNDS.register("crephoxl_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "crephoxl_death")));
    
    public static final RegistryObject<SoundEvent> FROSTBITE_GOLEM_IDLE = SOUNDS.register("frostbite_golem_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "frostbite_golem_idle")));
    public static final RegistryObject<SoundEvent> FROSTBITE_GOLEM_HURT = SOUNDS.register("frostbite_golem_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "frostbite_golem_hurt")));
    public static final RegistryObject<SoundEvent> FROSTBITE_GOLEM_DEATH = SOUNDS.register("frostbite_golem_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "frostbite_golem_death")));

    public static final RegistryObject<SoundEvent> MYLIOTHAN_IDLE = SOUNDS.register("myliothan_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "myliothan_idle")));
    
    public static final RegistryObject<SoundEvent> SHACHATH_IDLE = SOUNDS.register("shachath_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "shachath_idle")));
    public static final RegistryObject<SoundEvent> SHACHATH_HURT = SOUNDS.register("shachath_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "shachath_hurt")));
    public static final RegistryObject<SoundEvent> SHACHATH_DEATH = SOUNDS.register("shachath_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "shachath_death")));
    public static final RegistryObject<SoundEvent> SHACHATH_SLASH = SOUNDS.register("shachath_slash", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "shachath_slash")));

    public static final RegistryObject<SoundEvent> ALTYRUS_IDLE = SOUNDS.register("altyrus_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "altyrus_idle")));
    public static final RegistryObject<SoundEvent> ALTYRUS_CHARGE = SOUNDS.register("altyrus_charge", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "altyrus_charge")));

    public static final RegistryObject<SoundEvent> ALPHEM_STEP = SOUNDS.register("alphem_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "alphem_step")));

    public static final RegistryObject<SoundEvent> ALPHEM_KING_ROAR = SOUNDS.register("alphem_king_roar", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "alphem_king_roar")));

    public static final RegistryObject<SoundEvent> TRUE_DEFENSE_CLASH = SOUNDS.register("true_defense_clash", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Bioplethora.MOD_ID, "true_defense_clash")));

}
