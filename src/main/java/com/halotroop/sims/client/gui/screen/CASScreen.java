package com.halotroop.sims.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.halotroop.sims.SimsMain;
import com.halotroop.sims.client.gui.widget.CreateASimTabWidget;
import com.halotroop.sims.entity.SimEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class CASScreen extends HandledScreen<CASScreen.Handler> {
	public static final Identifier TEXTURE_ATLAS = SimsMain.getTexture("gui/cas/create_a_sim");
	public static final Text TITLE = new TranslatableText("gui.cas.title");
	public static ImmutableList<CreateASimTabWidget> TABS;

	static {
		TABS = ImmutableList.of(CreateASimTabWidget.BASICS, CreateASimTabWidget.SCALE,
				CreateASimTabWidget.STYLE, CreateASimTabWidget.FINALIZE);
	}
	
	private final SimEntity sim;
	public List<DrawableHelper> widgets = new ArrayList<>();
	private int x, y;
	
	public CASScreen(Handler handler, PlayerInventory inventory) {
		super(handler, inventory, TITLE);
		this.sim = handler.sim;
		SimsMain.logger.devInfo("Screen created.");
	}
	
	public static void setActiveTab(CreateASimTabWidget activeTab) {
		TABS.forEach((tab) -> tab.setActive(false));
		activeTab.setActive(true);
	}
	
	boolean notified;
	
	@Override
	public void tick() {
		if (client != null && client.currentScreen == this && !notified) {
			SimsMain.logger.devInfo("Screen opened.");
			notified = true;
		}
		super.tick();
	}
	
	@Override
	protected void init() {
		super.init();
		widgets.addAll(TABS);
		setActiveTab(TABS.get(0));
	}
	
	@Override
	public void init(MinecraftClient client, int width, int height) {
		super.init(client, width, height);
		this.x = (this.width - 256) / 2;
		this.y = (this.height - 120) / 2;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		renderForeground(matrices);
	}
	
	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		TABS.forEach((tab) -> tab.render(matrices, this.x, this.y, 0));
		MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE_ATLAS);
		this.drawTexture(matrices, this.x, this.y, 0, 0, 256, 121);
	}
	
	public void renderForeground(MatrixStack matrices) {
	}
	
	public static class Handler extends ScreenHandler {
		final PlayerInventory inventory;
		final SimEntity sim;
		
		public Handler(int syncId, PlayerInventory inv) { // Generic used by the registered type
			this(syncId, inv, SimsMain.SIM_ENTITY_TYPE.create(inv.player.world));
			SimsMain.logger.devInfo("Creating a generic sim screen handler.");
		}
		
		public Handler(int syncId, PlayerInventory inv, SimEntity sim) { // Used by the wand item
			super(SimsMain.CAS_SCREEN_HANDLER, syncId);
			SimsMain.logger.devInfo("Handler created.");
			this.inventory = inv;
			this.sim = sim;
		}
		
		@Override
		public boolean canUse(PlayerEntity player) {
			return sim.inventory.canPlayerUse(player);
		}
	}
}
