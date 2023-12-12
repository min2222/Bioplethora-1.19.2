package io.github.bioplethora.api.world;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemUtils {

    public static void shootWithItem(LivingEntity shooter, Projectile projectile, Level world) {
        projectile.setOwner(shooter);
        projectile.setPos(shooter.getX(), shooter.getY() + 1.5, shooter.getZ());
        projectile.shootFromRotation(projectile, shooter.getXRot(), shooter.yHeadRot, 0, 1F, 0);
        world.addFreshEntity(projectile);
    }

    public static void shootWithItemBreakable(LivingEntity shooter, Projectile projectile, Level world, ItemStack stack, int breakAmount) {
        shootWithItem(shooter, projectile, world);
        if (!((Player) shooter).isCreative()) {
            if (shooter.getMainHandItem() == stack) {
                stack.hurtAndBreak(breakAmount, shooter, (entity1) -> entity1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            } else if (shooter.getOffhandItem() == stack) {
                stack.hurtAndBreak(breakAmount, shooter, (entity1) -> entity1.broadcastBreakEvent(EquipmentSlot.OFFHAND));
            }
        }
    }

    public static float projAngleX(LivingEntity entity) {
        return -Mth.sin(entity.yHeadRot * ((float) Math.PI / 180F)) * Mth.cos(entity.getXRot() * ((float) Math.PI / 180F));
    }

    public static float projAngleY(LivingEntity entity) {
        return Mth.sin(entity.getXRot() * ((float) Math.PI / 180F));
    }

    public static float projAngleZ(LivingEntity entity) {
        return Mth.cos(entity.yHeadRot * ((float) Math.PI / 180F)) * Mth.cos(entity.getXRot() * ((float) Math.PI / 180F));
    }

    public static boolean checkCooldownUsable(LivingEntity entity, ItemStack stack) {
        if (entity instanceof Player) {
            return !((Player) entity).getCooldowns().isOnCooldown(stack.getItem());
        } else {
            return true;
        }
    }

    public static void setStackOnCooldown(LivingEntity entity, ItemStack stack, int cooldown, boolean checkGamemode) {
        if (entity instanceof Player) {
            if (checkGamemode) {
                if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity)) {
                    ((Player) entity).getCooldowns().addCooldown(stack.getItem(), cooldown);
                }
            } else {
                ((Player) entity).getCooldowns().addCooldown(stack.getItem(), cooldown);
            }
        }
    }
}
