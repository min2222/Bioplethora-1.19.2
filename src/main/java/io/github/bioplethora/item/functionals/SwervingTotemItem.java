package io.github.bioplethora.item.functionals;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SwervingTotemItem extends Item {

    public SwervingTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("item.bioplethora.boss_level.desc").withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.translatable("item.bioplethora.shift_reminder.desc").withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.translatable("item.bioplethora.totem_of_swerving.blessed_instinct.skill").withStyle(ChatFormatting.GOLD));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.totem_of_swerving.blessed_instinct.desc").withStyle(ChatFormatting.GRAY));
        }
    }
}
