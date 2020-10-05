package com.halotroop.sims.client;

import com.halotroop.sims.SimsMain;
import com.halotroop.sims.client.gui.screen.OakTreeCASScreen;
import com.halotroop.sims.client.render.entity.SimEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class SimsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScreenRegistry.<OakTreeCASScreen.Handler, OakTreeCASScreen>register(SimsMain.OAK_TREE_CAS_SCREEN_HANDLER,
				(handler, inv, title) -> new OakTreeCASScreen(handler));
		EntityRendererRegistry.INSTANCE.register(SimsMain.SIM_ENTITY_TYPE,
				(dispatcher, context) -> new SimEntityRenderer(dispatcher));
	}
}
