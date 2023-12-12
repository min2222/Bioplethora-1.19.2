package io.github.bioplethora.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;

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

public class FrostbiteEyeParticle extends TextureSheetParticle {

    private FrostbiteEyeParticle(ClientLevel clientLevel, double p_i232384_2_, double p_i232384_4_, double p_i232384_6_, SpriteSet sprite) {
        super(clientLevel, p_i232384_2_, p_i232384_4_, p_i232384_6_);
        this.lifetime = 20;
        float f = 1.0F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize = 1.0F;
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
        super.render(pBuffer, pRenderInfo, pPartialTicks);
        this.alpha = 1.0F - Mth.clamp(((float)this.age + pPartialTicks) / (float)this.lifetime, 0.0F, 1.0F);
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
            FrostbiteEyeParticle particle = new FrostbiteEyeParticle(pLevel, pX, pY, pZ, this.sprites);
            particle.setColor(1.0f, 1.0f, 1.0f);
            particle.setAlpha(1.0F);
            return particle;
        }
    }
}
