package io.github.bioplethora.particles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShachathClashInnerParticle extends TextureSheetParticle {

    private ShachathClashInnerParticle(ClientLevel clientLevel, double p_i232384_2_, double p_i232384_4_, double p_i232384_6_, SpriteSet sprite) {
        super(clientLevel, p_i232384_2_, p_i232384_4_, p_i232384_6_);
        this.lifetime = 10;
        float f = 1.0F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize = 2.5F;
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
        return ShachathClashInnerParticle.CLASH_SPECIAL;
    }

    public static ParticleRenderType CLASH_SPECIAL = new ParticleRenderType() {
        public void begin(BufferBuilder pBuilder, TextureManager pTextureManager) {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(true);
            RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            //RenderSystem.alphaFunc(516, 0.003921569F);
            pBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        public void end(Tesselator pTesselator) {
            pTesselator.end();
        }

        public String toString() {
            return "CLASH_SPECIAL";
        }
    };
    
    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet iAnimatedSprite) {
            this.sprites = iAnimatedSprite;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            ShachathClashInnerParticle particle = new ShachathClashInnerParticle(pLevel, pX, pY, pZ, this.sprites);
            particle.setColor(1.0f, 1.0f, 1.0f);
            return particle;
        }
    }
}
