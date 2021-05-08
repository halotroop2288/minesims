package com.halotroop.sims.client.rei;

import com.halotroop.sims.SimsMain;
import com.halotroop.sims.client.gui.screen.OakTreeCASScreen;
import me.shedaniel.rei.api.DisplayHelper;
import me.shedaniel.rei.api.OverlayDecider;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class REIPlugin implements REIPluginV0 {
	@Override
	public Identifier getPluginIdentifier() {
		return SimsMain.getID("rei_plugin");
	}
	
	@Override
	public void registerBounds(DisplayHelper displayHelper) {
		displayHelper.registerHandler(new OverlayDecider() {
			@Override
			public boolean isHandingScreen(Class<?> screen) {
				return screen == OakTreeCASScreen.class;
			}
			
			@Override
			public ActionResult shouldScreenBeOverlayed(Class<?> screen) {
				return ActionResult.FAIL;
			}
		});
	}
}
