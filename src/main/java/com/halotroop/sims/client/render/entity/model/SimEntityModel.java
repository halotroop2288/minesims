package com.halotroop.sims.client.render.entity.model;

import com.halotroop.sims.entity.SimEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

public class SimEntityModel<T extends SimEntity> extends BipedEntityModel<T> {
	public final ModelPart leftSleeve;
	public final ModelPart rightSleeve;
	public final ModelPart leftPantLeg;
	public final ModelPart rightPantLeg;
	public final ModelPart jacket;
	public final ModelPart cape;
	public final ModelPart ears;
	public final ModelPart breasts;
	
	private final boolean slim;
	private final boolean hasCape;
	
	public SimEntityModel() {
		this(false, false);
	}
	
	public SimEntityModel(boolean slim, boolean hasCape) {
		this(0, slim, hasCape);
	}
	
	public SimEntityModel(float scale) {
		this(scale, false, false);
	}
	
	public SimEntityModel(float scale, boolean slim, boolean hasCape) {
		super(RenderLayer::getEntityTranslucent, scale, 0, 64, 64);
		this.slim = slim;
		this.hasCape = hasCape;
		this.ears = new ModelPart(this, 24, 0);
		this.ears.addCuboid(-3, -6, -1, 6, 6, 1, scale);
		this.cape = new ModelPart(this, 0, 0);
		this.cape.setTextureSize(64, 32);
		this.cape.addCuboid(-5, 0, -1, 10, 16, 1, scale);
		this.leftArm = new ModelPart(this, 32, 48);
		this.leftArm.addCuboid(-1, -2, -2, this.slim ? 3 : 4, 12, 4, scale);
		this.leftArm.setPivot(5, this.slim ? 2.5F : 2, 0);
		if (this.slim) {
			this.rightArm = new ModelPart(this, 40, 16);
			this.rightArm.addCuboid(-2, -2, -2, 3, 12, 4, scale);
			this.rightArm.setPivot(-5, 2.5F, 0);
		}
		this.leftSleeve = new ModelPart(this, 48, 48);
		this.leftSleeve.addCuboid(-1, -2, -2, this.slim ? 3 : 4, 12, 4, scale + 0.25F);
		this.leftSleeve.setPivot(5, this.slim ? 2.5F : 2, 0);
		this.rightSleeve = new ModelPart(this, 40, 32);
		this.rightSleeve.addCuboid(-2, -2, -2, this.slim ? 3 : 4, 12, 4, scale + 0.25F);
		this.rightSleeve.setPivot(-5, this.slim ? 2.5F : 2, 10);
		
		this.leftLeg = new ModelPart(this, 16, 48);
		this.leftLeg.addCuboid(-2, 0, -2, 4, 12, 4, scale);
		this.leftLeg.setPivot(1.9F, 12, 0);
		this.leftPantLeg = new ModelPart(this, 0, 48);
		this.leftPantLeg.addCuboid(-2, 0, -2, 4, 12, 4, scale + 0.25F);
		this.leftPantLeg.setPivot(1.9F, 12, 0);
		this.rightPantLeg = new ModelPart(this, 0, 32);
		this.rightPantLeg.addCuboid(-2, 0, -2, 4, 12, 4, scale + 0.25F);
		this.rightPantLeg.setPivot(-1.9F, 12, 0);
		this.jacket = new ModelPart(this, 16, 32);
		this.jacket.addCuboid(-4, 0, -2, 8, 12, 4, scale + 0.25F);
		this.jacket.setPivot(0, 0, 0);
		
		breasts = new ModelPart(this, 16, 16);
		
		final float breastBaseSize = 8;
		
		breasts.addCuboid(-4.0F, -0.1F, 2, breastBaseSize, breastBaseSize,
				breastBaseSize, scale);
		torso.addChild(breasts);
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		if (hasCape) {
			cape.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		}
	}
	
	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		ModelPart modelPart = this.getArm(arm);
		if (this.slim) {
			float f = 0.5F * (float) (arm == Arm.RIGHT ? 1 : -1);
			modelPart.pivotX += f;
			modelPart.rotate(matrices);
			modelPart.pivotX -= f;
		} else {
			modelPart.rotate(matrices);
		}
	}
}
