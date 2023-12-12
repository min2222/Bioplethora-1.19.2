package io.github.bioplethora.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

@Mixin(Monster.class)
public class MonsterEntityMixin {

    @Inject(at = @At("HEAD"), method = ("checkMonsterSpawnRules"), cancellable = true)
    private static void checkMonsterSpawnRules(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, Random pRandom, CallbackInfoReturnable<Boolean> cir) {
        if (pType == EntityType.ENDERMAN) {
            if (pLevel.getLevel().dimension() == Level.END) {
                cir.setReturnValue(true);
            }
        }
    }
}
