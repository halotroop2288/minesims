package com.halotroop.sims;

import com.halotroop.sims.client.gui.screen.CASScreen;
import com.halotroop.sims.client.gui.screen.CreateASimScreen;
import com.halotroop.sims.client.gui.screen.OakTreeCASScreen;
import com.halotroop.sims.entity.SimEntity;
import com.halotroop.sims.item.SpawnWandItem;
import io.github.cottonmc.cotton.logging.ModLogger;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

// TODO: Add config screen for ModMenu
public class SimsMain implements ModInitializer {
	public static final ModLogger logger = new ModLogger("The Sims");
	public static final String MOD_NAME = "MineSims"; // FIXME: Copyright infringement!
	public static final ScreenHandlerType<CASScreen.Handler> CAS_SCREEN_HANDLER;
	public static final ScreenHandlerType<OakTreeCASScreen.Handler> OAK_TREE_CAS_SCREEN_HANDLER;
	public static final EntityType<SimEntity> SIM_ENTITY_TYPE;
	public static final Item SPAWN_WAND;
	
	static {
		CAS_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(getID("create_a_sim"),
				CASScreen.Handler::new);
		
		OAK_TREE_CAS_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(getID("create_a_sim_v2"),
				OakTreeCASScreen.Handler::new);
		
		SIM_ENTITY_TYPE = Registry.register(Registry.ENTITY_TYPE, getID("sim"), FabricEntityTypeBuilder
				.create(SpawnGroup.MISC, SimEntity::new)
				.dimensions(EntityDimensions.fixed(1, 2)).build());
		
		SPAWN_WAND = Registry.register(Registry.ITEM, getID("spawn_wand"), new SpawnWandItem());
	}
	
	/**
	 * @param path path relative to sims:
	 * @return an Identifier for that
	 */
	public static Identifier getID(String path) {
		return new Identifier("sims", path);
	}
	
	/**
	 * @param path path relative to assets/sims/textures of your texture (no extension)
	 * @return Identifier representing the texture
	 */
	public static Identifier getTexture(String path) {
		return getID("textures/" + path + ".png");
	}
	
	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(SIM_ENTITY_TYPE, SimEntity.createMobAttributes());
		logger.info(MOD_NAME + " is being loaded!");
	}
}
