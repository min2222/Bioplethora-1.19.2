package io.github.bioplethora.data;

import java.util.HashSet;
import java.util.Set;

import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class BPEntityLootTables extends EntityLoot {

    private final Set<EntityType<?>> entityTypes = new HashSet<>();

    @Override
    public void add(EntityType<?> entity, LootTable.Builder builder) {
        super.add(entity, builder);
        entityTypes.add(entity);
    }

    @Override
    protected void addTables() {

        // Crephoxl
        add(BPEntities.CREPHOXL.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.CREPHOXL_FEATHER.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.CREPHOXL_STICK.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Alphem
        add(BPEntities.ALPHEM.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.WINDPIECE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.OAK_PLANKS)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 4F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.STICK)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 6F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // FrostbiteGolem
        add(BPEntities.FROSTBITE_GOLEM.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.BELLOPHITE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.BELLOPHITE_CORE_FRAGMENT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Altyrus
        add(BPEntities.ALTYRUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.BELLOPHITE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4F, 7F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.0F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.BELLOPHITE_CORE_FRAGMENT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 4F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.PRIMORDIAL_FRAGMENT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(5F, 9F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.0F)))
                        ))
        );

        // Cuttlefish
        add(BPEntities.CUTTLEFISH.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.RAW_CUTTLEFISH_MEAT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.5F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.INK_SAC)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Peaguin
        add(BPEntities.PEAGUIN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.PEAGUIN_SCALES.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Nandbri
        add(BPEntities.NANDBRI.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.NANDBRI_SCALES.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 3F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.NANDBRI_FANG.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceCondition.randomChance(0.12F))
                        ))
        );

        // Myliothan
        add(BPEntities.MYLIOTHAN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.ABYSSAL_SCALES.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(6F, 9F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.5F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Shachath
        add(BPEntities.SHACHATH.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.TOTEM_OF_SWERVING.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Wooden Grylynen
        add(BPEntities.WOODEN_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.OAK_PLANKS)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3F, 6F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.5F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.GREEN_GRYLYNEN_CRYSTAL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.5F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Stone Grylynen
        add(BPEntities.STONE_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.STONE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 4F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.5F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.GREEN_GRYLYNEN_CRYSTAL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.5F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Golden Grylynen
        add(BPEntities.GOLDEN_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.5F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.YELLOW_GRYLYNEN_CRYSTAL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.3F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Iron Grylynen
        add(BPEntities.IRON_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.5F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.YELLOW_GRYLYNEN_CRYSTAL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.3F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Diamond Grylynen
        add(BPEntities.DIAMOND_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.DIAMOND)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.2F, 1.2F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.RED_GRYLYNEN_CRYSTAL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.3F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Netherite Grylynen
        add(BPEntities.NETHERITE_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 1F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.NETHERITE_INGOT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 1F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceCondition.randomChance(0.0225F))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.RED_GRYLYNEN_CRYSTAL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.3F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Cavern Fleignar
        add(BPEntities.CAVERN_FLEIGNAR.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.FLEIGNARITE_SCALES.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.RAW_FLENTAIR.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Alphem King
        add(BPEntities.ALPHEM_KING.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.ALPHANUM_GEM.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 5F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.ALPHEM_KING_REMNANT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.WINDPIECE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3F, 6F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.5F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.WINDY_ESSENCE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPBlocks.ALPHANUM_PILLAR.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(6F, 12F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Dwarf Mossadile
        add(BPEntities.DWARF_MOSSADILE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.RAW_MOSILE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                        ))
        );

        // Soul Eurydn
        add(BPEntities.SOUL_EURYDN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.SOUL_CUBE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                        ))
        );

        // Fiery Eurydn
        add(BPEntities.SOUL_EURYDN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.FIERY_CUBE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                        ))
        );


        // Trapjaw
        add(BPEntities.TRAPJAW.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.TRAPJAW_SCALES.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.RAW_JAWFLESH.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );

        // Voidjaw
        add(BPEntities.VOIDJAW.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.VOIDJAW_SCALES.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(BPItems.RAW_JAWFLESH.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        ))
        );
    }

    public LootPool.Builder addBasicEntity(RegistryObject<EntityType<?>> entity) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1.0F)))
        ;
    }

    @Override
    public Set<EntityType<?>> getKnownEntities() {
        return entityTypes;
    }
}
