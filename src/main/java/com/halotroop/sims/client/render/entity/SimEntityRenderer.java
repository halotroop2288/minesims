package com.halotroop.sims.client.render.entity;

import com.halotroop.sims.client.render.entity.model.SimEntityModel;
import com.halotroop.sims.entity.SimEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SimEntityRenderer extends MobEntityRenderer<SimEntity, SimEntityModel<SimEntity>> {
	public SimEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new SimEntityModel<>(0, false, false), 1);
		this.addFeature(new ArmorFeatureRenderer<>(this,
				new SimEntityModel<>(0.5F), new SimEntityModel<>(1.0F)));
		this.addFeature(new ElytraFeatureRenderer<>(this));
	}
	
	@Override
	public void render(SimEntity sim, float yaw, float tickDelta, MatrixStack matrixStack,
	                   VertexConsumerProvider vertexConsumerProvider, int light) {
		super.render(sim, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
	}
	
	@Override
	public Identifier getTexture(SimEntity entity) {
		return entity.getSkinTexture();
	}
}
