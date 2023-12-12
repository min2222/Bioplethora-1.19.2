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

public class GrylynenShieldYellowItem extends GrylynenShieldBaseItem {

    public GrylynenShieldYellowItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getCooldownReduction() {
        return 40;
    }

    @Override
    public int getArmorBonus() {
        return 2;
    }

    @Override
    public void blockingSkill(ItemStack stack, LivingEntity user, Entity attacker, Level world) {
        // Checks if it is a 50% chance
        if (Math.random() <= 0.5) {
            // Regenerates the user's health by 2 hearts
            user.heal(2F);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.yellow_grylynen_shield.yellow_crystal_energy.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.yellow_grylynen_shield.yellow_crystal_energy.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
