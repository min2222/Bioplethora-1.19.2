package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Mixin;

import io.github.bioplethora.api.mixin.IAbstractArrowMixin;
import net.minecraft.world.entity.projectile.AbstractArrow;

@Mixin(AbstractArrow.class)
public class AbstractArrowEntityMixin implements IAbstractArrowMixin {

    boolean shouldExplode;
    float explosionRadius;

    @Override
    public boolean getShouldExplode() {
        return this.shouldExplode;
    }

    @Override
    public void setShouldExplode(boolean shouldExplode) {
        this.shouldExplode = shouldExplode;
    }

    @Override
    public float getExplosionRadius() {
        return this.explosionRadius;
    }

    @Override
    public void setExplosionRadius(float explosionRadius) {
        this.explosionRadius = explosionRadius;
    }
}
