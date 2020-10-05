package com.halotroop.sims.client.gui.screen;

import com.halotroop.sims.SimsMain;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.control.Anchor;
import io.github.redstoneparadox.oaktree.client.gui.control.Control;
import io.github.redstoneparadox.oaktree.client.gui.control.GridPanelControl;
import io.github.redstoneparadox.oaktree.client.gui.control.PanelControl;
import io.github.redstoneparadox.oaktree.client.gui.style.TextureControlStyle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class OakTreeCASScreen extends HandledScreen<OakTreeCASScreen.Handler> {
	public static final Identifier TEXTURE_ATLAS = SimsMain.getID("textures/gui/cas/create_a_sim_v2.png");
	public static final Text TITLE = new TranslatableText("gui.cas.title");
	public final PanelControl<?> rootPanel;
	private final ControlGui gui;
	
	private TextureControlStyle style() {
		return new TextureControlStyle(TEXTURE_ATLAS)
				.fileDimensions(256, 256)
				// RedstoneParadox: This needs to be removed for non-tiling textures
				.textureSize(256, 256);
	}
	
	public OakTreeCASScreen(Handler handler) {
		super(handler, handler.inventory, TITLE);
		rootPanel = personalityTab();
		gui = new ControlGui(this, rootPanel);
	}
	
	private PanelControl<?> rootPanel() {
		return new PanelControl<>()
				.baseStyle(style().drawOrigin(1, 36))
				.size(254, 105).position(this.x, this.y)
				.anchor(Anchor.CENTER)
				.child(new Control<>()
						.baseStyle(style().drawOrigin(144, 15))
						.size(111, 19).position(this.x + 143, this.y - 19)
						.anchor(Anchor.CENTER)); // ???
	}
	
	private PanelControl<?> personalityTab() {
		return rootPanel().child(new GridPanelControl().baseStyle(style().drawOrigin(66, 161))
				.size(18, 90)
				.columns(1).rows(5)
				.anchor(Anchor.CENTER)) // ???
				.child(new GridPanelControl().baseStyle(style().drawOrigin(89, 161))
						.size(18, 90)
						.columns(8).rows(5)
						.anchor(Anchor.CENTER)); // ???
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.drawBackground(matrices, delta, mouseX, mouseY);
		this.drawForeground(matrices, mouseX, mouseY);
	}
	
	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		gui.draw(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
	}
	
	public static class Handler extends ScreenHandler {
		private final PlayerInventory inventory;
		
		public Handler(int syncId, PlayerInventory inventory) {
			super(SimsMain.OAK_TREE_CAS_SCREEN_HANDLER, syncId);
			this.inventory = inventory;
		}
		
		@Override
		public boolean canUse(PlayerEntity player) {
			return true;
		}
	}
}
