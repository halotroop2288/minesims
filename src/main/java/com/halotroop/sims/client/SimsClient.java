package com.halotroop.sims.client;

import com.halotroop.sims.SimsMain;
import com.halotroop.sims.client.screen.CreateASimGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class SimsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(SimsMain.CAS_SCREEN_HANDLER,
				(gui, inventory, title) -> new CreateASimGUI.Screen(gui, inventory));
	}
}
