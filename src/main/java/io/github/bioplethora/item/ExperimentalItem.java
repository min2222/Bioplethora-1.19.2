package io.github.bioplethora.item;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.api.IReachWeapon;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExperimentalItem extends Item implements IReachWeapon {

    public ExperimentalItem(Properties properties) {
        super(properties);
    }

    @Override
    public double getReachDistance() {
        return 128.0D;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int pItemSlot, boolean pIsSelected) {
        super.inventoryTick(stack, world, entity, pItemSlot, pIsSelected);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        for (int i = entity.level.getHeight(); i > entity.getY(); i--) {
            for (int c = 0; c < 90; c++) {
                entity.level.addParticle(ParticleTypes.FLAME, entity.getX(), i, entity.getZ(), Math.sin(c), 0.01, Math.cos(c));
            }
        }
        return super.hurtEnemy(stack, entity, source);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("item.bioplethora.boss_level.desc").withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.translatable("item.bioplethora.test_item.desc").withStyle(ChatFormatting.GRAY));
    }
}