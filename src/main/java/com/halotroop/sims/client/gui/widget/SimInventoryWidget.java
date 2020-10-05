package com.halotroop.sims.client.gui.widget;

import com.halotroop.sims.entity.SimEntity;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;

public class SimInventoryWidget extends DrawableHelper implements Drawable, Element {
	private final SimEntity sim;
	
	public SimInventoryWidget(SimEntity sim) {
		this.sim = sim;
	}
	
	@Override
	public void render(MatrixStack matrices, int x, int y, float delta) {
	
	}
	
	public SimEntity getSim() {
		return sim;
	}
}
