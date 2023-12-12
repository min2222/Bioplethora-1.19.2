package io.github.bioplethora.event.helper;

import io.github.bioplethora.entity.creatures.ShachathEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class ShachathCurseHelper {
    private final static String SHACHATH_CURSE = "bioplethora.shachath_curse";

    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Villager) {
            if (event.getSource().getEntity() instanceof Player) {
                Player source = (Player) event.getSource().getEntity();
                int curseLevel = nbt(source).getInt(SHACHATH_CURSE);

                if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(source)) {

                    nbt(source).putInt(SHACHATH_CURSE, curseLevel + 1);

                    if (curseLevel == 1) {
                        source.displayClientMessage(shachathMessage(1), true);
                    } else if (curseLevel == 10) {
                        source.displayClientMessage(shachathMessage(2), true);
                    } else if (curseLevel == 20) {
                        source.displayClientMessage(shachathMessage(3), true);
                    } else if (curseLevel == 30) {
                        source.displayClientMessage(shachathMessage(4), true);
                    } else if (curseLevel == 40) {
                        source.displayClientMessage(shachathMessage(5), true);
                    } else if (curseLevel == 50) {
                        source.displayClientMessage(shachathMessage(6), true);
                    } else if (curseLevel == 59) {
                        source.displayClientMessage(shachathMessage(7), true);
                    } else if (curseLevel == 60) {
                        source.displayClientMessage(shachathMessage(8), true);
                        summonShachath(source);
                    } else {
                        source.displayClientMessage(shachathMessage(0), true);
                    }
                }
            }
        }
    }

    public static void summonShachath(Player player) {
        ShachathEntity effect = BPEntities.SHACHATH.get().create(player.level);
        BlockPos summonPos = new BlockPos(player.blockPosition()).offset(-5 + player.getRandom().nextInt(10), 0, -5 + player.getRandom().nextInt(10));
        effect.moveTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
        effect.setTarget(player);
        player.level.addFreshEntity(effect);

        effect.descendEffect();
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BLAZE_SHOOT, 0.9F));
    }

    private static Component shachathMessage(int level) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.9F));
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ENDERMAN_TELEPORT, 1.5F));
        return Component.translatable("message.bioplethora.shachath_curse_" + level).withStyle(ChatFormatting.DARK_RED);
    }

    private static CompoundTag nbt(Player player) {
        CompoundTag playerData = player.getPersistentData();
        return playerData.getCompound(Player.PERSISTED_NBT_TAG);
    }
}
