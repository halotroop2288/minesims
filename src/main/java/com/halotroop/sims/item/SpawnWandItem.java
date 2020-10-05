package com.halotroop.sims.item;

import com.halotroop.sims.SimsMain;
import com.halotroop.sims.client.gui.screen.CASScreen;
import com.halotroop.sims.client.gui.screen.OakTreeCASScreen;
import com.halotroop.sims.entity.SimEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpawnWandItem extends Item {
	public SpawnWandItem() {
		super(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
	}
	
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText(this.getTranslationKey().concat(".tooltip")));
		if (context.isAdvanced()) {
			for (int i = 1; i <= 4; i++) {
				tooltip.add(new TranslatableText(this.getTranslationKey().concat(".tooltip_advanced." + i)));
			}
		}
		super.appendTooltip(stack, world, tooltip, context);
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (!context.getWorld().isClient) {
			SimsMain.logger.devInfo("Used wand on block! Attempting to create sim.");
			SimEntity sim = SimsMain.SIM_ENTITY_TYPE.create(context.getWorld());
			if (sim != null && context.getPlayer() != null) {
				SimsMain.logger.devInfo("Attempting to open create-a-sim screen.");
				context.getPlayer().openHandledScreen(createScreenHandlerFactory());
			} else return ActionResult.FAIL;
		}
		return super.useOnBlock(context);
	}
	
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if ((entity instanceof SimEntity) && user.abilities.creativeMode) {
			user.openHandledScreen(createScreenHandlerFactory());
			return ActionResult.SUCCESS;
		} else {
			SimsMain.logger.devInfo("Tried to use wand to edit a non-sim or edit a sim in survival mode.");
			return ActionResult.FAIL;
		}
	}
	
	private NamedScreenHandlerFactory createScreenHandlerFactory() {
		return new SimpleNamedScreenHandlerFactory((syncId, inv, player) ->
				new OakTreeCASScreen.Handler(syncId, inv), OakTreeCASScreen.TITLE);
	}
}
