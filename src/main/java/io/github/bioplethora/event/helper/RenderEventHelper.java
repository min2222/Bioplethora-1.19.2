package io.github.bioplethora.event.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.mixin.IPlayerEntityMixin;
import io.github.bioplethora.config.BPConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;

public class RenderEventHelper {

    public static boolean reverseCurseAlpha;
    public static double curseAlpha;

    public static void onRenderingPlayer(RenderPlayerEvent event) {
    }

    public static void onRenderingOverlay(RenderGuiOverlayEvent.Pre event) {

        int getWidth = event.getWindow().getGuiScaledWidth(), getHeight = event.getWindow().getGuiScaledHeight();
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        IPlayerEntityMixin mxPlayer = (IPlayerEntityMixin) player;

        if (mxPlayer.hasAlphanumCurse()) {
            if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(player)) {
                renderAlphanumCurse(getWidth, getHeight);
            }
        }
    }

    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        IPlayerEntityMixin playermx = (IPlayerEntityMixin) player;
        float delta = Minecraft.getInstance().getFrameTime();
        float ticksExistedDelta = player.tickCount + delta;
        float shakeAmplitude;
        if (playermx.getScreenShaking() > 0 && !Minecraft.getInstance().isPaused() && player.level.isClientSide()) {
            shakeAmplitude = 0.05F;
            event.setPitch((float) (event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3 + 2) * 25));
            event.setYaw((float) (event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5 + 1) * 25));
            event.setRoll((float) (event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4) * 25));
            playermx.setScreenShaking(playermx.getScreenShaking() - 1);
        }
    }

    public static void onFogDensity(ViewportEvent.RenderFog event) {
        Minecraft mc = Minecraft.getInstance();
        BlockPos blockpos = Minecraft.getInstance().getCameraEntity().blockPosition();
    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!reverseCurseAlpha) { curseAlpha += 0.001; if (curseAlpha >= 0.10) { reverseCurseAlpha = true; }
        } else { curseAlpha -= 0.001; if (curseAlpha <= 0) { reverseCurseAlpha = false; }}
    }

    protected static void renderAlphanumCurse(double width, double height) {
        if (BPConfig.COMMON.alphemCurseOverlay.get()) {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(770, 771);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, (float) curseAlpha);
            RenderSystem.enableTexture();
            RenderSystem.setShaderTexture(0, new ResourceLocation(Bioplethora.MOD_ID, "textures/misc/alphanum_curse.png"));
            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(0.0D, height, -90.0D).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(width, height, -90.0D).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(width, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }
}
