package com.halotroop.sims;

import com.halotroop.sims.client.screen.CreateASimGUI;
import io.github.cottonmc.cotton.logging.ModLogger;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class SimsMain implements ModInitializer {
	public static final ModLogger logger = new ModLogger("The Sims");
	public static final String MOD_NAME = "The Sims Mod"; // FIXME: Copyright infringement!
	public static final ScreenHandlerType<CreateASimGUI> CAS_SCREEN_HANDLER;
	
	static {
		CAS_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(getID("create_a_sim"), CreateASimGUI::new);
	}
	
	public static Identifier getID(String path) {
		return new Identifier("sims", path);
	}
	
	@Override
	public void onInitialize() {
		logger.info(MOD_NAME + " is being loaded!");
	}
}
