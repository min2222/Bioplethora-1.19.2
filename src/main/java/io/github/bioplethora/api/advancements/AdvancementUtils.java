package io.github.bioplethora.api.advancements;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class AdvancementUtils {

    public static void grantBioAdvancement(Entity target, String advancement) {
        if (target instanceof ServerPlayer) {
            Advancement adv = ((ServerPlayer) target).server.getAdvancements().getAdvancement(new ResourceLocation(advancement));
            assert adv != null;
            AdvancementProgress advProg = ((ServerPlayer) target).getAdvancements().getOrStartProgress(adv);
            if (!advProg.isDone()) {
                for (String s : advProg.getRemainingCriteria()) {
                    ((ServerPlayer) target).getAdvancements().award(adv, s);
                }
            }
        }
    }
}
