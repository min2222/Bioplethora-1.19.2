package io.github.bioplethora.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class BPFoods {
    public static final FoodProperties FIERY_SAP_BOTTLE = new FoodProperties.Builder().nutrition(3).saturationMod(0.2F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0), 1.0F).fast().build();
    public static final FoodProperties SOUL_SAP_BOTTLE = new FoodProperties.Builder().nutrition(6).saturationMod(0.4F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0), 1.0F).build();
    public static final FoodProperties WARPED_GRAPES = new FoodProperties.Builder().nutrition(5).saturationMod(0.4F).build();
    public static final FoodProperties CRIMSON_BITTERSWEET_SEEDS = new FoodProperties.Builder().nutrition(4).saturationMod(0.3F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0), 1.0F).fast().build();
    public static final FoodProperties THONTUS_BERRIES = new FoodProperties.Builder().nutrition(5).saturationMod(0.5F).build();

    public static final FoodProperties RAW_CUTTLEFISH_MEAT = new FoodProperties.Builder().nutrition(3).saturationMod(0.2F).meat().fast().build();
    public static final FoodProperties COOKED_CUTTLEFISH_MEAT = new FoodProperties.Builder().nutrition(6).saturationMod(0.5F).meat().fast().build();
    public static final FoodProperties RAW_FLENTAIR = new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).effect(() -> new MobEffectInstance(MobEffects.POISON, 200, 0), 0.45F).meat().build();
    public static final FoodProperties COOKED_FLENTAIR = new FoodProperties.Builder().nutrition(7).saturationMod(0.7F).meat().build();
    public static final FoodProperties RAW_MOSILE = new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).meat().fast().build();
    public static final FoodProperties COOKED_MOSILE = new FoodProperties.Builder().nutrition(5).saturationMod(0.5F).meat().fast().build();
    public static final FoodProperties RAW_JAWFLESH = new FoodProperties.Builder().nutrition(3).saturationMod(0.4F).meat().build();
    public static final FoodProperties COOKED_JAWFLESH = new FoodProperties.Builder().nutrition(7).saturationMod(0.9F).meat().build();
}