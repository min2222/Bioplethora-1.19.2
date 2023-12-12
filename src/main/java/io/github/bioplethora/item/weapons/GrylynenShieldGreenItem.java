package io.github.bioplethora.item.weapons;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.api.BPItemSettings;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GrylynenShieldGreenItem extends GrylynenShieldBaseItem {

    public GrylynenShieldGreenItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getCooldownReduction() {
        return 60;
    }

    @Override
    public int getArmorBonus() {
        return 2;
    }

    @Override
    public void blockingSkill(ItemStack stack, LivingEntity user, Entity attacker, Level world) {
        // This area is still empty at the moment because I am not planning to give the green shield a skill yet.
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.green_grylynen_shield.green_crystal_energy.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.green_grylynen_shield.green_crystal_energy.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
