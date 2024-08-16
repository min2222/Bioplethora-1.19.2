package io.github.bioplethora.registry;

import java.util.HashMap;
import java.util.Map;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.loot.conditions.InHellmode;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class BPLootConditions {
    private static final Map<ResourceLocation, LootItemConditionType> LOOT_CONDITIONS = new HashMap<>();

    public static final LootItemConditionType IN_HELLMODE = addCondition("in_hellmode", new InHellmode.HellSerializer());

    private static LootItemConditionType addCondition(String name, Serializer<? extends LootItemCondition> serializer) {
    	LootItemConditionType condition = new LootItemConditionType(serializer);
        LOOT_CONDITIONS.put(new ResourceLocation(Bioplethora.MOD_ID, name), condition);
        return condition;
    }

    public static void registerConditions() {
        LOOT_CONDITIONS.forEach((key, condition) -> Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, key, condition));
    }
}
