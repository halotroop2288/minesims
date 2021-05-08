package com.halotroop.sims.client.gui.screen;

import com.halotroop.sims.SimsMain;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.control.*;
import io.github.redstoneparadox.oaktree.client.gui.style.TextureControlStyle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ShieldItem;
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
	
	public OakTreeCASScreen(Handler handler) {
		super(handler, handler.inventory, TITLE);
		rootPanel = inventoryTab();
		gui = new ControlGui(this, rootPanel);
	}
	
	private TextureControlStyle style() {
		return new TextureControlStyle(TEXTURE_ATLAS)
				.fileDimensions(256, 256)
				// RedstoneParadox: This needs to be removed for non-tiling textures
				.textureSize(256, 256)
				.scale(1f);
	}
	
	private PanelControl<?> rootPanel() {
		return new PanelControl<>()
				.baseStyle(style().drawOrigin(1, 36))
				.size(254, 105).position(this.x, this.y)
				.anchor(Anchor.CENTER)
				.padding(6)
				.child(new Control<>()
						.baseStyle(style().drawOrigin(144, 15))
						.size(111, 19).position(this.x + 6, this.y - 22)
						.anchor(Anchor.TOP_RIGHT));
	}
	
	private PanelControl<?> inventoryTab() {
		return rootPanel()
				.child(new GridPanelControl().baseStyle(style().drawOrigin(66, 161))
						.size(18, 90)
						.columns(1).rows(5)
						.anchor(Anchor.CENTER_LEFT).position(this.x, this.y)
						.cells((row, column, index) -> new SlotControl(index, 0)
								.canInsert((gui, slot, stack) -> {
									if (stack.getItem() instanceof ArmorItem) {
										ArmorItem item = ((ArmorItem)stack.getItem());
										switch (index) {
											case 1:
												return item.getSlotType().equals(EquipmentSlot.HEAD);
											case 2:
												return item.getSlotType().equals(EquipmentSlot.CHEST);
											case 4:
												return item.getSlotType().equals(EquipmentSlot.LEGS);
											case 5:
												return item.getSlotType().equals(EquipmentSlot.FEET);
											default:
												return false;
										}
									} else {
										return index == 3 && stack.getItem() instanceof ShieldItem;
									}
								})
								.canInsert((g1, s1, s2) -> true)
								.filter()
						)
				)
				.child(new GridPanelControl().baseStyle(style().drawOrigin(89, 161))
						.size(144, 90)
						.columns(8).rows(5)
						.anchor(Anchor.CENTER).position(this.x, this.y)
						.cells((a, b, c) -> new SlotControl(c, 1))
				);
	}
	
	@Override
	public void init(MinecraftClient client, int width, int height) {
		super.init(client, width, height);
		this.gui.init();
	}
	
	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		this.gui.draw(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
	}
	
	@Override
	public void onClose() {
		this.gui.close();
		super.onClose();
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
