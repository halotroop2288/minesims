package com.halotroop.sims.client.gui.widget;

import com.halotroop.sims.client.gui.screen.CASScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CreateASimTabWidget extends DrawableHelper implements Drawable, Element {
	public static final CreateASimTabWidget BASICS, SCALE, STYLE, FINALIZE;
	private static final int V = 121; // Backgrounds and icons start at y 121
	private static final int BACK_SIZE = 28, FRONT_SIZE = 16;
	
	static {
		BASICS = new CreateASimTabWidget(0);
		SCALE = new CreateASimTabWidget(1);
		STYLE = new CreateASimTabWidget(2);
		FINALIZE = new CreateASimTabWidget(3);
	}
	
	private final int index;
	private final int inactiveBackU, frontU;
	protected boolean active;
	
	private CreateASimTabWidget(int index) {
		// only have room for 4 different tabs
		this.index = MathHelper.clamp(index, 0, 3);
		this.inactiveBackU = BACK_SIZE * this.index;
		this.frontU = (FRONT_SIZE * this.index) + 140; // set of 6 icons starts at 140, 121, each 16x16
	}
	
	@Override
	public void render(MatrixStack matrices, int x, int y, float delta) {
		MinecraftClient.getInstance().getTextureManager().bindTexture(CASScreen.TEXTURE_ATLAS);
		int tabX = ((BACK_SIZE * this.index) + (10 * this.index));
		int frontX = tabX + 6;
		
		final int tabY = y - 11;
		this.drawTexture(matrices, x + tabX, tabY,
				active ? 0 : this.inactiveBackU, // inactive texture is at x 0
				V, BACK_SIZE, BACK_SIZE);
		
		this.drawTexture(matrices, frontX + x, tabY + 7, this.frontU, V, FRONT_SIZE, FRONT_SIZE);
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
}
