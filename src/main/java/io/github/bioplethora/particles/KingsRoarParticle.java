package io.github.bioplethora.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import io.github.bioplethora.api.extras.ClientUtils;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KingsRoarParticle extends TextureSheetParticle {
    private static final Vector3f ROTATION_VECTOR = Util.make(new Vector3f(0.5F, 0.5F, 0.5F), Vector3f::normalize);
    private static final Vector3f TRANSFORM_VECTOR = new Vector3f(-1.0F, -1.0F, 0.0F);

    private KingsRoarParticle(ClientLevel clientLevel, double p_i232384_2_, double p_i232384_4_, double p_i232384_6_, double p_i232384_8_, double p_i232384_10_, double p_i232384_12_, SpriteSet sprite) {
        super(clientLevel, p_i232384_2_, p_i232384_4_, p_i232384_6_);
        this.lifetime = 40;
        float f = 1.0F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize = 16.0F;
        this.setSpriteFromAge(sprite);
    }

    public int getLightColor(float pPartialTick) {
        return 15728880;
    }

    public float getQuadSize(float p_234003_) {
        return this.quadSize * Mth.clamp(((float)this.age + p_234003_) / (float)this.lifetime * 0.75F, 0.0F, 1.0F);
    }

    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        this.alpha = 1.0F - Mth.clamp(((float)this.age + pPartialTicks) / (float)this.lifetime, 0.0F, 1.0F);
        ClientUtils.renderRotatedParticle(this, pBuffer, pRenderInfo, pPartialTicks, (p_234005_) -> {
            p_234005_.mul(Vector3f.YP.rotation(0.0F));
            p_234005_.mul(Vector3f.XP.rotation(-(1.0472F + (1.0472F / 2F))));
        });
        ClientUtils.renderRotatedParticle(this, pBuffer, pRenderInfo, pPartialTicks, (p_234000_) -> {
            p_234000_.mul(Vector3f.YP.rotation(-(float)Math.PI));
            p_234000_.mul(Vector3f.XP.rotation(1.0472F + (1.0472F / 2F)));
        });
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet iAnimatedSprite) {
            this.sprites = iAnimatedSprite;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            KingsRoarParticle particle = new KingsRoarParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
            particle.setColor(1.0f, 1.0f, 1.0f);
            particle.setAlpha(1.0F);
            return particle;
        }
    }
}
