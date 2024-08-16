package io.github.bioplethora.client.entity.render.projectile;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.bioplethora.entity.projectile.VermilionBladeProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.inventory.InventoryMenu;

public class VermilionBladeProjectileRender<T extends Entity & ItemSupplier> extends EntityRenderer<T> {

    private final ItemRenderer itemRenderer;
    private final boolean fullBright;

    public VermilionBladeProjectileRender(EntityRendererProvider.Context p_i226035_1_, ItemRenderer p_i226035_2_, float p_i226035_3_, boolean p_i226035_4_) {
        super(p_i226035_1_);
        this.itemRenderer = p_i226035_2_;
        this.fullBright = p_i226035_4_;
    }

    public VermilionBladeProjectileRender(EntityRendererProvider.Context p_i50957_1_, ItemRenderer p_i50957_2_) {
        this(p_i50957_1_, p_i50957_2_, 1.0F, false);
    }

    protected int getBlockLightLevel(T pEntity, BlockPos pPos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(pEntity, pPos);
    }

    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.tickCount >= 2) {

            VermilionBladeProjectileEntity verm = (VermilionBladeProjectileEntity) pEntity;
            pPoseStack.pushPose();
            //RenderSystem.enableRescaleNormal();

            pPoseStack.scale(2.5F * (verm.bladeSize / 2F), 2.5F * (verm.bladeSize / 2F), 2.5F * (verm.bladeSize / 2F));
            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.yRot) - 90.0F));
            pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.xRot) - 45.0F));

            Vector3f rotation = new Vector3f(1.0F, 1.0F, 0.0F);
            rotation.normalize();
            pPoseStack.mulPose(rotation.rotationDegrees(180.0F));
            pPoseStack.translate(-0.1D, -0.65D, 0.0D);
            this.itemRenderer.renderStatic(pEntity.getItem(), ItemTransforms.TransformType.GROUND, pPackedLight, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, 0);

            pPoseStack.popPose();
            //RenderSystem.disableRescaleNormal();
            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        }
    }

    public ResourceLocation getTextureLocation(Entity pEntity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}