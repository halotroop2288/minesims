package com.halotroop.sims.client.gui.screen;

import com.halotroop.sims.SimsMain;
import com.halotroop.sims.entity.SimEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryListener;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.search.SearchManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

@Deprecated
@Environment(EnvType.CLIENT)
public class CreateASimScreen extends AbstractInventoryScreen<CreateASimScreen.Handler> {
	public static final Identifier TEXTURE = SimsMain.getTexture("gui/cas/create_a_sim");
	public static final Text TITLE = new TranslatableText("gui.cas.title");
	public static final int T_COL = 0x40_40_40;
	protected static final TexturePointer background, searchBar, previewBackground, simItemSlots,
			creativeItemSlots, scrollbarBack, scrollbarFront;
	private static final SimpleInventory inventory = new SimpleInventory(45);
	
	static {
		background = new TexturePointer(0, 0, 256, 121);
		searchBar = new TexturePointer(153, 142, 96, 12);
		previewBackground = new TexturePointer(6, 159, 58, 90);
		simItemSlots = new TexturePointer(67, 159, 18, 90);
		creativeItemSlots = new TexturePointer(88, 159, 144, 90);
		scrollbarBack = new TexturePointer(235, 159, 14, 90);
		scrollbarFront = new TexturePointer(236, 121, 12, 15);
	}
	
	private final PlayerEntity player;
	private TextFieldWidget searchBox;
	private CreativeInventoryListener listener;
	private float scrollPosition;
	private Tab currentTab = Tab.INVENTORY;
	private int mouseX, mouseY;
	
	{
		assert this.client != null && this.client.interactionManager != null && this.client.player != null;
	}
	
	public CreateASimScreen(Handler handler) {
		super(handler, handler.playerInventory, TITLE);
		this.player = handler.playerInventory.player;
		this.player.currentScreenHandler = handler;
		this.backgroundWidth = background.w;
		this.backgroundHeight = background.h;
	}
	
	@Override
	public void tick() {
		assert this.client != null && this.client.interactionManager != null;
		super.tick();
		
		// Close the screen if the player loses creative privileges or sim is null
		if (this.handler.sim == null || !this.player.abilities.creativeMode) {
			this.client.openScreen(null);
		} else {
			this.searchBox.tick();
		}
	}
	
	@Override
	public void resize(MinecraftClient client, int width, int height) {
		String string = this.searchBox.getText();
		this.init(client, width, height);
		this.searchBox.setText(string);
		if (!this.searchBox.getText().isEmpty()) {
			this.search();
		}
	}
	
	private void search() {
		assert this.client != null;
		this.handler.itemList.clear();
		String searchQuery = this.searchBox.getText();
		
		if (toString().isEmpty()) {
			for (Item item : Registry.ITEM) {
				item.appendStacks(ItemGroup.COMBAT, this.handler.itemList);
			}
		} else {
			handler.itemList.addAll(this.client.getSearchableContainer(SearchManager.ITEM_TOOLTIP)
					.findAll(searchQuery.toLowerCase(Locale.ROOT)));
		}
		
		this.scrollPosition = 0;
		this.handler.scrollItems(0);
	}
	
