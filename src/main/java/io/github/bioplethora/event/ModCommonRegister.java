package io.github.bioplethora.event;

import org.jetbrains.annotations.NotNull;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.CuttlefishEntity;
import io.github.bioplethora.entity.creatures.GaugalemEntity;
import io.github.bioplethora.entity.creatures.MyliothanEntity;
import io.github.bioplethora.entity.creatures.OnofishEntity;
import io.github.bioplethora.entity.creatures.VoidjawEntity;
import io.github.bioplethora.entity.projectile.FrostbiteMetalArrowEntity;
import io.github.bioplethora.entity.projectile.MagmaBombEntity;
import io.github.bioplethora.entity.projectile.WindArrowEntity;
import io.github.bioplethora.item.BioplethoraSpawnEggItem;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.Util;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonRegister {

	//TODO might be incorrect event
    @SubscribeEvent
    public static void onRegisterEntities(FMLCommonSetupEvent event) {
        BioplethoraSpawnEggItem.initUnaddedEggs();
    }

    @SubscribeEvent
    public static void registerDispenserBehaviors(FMLCommonSetupEvent event) {

        DispenserBlock.registerBehavior(BPItems.WIND_ARROW.get(), new AbstractProjectileDispenseBehavior() {
            protected @NotNull Projectile getProjectile(@NotNull Level pLevel, @NotNull Position pPosition, @NotNull ItemStack pStack) {
                WindArrowEntity arrowentity = new WindArrowEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z());
                arrowentity.pickup = AbstractArrow.Pickup.ALLOWED;
                return arrowentity;
            }
        });
        DispenserBlock.registerBehavior(BPItems.BELLOPHITE_ARROW.get(), new AbstractProjectileDispenseBehavior() {
            protected @NotNull Projectile getProjectile(@NotNull Level pLevel, @NotNull Position pPosition, @NotNull ItemStack pStack) {
                FrostbiteMetalArrowEntity arrowentity = new FrostbiteMetalArrowEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z());
                arrowentity.pickup = AbstractArrow.Pickup.ALLOWED;
                return arrowentity;
            }
        });
        DispenserBlock.registerBehavior(BPItems.MAGMA_BOMB.get(), new AbstractProjectileDispenseBehavior() {
            protected @NotNull Projectile getProjectile(@NotNull Level pLevel, @NotNull Position pPosition, @NotNull ItemStack pStack) {
                return Util.make(new MagmaBombEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z()), (p_218408_1_) -> {
                });
            }
        });
    }


    @SubscribeEvent
    public static void registerEntityPlacements(SpawnPlacementRegisterEvent event) {
    	event.register(BPEntities.CUTTLEFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CuttlefishEntity::checkCuttlefishSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(BPEntities.ONOFISH.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, OnofishEntity::checkOnofishSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(BPEntities.TRIGGERFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(BPEntities.FIERY_EURYDN.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(BPEntities.SOUL_EURYDN.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

        event.register(BPEntities.DWARF_MOSSADILE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(BPEntities.GAUGALEM.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GaugalemEntity::checkGaugalemSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(BPEntities.MYUTHINE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

        event.register(BPEntities.CREPHOXL.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(BPEntities.VOIDJAW.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, VoidjawEntity::checkVoidjawSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

        event.register(BPEntities.MYLIOTHAN.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MyliothanEntity::checkMyliothanSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }
}
