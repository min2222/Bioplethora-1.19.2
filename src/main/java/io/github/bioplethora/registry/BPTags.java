package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BPTags {
    public static final class Blocks {
        public static final TagKey<Block> GROUND_END_BLOCKS = bioLoc("ground_end_blocks");
        public static final TagKey<Block> CAERULWOOD_LOGS = bioLoc("caerulwood_logs");
        public static final TagKey<Block> END_SPRINGS_REPLACEABLE = bioLoc("end_springs_replaceable");
        public static final TagKey<Block> CAERI_REPLACEABLE = bioLoc("caeri_replaceable");

        public static final TagKey<Block> GRYLYNEN_UNBREAKABLE = forgeLoc("grylynen_unbreakable");
        public static final TagKey<Block> CHORUS_GROWABLE = forgeLoc("chorus_growable");

        public static final TagKey<Block> WOODEN_GRYLYNEN_SPAWNABLE = forgeLoc("wooden_grylynen_spawnable");
        public static final TagKey<Block> STONE_GRYLYNEN_SPAWNABLE = forgeLoc("stone_grylynen_spawnable");
        public static final TagKey<Block> GOLDEN_GRYLYNEN_SPAWNABLE = forgeLoc("golden_grylynen_spawnable");
        public static final TagKey<Block> IRON_GRYLYNEN_SPAWNABLE = forgeLoc("iron_grylynen_spawnable");
        public static final TagKey<Block> DIAMOND_GRYLYNEN_SPAWNABLE = forgeLoc("diamond_grylynen_spawnable");
        public static final TagKey<Block> NETHERITE_GRYLYNEN_SPAWNABLE = forgeLoc("netherite_grylynen_spawnable");

        public static final TagKey<Block> ALPHANIA = forgeLoc("alphania");

        private static TagKey<Block> bioLoc(String path) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Bioplethora.MOD_ID, path));
        }

        private static TagKey<Block> mcLoc(String path) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("minecraft", path));
        }

        private static TagKey<Block> forgeLoc(String path) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("forge", path));
        }
    }

    public static final class Items {
        public static final TagKey<Item> ENIVILE_LOGS = bioLoc("enivile_logs");
        public static final TagKey<Item> CAERULWOOD_LOGS = bioLoc("caerulwood_logs");

        public static final TagKey<Item> LOGS = mcLoc("logs");
        public static final TagKey<Item> PLANKS = mcLoc("planks");
        public static final TagKey<Item> WOODEN_PRESSURE_PLATES = mcLoc("wooden_pressure_plates");
        public static final TagKey<Item> WOODEN_BUTTONS = mcLoc("wooden_buttons");
        public static final TagKey<Item> WOODEN_STAIRS = mcLoc("wooden_stairs");
        public static final TagKey<Item> WOODEN_SLABS = mcLoc("wooden_slabs");
        public static final TagKey<Item> WOODEN_FENCES = mcLoc("wooden_fences");
        public static final TagKey<Item> FENCE_GATES = mcLoc("fence_gates");

        public static final TagKey<Item> WOODEN_DOORS = mcLoc("wooden_doors");
        public static final TagKey<Item> WOODEN_TRAPDOORS = mcLoc("wooden_trapdoors");
        public static final TagKey<Item> STANDING_SIGNS = mcLoc("standing_signs");
        public static final TagKey<Item> WALL_SIGNS = mcLoc("wall_signs");

        // Curios Integration
        public static final TagKey<Item> CHARM = curiosLoc("charm");
        public static final TagKey<Item> NECKLACE = curiosLoc("necklace");

        // Wastelands of Baedoor Integration
        public static final TagKey<Item> WOBR_SABRE = forgeLoc("wobr_sabre");

        private static TagKey<Item> bioLoc(String path) {
            return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Bioplethora.MOD_ID, path));
        }

        private static TagKey<Item> mcLoc(String path) {
            return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("minecraft", path));
        }

        private static TagKey<Item> forgeLoc(String path) {
            return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", path));
        }

        private static TagKey<Item> curiosLoc(String path) {
            return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("curios", path));
        }
    }

    public static final class Entities {

        public static final TagKey<EntityType<?>> FLEIGNAR_TARGETS = forgeLoc("cavern_fleignar_targets");

        // Wastelands of Baedoor Integration
        public static final TagKey<EntityType<?>> AVOIDER_KILLABLE = forgeLoc("avoider_killable");
        public static final TagKey<EntityType<?>> AUTOMATON_TYPE = forgeLoc("automaton_type");

        private static TagKey<EntityType<?>> bioLoc(String path) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(Bioplethora.MOD_ID, path));
        }

        private static TagKey<EntityType<?>> mcLoc(String path) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("minecraft", path));
        }

        private static TagKey<EntityType<?>> forgeLoc(String path) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge", path));
        }
    }
}