	@Override
	@SuppressWarnings({"ResultOfMethodCallIgnored"})
	public void init() {
		assert this.client != null && this.client.interactionManager != null;
		
		super.init();
		this.textRenderer.getClass();
		this.client.keyboard.setRepeatEvents(true);
		searchBox = new TextFieldWidget(this.textRenderer, this.x, this.y, 80, 9, new TranslatableText("itemGroup.search"));
		this.searchBox.setMaxLength(50);
		this.searchBox.setHasBorder(false);
		this.searchBox.setVisible(false);
		this.searchBox.setEditableColor(0xFFFFFF);
		this.children.add(this.searchBox);
		this.player.playerScreenHandler.removeListener(this.listener);
		this.listener = new CreativeInventoryListener(this.client);
		this.player.playerScreenHandler.addListener(this.listener);
	}
	
	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		currentTab.foreground.draw(this, matrices, mouseX, mouseY, 0);
	}
	
	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		assert this.client != null && this.client.player != null;
		
		this.client.getTextureManager().bindTexture(TEXTURE);
		
		// Draw tab icons under the background
		for (int i = 0; i <= 3; i++) {
			drawTabIcon(matrices, i);
		}
		
		// Draw the background // TODO: Move this to each tab.
		background.draw(this, matrices, this.x, this.y, delta);
		
		// Draw the current tab's elements
		currentTab.drawBackground(this, matrices, delta);
	}
	
	protected void drawTabIcon(MatrixStack matrices, int index) {
		assert this.client != null;
		
		int i = MathHelper.clamp(index, 0, 4);
		boolean active = (currentTab == Tab.values()[i]);
		
		final int tabWidth = 28;
		final int tabX = this.x + (i * (tabWidth + 11));
		this.client.getTextureManager().bindTexture(TEXTURE);
		// if active, show active background, if not, show custom one
		this.drawTexture(matrices, tabX, this.y - 11, (active ? 0 : i * tabWidth), 123, 28, 32);
		final int iconU = 140 + (16 * i);
		final int iconV = 121;
		this.drawTexture(matrices, tabX + 6, this.y - 5, iconU, iconV, 16, 16);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.renderBackground(matrices);
		this.drawBackground(matrices, delta, mouseX, mouseY);
		this.drawForeground(matrices, mouseX, mouseY);
		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
	}
	
	@Override
	public void onClose() {
		if (this.handler.sim != null) {
			this.handler.sim.setPos(this.player);
			this.player.world.spawnEntity(this.handler.sim);
		} else {
			SimsMain.logger.error("Sim was null!");
		}
		super.onClose();
	}
	
	private enum Tab {
		PERSONALITY((screen, matrices, mx, my, d) -> { // Foreground
			drawScrollBar(screen, matrices);
		}, // Background
				CreateASimScreen.searchBar,
				CreateASimScreen.previewBackground,
				CreateASimScreen.simItemSlots,
				CreateASimScreen.creativeItemSlots,
				CreateASimScreen.scrollbarBack
		),
		LOOKS((screen, matrices, mx, my, d) -> { // Foreground
			drawTitle(screen, matrices);
			drawEntityPreview(screen, mx, my);
			drawScrollBar(screen, matrices);
		}, // Background
				CreateASimScreen.background
		),
		INVENTORY((screen, matrices, mx, my, d) -> { // Foreground
			drawEntityPreview(screen, d, 0);
			drawScrollBar(screen, matrices);
		}, searchBar, previewBackground, simItemSlots, creativeItemSlots, scrollbarBack), // Background
		PREVIEW((screen, matrices, mx, my, d) -> { // Foreground
			drawTitle(screen, matrices);
			drawScrollBar(screen, matrices);
		}, previewBackground); // Background
		
		private final Drawable foreground;
		private final TexturePointer[] backgroundElements;
		
		Tab(Drawable foreground, TexturePointer... backgroundElements) {
			this.backgroundElements = backgroundElements;
			this.foreground = foreground;
		}
		
		// Draw the sim preview (This should be in the foreground, or come last in the background, so it's on top.)
		static void drawEntityPreview(CreateASimScreen screen, float pitch, float yaw) {
			final int previewX = screen.x + 35;
			final int previewY = screen.y + 100;
			final float simPitch = (float) previewX - screen.mouseX + pitch;
			final float simYaw = (float) previewY - screen.mouseY - 64 + yaw;
			
			InventoryScreen.drawEntity(previewX, previewY, 32, simPitch, simYaw,
					screen.handler.sim.inventory.owner);
		}
		
		public static void drawTitle(CreateASimScreen screen, MatrixStack matrices) {
			screen.textRenderer.draw(matrices,
					new TranslatableText("gui.cas." + screen.currentTab.name().toLowerCase()),
					screen.x + 170, screen.y + 6, T_COL);
		}
		
		public static void drawScrollBar(CreateASimScreen screen, MatrixStack matrices) {
			assert screen.client != null;
			
			final int scrollMin = screen.y + 25;
			final int scrollMax = scrollMin + 90;
			
			screen.client.getTextureManager().bindTexture(TEXTURE);
			scrollbarFront.draw(screen, matrices, screen.x + scrollbarFront.u,
					scrollMin + (int) ((float) (scrollMax - scrollMin - 17) * screen.scrollPosition), 0);
		}
		
		void drawBackground(CreateASimScreen screen, MatrixStack matrices, float delta) {
			for (TexturePointer element : this.backgroundElements) {
				element.draw(screen, matrices, screen.x + element.u, screen.y + element.v - 136, delta);
			}
		}
	}
	
	private interface Drawable {
		void draw(CreateASimScreen screen, MatrixStack matrices, int x, int y, float delta);
	}
	
	private static class TexturePointer implements Drawable {
		final int u, v, w, h;
		int x, y;
		
		private TexturePointer(int u, int v, int w, int h) {
			this.u = u;
			this.v = v;
			this.w = w;
			this.h = h;
		}
		
		private TexturePointer(int u, int v, int w, int h, int x, int y) {
			this(u, v, w, h);
			this.x = x;
			this.y = y;
		}
		
		/**
		 * @param screen   to be rendered to
		 * @param matrices from screen draw method
		 * @param x        offset
		 * @param y        offset
		 * @param delta    is unnecessary
		 */
		@Override
		public void draw(CreateASimScreen screen, MatrixStack matrices, int x, int y, float delta) {
			screen.drawTexture(matrices, this.x + x, this.y + y, this.u, this.v, this.w, this.h);
		}
	}
	
	@Deprecated
	public static class Handler extends ScreenHandler {
		final PlayerInventory playerInventory;
		final SimEntity sim;
		final DefaultedList<ItemStack> itemList = DefaultedList.of();
		
		@Deprecated
		public Handler(int syncId, PlayerInventory playerInventory) {
			this(null, syncId, playerInventory);
		}
		
		public Handler(ScreenHandlerType<? extends Handler> type, int syncId, PlayerInventory playerInventory) {
			super(type, syncId);
			this.playerInventory = playerInventory;
			sim = SimsMain.SIM_ENTITY_TYPE.create(playerInventory.player.world);
			SimsMain.logger.error("Sim " + (sim == null ? "not" : "") + " successfully created!");
		}
		
		public void scrollItems(float position) {
			int i = (this.itemList.size() + 9 - 1) / 9 - 5;
			int j = (int) ((double) (position * (float) i) + 0.5D);
			if (j < 0) {
				j = 0;
			}
			
			for (int k = 0; k < 5; k++) {
				for (int l = 0; l < 9; l++) {
					int m = l + (k + j) * 9;
					if (m >= 0 && m < this.itemList.size()) {
						CreateASimScreen.inventory.setStack(l + k * 9, this.itemList.get(m));
					} else {
						CreateASimScreen.inventory.setStack(l + k * 9, ItemStack.EMPTY);
					}
				}
			}
		}
		
		@Override
		public boolean canUse(PlayerEntity player) {
			return player == this.playerInventory.player;
		}
	}
}
