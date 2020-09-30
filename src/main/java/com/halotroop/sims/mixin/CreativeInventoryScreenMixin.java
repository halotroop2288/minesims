package com.halotroop.sims.mixin;

import com.halotroop.sims.SimsMain;
import com.halotroop.sims.client.screen.CreateASimGUI;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
	
	public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}
	
	@Inject(method = "init", at = @At("TAIL"))
	private void addCASButton(CallbackInfo ci) {
		assert this.client != null;
		assert this.client.interactionManager != null;
		
		if (this.client.interactionManager.hasCreativeInventory()) {
				this.addButton(new ButtonWidget(16, 16, textRenderer.getWidth(CreateASimGUI.TITLE) + 4,
						16, CreateASimGUI.TITLE, (a) -> {
					SimsMain.logger.devInfo("SyncId is hard-coded! That's not right! This will cause issues on servers!");
					client.openScreen(new CreateASimGUI.Screen(SimsMain.CAS_SCREEN_HANDLER.create(42069, playerInventory), playerInventory));
			}));
		}
	}
}
