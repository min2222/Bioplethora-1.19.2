package io.github.bioplethora.loot.conditions;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.BPLootConditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class InHellmode implements LootItemCondition {
    public static final InHellmode INSTANCE = new InHellmode();
    public static final LootContextParam<Boolean> HELLMODE_PARAM = new LootContextParam<>(new ResourceLocation(Bioplethora.MOD_ID, "hellmode"));

    public InHellmode() {
    }

    public LootItemConditionType getType() {
        return BPLootConditions.IN_HELLMODE;
    }

    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(HELLMODE_PARAM);
    }

    @Override
    public boolean test(LootContext lootContext) {
        return BPConfig.IN_HELLMODE;
    }

    public static LootItemCondition.Builder inHellMode() {
        return () -> INSTANCE;
    }

    public static class HellSerializer implements Serializer<InHellmode> {

        public void serialize(JsonObject pJson, InHellmode pValue, JsonSerializationContext pSerializationContext) {
        }

        public InHellmode deserialize(JsonObject pJson, JsonDeserializationContext pSerializationContext) {
            return InHellmode.INSTANCE;
        }
    }
}
