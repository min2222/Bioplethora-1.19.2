package io.github.bioplethora.api.world;

import java.util.List;
import java.util.function.Predicate;

import io.github.bioplethora.api.mixin.IPlayerEntityMixin;
import io.github.bioplethora.entity.SummonableMonsterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityUtils {
	
	public static Explosion.BlockInteraction getMobGriefingEvent(Level level, Entity entity) {
		return ForgeEventFactory.getMobGriefingEvent(level, entity) ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE;
	}

    public static Predicate<Entity> IsNotPet(Entity owner) {
        return (entity) -> {
            if (entity instanceof TamableAnimal) {
                return ((TamableAnimal) entity).getOwner() != owner;
            } else if (entity instanceof SummonableMonsterEntity) {
                return ((SummonableMonsterEntity) entity).getOwner() != owner;
            } else {
                return entity != owner;
            }
        };
    }

    public static void shakeNearbyPlayersScreen(LivingEntity mob, int radius, int timeInTicks) {
        double x = mob.getX(), y = mob.getY(), z = mob.getZ();
        AABB area = new AABB(x - (radius / 2d), y, z - (radius / 2d), x + (radius / 2d), y + (radius / 2d), z + (radius / 2d));
        Level world = mob.level;

        for (Player entityIterator : world.getEntitiesOfClass(Player.class, area)) {
            ((IPlayerEntityMixin) entityIterator).setScreenShaking(timeInTicks);
        }
    }


    public static void knockbackAwayFromUser(float force, LivingEntity user, LivingEntity target) {
        target.knockback(force, Mth.sin(user.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(user.getYRot() * ((float) Math.PI / 180F)));
    }

    public static <T extends Entity> List<? extends T> getEntitiesInArea(Class<? extends T> entityClass, Level world, double x, double y, double z, double xzRad, double yRad) {
        return world.getEntitiesOfClass(entityClass, new AABB(
                x - (xzRad / 2d), y - (yRad / 2d), z - (xzRad / 2d),
                x + (xzRad / 2d), y + (yRad / 2d), z + (xzRad / 2d)
                )
        );
    }

    public static EquipmentSlot getSlotTypeFromItem(ItemStack stack, LivingEntity living) {
        if (living.getMainHandItem() == stack) {
            return EquipmentSlot.MAINHAND;

        } else if (living.getOffhandItem() == stack) {
            return EquipmentSlot.OFFHAND;

        } else {
            return EquipmentSlot.MAINHAND;
        }
    }

    public static void swingAHand(ItemStack stack, LivingEntity living) {
        if (living.getMainHandItem() == stack) {
            living.swing(InteractionHand.MAIN_HAND);

        } else if (living.getOffhandItem() == stack) {
            living.swing(InteractionHand.OFF_HAND);
        }
    }

    public static <T extends Entity> List<? extends T> getEntitiesInArea(Class<? extends T> entityClass, Level world, BlockPos pos, double xzRad, double yRad) {
        return getEntitiesInArea(entityClass, world, pos.getX(), pos.getY(), pos.getZ(), xzRad, yRad);
    }

    public static boolean isLiving(Entity entity) {
        return entity instanceof LivingEntity;
    }
}
