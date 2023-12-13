package io.github.bioplethora.client.armor.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import io.github.bioplethora.Bioplethora;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class NandbricArmorModel<T extends LivingEntity> extends HumanoidModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Bioplethora.MOD_ID, "nandbric_armor"), "main");
	public final ModelPart helmet;
	public final ModelPart chestplate;
	public final ModelPart rightarm;
	public final ModelPart leftarm;
	public final ModelPart rightboot;
	public final ModelPart leftboot;

	public NandbricArmorModel(ModelPart root) {
		super(root);
		this.helmet = root.getChild("helmet");
		this.chestplate = root.getChild("chestplate");
		this.rightarm = root.getChild("rightarm");
		this.leftarm = root.getChild("leftarm");
		this.rightboot = root.getChild("rightboot");
		this.leftboot = root.getChild("leftboot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 1);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition helmet = partdefinition.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightscales = helmet.addOrReplaceChild("rightscales", CubeListBuilder.create(), PartPose.offset(-5.0F, -9.0F, 0.0F));

		PartDefinition scale0 = rightscales.addOrReplaceChild("scale0", CubeListBuilder.create(), PartPose.offset(6.0F, 4.0F, 2.0F));

		scale0.addOrReplaceChild("scale0_r1", CubeListBuilder.create().texOffs(56, 16).addBox(-6.0F, -0.5F, 0.1643F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-4.9486F, -3.4895F, -6.1643F, 0.5404F, 1.0769F, 0.8892F));

		PartDefinition scale1 = rightscales.addOrReplaceChild("scale1", CubeListBuilder.create(), PartPose.offset(2.35F, 6.0F, -3.0F));

		scale1.addOrReplaceChild("scale1_r1", CubeListBuilder.create().texOffs(56, 20).addBox(-4.0685F, -1.5F, 0.0328F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-2.25F, -2.75F, -1.5F, 0.0F, 1.1345F, 0.0F));

		PartDefinition scale2 = rightscales.addOrReplaceChild("scale2", CubeListBuilder.create(), PartPose.offset(2.35F, 5.0F, 1.0F));

		scale2.addOrReplaceChild("scale2_r1", CubeListBuilder.create().texOffs(56, 20).addBox(-2.0F, -2.75F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-3.0F, -1.5F, 0.5F, 0.0F, 1.1345F, 0.0F));

		PartDefinition leftscales = helmet.addOrReplaceChild("leftscales", CubeListBuilder.create(), PartPose.offset(5.0F, -9.0F, 0.0F));

		PartDefinition scale3 = leftscales.addOrReplaceChild("scale3", CubeListBuilder.create(), PartPose.offset(-6.0F, 4.0F, 2.0F));

		scale3.addOrReplaceChild("scale3_r1", CubeListBuilder.create().texOffs(56, 16).mirror().addBox(0.0F, -0.5F, 0.1643F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(4.9486F, -3.4895F, -6.1643F, 0.5404F, -1.0769F, -0.8892F));

		PartDefinition scale4 = leftscales.addOrReplaceChild("scale4", CubeListBuilder.create(), PartPose.offset(-2.35F, 6.0F, -3.0F));

		scale4.addOrReplaceChild("scale4_r1", CubeListBuilder.create().texOffs(56, 20).mirror().addBox(0.0685F, -1.5F, 0.0328F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(2.25F, -2.75F, -1.5F, 0.0F, -1.1345F, 0.0F));

		PartDefinition scale5 = leftscales.addOrReplaceChild("scale5", CubeListBuilder.create(), PartPose.offset(-2.35F, 5.0F, 1.0F));

		scale5.addOrReplaceChild("scale5_r1", CubeListBuilder.create().texOffs(56, 20).mirror().addBox(-2.0F, -2.75F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(3.0F, -1.5F, 0.5F, 0.0F, -1.1345F, 0.0F));

		partdefinition.addOrReplaceChild("chestplate", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		partdefinition.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F))
		.texOffs(56, 24).addBox(-4.0F, 0.3F, -2.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(1.002F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		partdefinition.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false)
		.texOffs(56, 24).mirror().addBox(-1.0F, 0.3F, -2.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(1.002F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		partdefinition.addOrReplaceChild("rightboot", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		partdefinition.addOrReplaceChild("leftboot", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 80, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		helmet.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		chestplate.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightboot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftboot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}