package io.github.bioplethora.event.helper;

import io.github.bioplethora.api.mixin.IAbstractArrowMixin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

public class ArrowMixinHelper {

    public static void onProjectileImpact(ProjectileImpactEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();

        if (entity instanceof AbstractArrow) {
            AbstractArrow arrow = (AbstractArrow) entity;
            IAbstractArrowMixin mxArrow = (IAbstractArrowMixin) arrow;

            if (mxArrow.getShouldExplode()) {
                level.explode(null, x, y, z, mxArrow.getExplosionRadius(), Level.ExplosionInteraction.MOB);

                // Wind Arrow Bug Fix
                arrow.discard();
            }
        }
    }
}
