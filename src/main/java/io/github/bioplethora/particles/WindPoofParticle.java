package io.github.bioplethora.particles;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
* @Credit MaxBogomol
*/
@OnlyIn(Dist.CLIENT)
public class WindPoofParticle extends TextureSheetParticle {

    private final SpriteSet sprites;
    private float maxScale;
    private float maxAlpha;
    private float loseScale = 0.01F;

    private WindPoofParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Color tint, double diameter, SpriteSet sprites) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.sprites = sprites;

        setColor(tint.getRed()/255.0F, tint.getGreen()/255.0F, tint.getBlue()/255.0F);
        setSize((float)diameter, (float)diameter);

        final float PARTICLE_SCALE_FOR_ONE_METRE = 0.2F;
        this.quadSize = PARTICLE_SCALE_FOR_ONE_METRE * (float)diameter;

        this.lifetime = 100;

        final float ALPHA_VALUE = 1.0F;
        this.alpha = ALPHA_VALUE;

        this.xd = velocityX; this.yd = velocityY; this.zd = velocityZ;

        this.hasPhysics = true;
        maxScale = this.quadSize;
        maxAlpha = this.alpha;
    }

    @Override
    protected int getLightColor(float partialTick) {
        final int BLOCK_LIGHT = 15;
        final int SKY_LIGHT = 15;
        final int FULL_BRIGHTNESS_VALUE = LightTexture.pack(BLOCK_LIGHT, SKY_LIGHT);
        return FULL_BRIGHTNESS_VALUE;
    }

    @Override
    public void tick() {
        xo = x; yo = y; zo = z;
        quadSize = quadSize - loseScale;
        alpha = alpha - (maxAlpha/(maxScale/loseScale));

        if (quadSize <= 0) {
            this.remove();
        }

        move(xd, yd, zd);

        if (onGround) {
            this.remove();
        }

        if (yo == y && yd > 0) {
            this.remove();
        }

        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    public ParticleRenderType getRenderType() {
        return NORMAL_RENDER;
    }

    public static final void beginRenderCommon(BufferBuilder buffer, TextureManager manager) {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        //RenderSystem.alphaFunc(GL11.GL_GREATER, 0.003921569F);
        //RenderSystem.disableLighting();
        
        manager.bindForSetup(TextureAtlas.LOCATION_PARTICLES);
        manager.getTexture(TextureAtlas.LOCATION_PARTICLES).setBlurMipmap(true, false);
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    public static final void endRenderCommon() {
        Minecraft.getInstance().textureManager.getTexture(InventoryMenu.BLOCK_ATLAS).restoreLastBlurMipmap();
        //RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
    }

    public static final ParticleRenderType NORMAL_RENDER = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder pBuilder, TextureManager pTextureManager) {
            beginRenderCommon(pBuilder, pTextureManager);
        }

        @Override
        public void end(Tesselator pTesselator) {
            pTesselator.end();
            endRenderCommon();
        }

        @Override
        public String toString() {
            return "bioplethora:wind_poof";
        }
    };

    public static final ParticleRenderType DIW_RENDER = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder pBuilder, TextureManager pTextureManager) {
            beginRenderCommon(pBuilder, pTextureManager);
            RenderSystem.disableDepthTest();
        }

        @Override
        public void end(Tesselator pTesselator) {
            pTesselator.end();
            RenderSystem.enableDepthTest();
            endRenderCommon();
        }

        @Override
        public String toString() {
            return "bioplethora:no_depth_wind_poof";
        }
    };

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<WindPoofParticleData> {
        private final SpriteSet sprites;

        public Factory(SpriteSet p_i50563_1_) {
            this.sprites = p_i50563_1_;
        }

        public Particle createParticle(WindPoofParticleData flameParticleData, ClientLevel world, double xPos, double yPos, double zPos, double xVelocity, double yVelocity, double zVelocity) {
            WindPoofParticle windPoof = new WindPoofParticle(world, xPos, yPos, zPos, xVelocity, yVelocity, zVelocity,
                    flameParticleData.getTint(), flameParticleData.getDiameter(),
                    sprites);
            windPoof.pickSprite(sprites);
            return windPoof;
        }
    }
}
