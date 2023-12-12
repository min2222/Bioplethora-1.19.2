package io.github.bioplethora.api;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class BPItemSettings {
    public static String SACRED_LEVEL = "item.bioplethora.sacred_level.desc";
    public static String BOSS_LEVEL = "item.bioplethora.boss_level.desc";
    public static String SHIFT_REMINDER = "item.bioplethora.shift_reminder.desc";

    public static ChatFormatting TIER_LEVEL_COLOR = ChatFormatting.AQUA;
    public static ChatFormatting SHIFT_REMINDER_COLOR = ChatFormatting.GRAY;

    public static ChatFormatting SKILL_NAME_COLOR = ChatFormatting.GREEN;
    public static ChatFormatting SKILL_DESC_COLOR = ChatFormatting.GRAY;

    public static void sacredLevelText(List<Component> tooltip) {
        tooltip.add(Component.translatable(BPItemSettings.SACRED_LEVEL).withStyle(BPItemSettings.TIER_LEVEL_COLOR));
        tooltip.add(Component.translatable(BPItemSettings.SHIFT_REMINDER).withStyle(BPItemSettings.SHIFT_REMINDER_COLOR));
    }

    public static void bossLevelText(List<Component> tooltip) {
        tooltip.add(Component.translatable(BPItemSettings.BOSS_LEVEL).withStyle(BPItemSettings.TIER_LEVEL_COLOR));
        tooltip.add(Component.translatable(BPItemSettings.SHIFT_REMINDER).withStyle(BPItemSettings.SHIFT_REMINDER_COLOR));
    }

    public static ChatFormatting REACH_BONUS_COLOR = ChatFormatting.AQUA;

    public static String REACH_BONUS = "tooltip.bioplethora.reach_bonus.desc";

    public static ChatFormatting ANTIBIO_BONUS_COLOR = ChatFormatting.YELLOW;

    public static String ECOHARMLESS_ENCH = "tooltip.bioplethora.antibio_ecoharmless.desc";
    public static String PLETHONEUTRAL_ENCH = "tooltip.bioplethora.antibio_plethoneutral.desc";
    public static String DANGERUM_ENCH = "tooltip.bioplethora.antibio_dangerum.desc";
    public static String HELLSENT_ENCH = "tooltip.bioplethora.antibio_hellsent.desc";
    public static String ELDERIA_ENCH = "tooltip.bioplethora.antibio_elderia.desc";

    public static ChatFormatting NO_USE_COLOR = ChatFormatting.RED;

    public static String NO_USE_YET = "tooltip.bioplethora.no_use.desc";
}
