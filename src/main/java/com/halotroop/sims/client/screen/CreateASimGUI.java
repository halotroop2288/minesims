package com.halotroop.sims.client.screen;

import com.halotroop.sims.SimsMain;
import com.halotroop.sims.entity.simdata.Gender;
import com.halotroop.sims.entity.simdata.SimData;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class CreateASimGUI extends SyncedGuiDescription {
	public PlayerEntity player = playerInventory.player;
	
	public static Text TITLE = new TranslatableText("gui.cas.title");
	
	public SimData simData = SimData.GENERIC;
	
	WTabPanel settingsPanel;
	WGridPanel previewPanel, inventoryPanel;
	WScrollPanel looksPanel, personalityPanel;
	
	public CreateASimGUI(int syncId, PlayerInventory playerInventory) {
		super(SimsMain.CAS_SCREEN_HANDLER, syncId, playerInventory);
		initPanels();
		this.getRootPanel().validate(this); // settingsPanel
	}
	
	private void initPanels() {
		settingsPanel = new WTabPanel();
		setRootPanel(settingsPanel);
		
		initPersonalityPanel();
		initLooksPanel();
		initInventoryPanel();
		initPreviewPanel();
	}
	
	// This system is potentially noninclusive of genderfluid people. Suggestions welcome!
	private void initPersonalityPanel() {
		WGridPanel personalityTallPanel = new WGridPanel();
		
		int yOff = 1;
		
		final int width = 9;
		
		// Gender preset buttons
		
		personalityTallPanel.add(new WText(new TranslatableText("gui.cas.gender.presets")), 0, 0, width, 1);
		
		WButton malePresetButton = new WButton();
		malePresetButton.setLabel(new TranslatableText("gui.cas.gender.preset.male"));
		malePresetButton.addTooltip(new TooltipBuilder()
				.add(new TranslatableText("gui.cas.gender.male")));
		malePresetButton.setOnClick(() -> simData.gender = Gender.GENERIC_MALE);
		WButton femalePresetButton = new WButton();
		femalePresetButton.setLabel(new TranslatableText("gui.cas.gender.preset.female"));
		femalePresetButton.addTooltip(new TooltipBuilder()
				.add(new TranslatableText("gui.cas.gender.female")));
		femalePresetButton.setOnClick(() -> simData.gender = Gender.GENERIC_FEMALE);
		WButton unknownPresetButton = new WButton();
		unknownPresetButton.setLabel(new TranslatableText("gui.cas.gender.preset.unknown"));
		unknownPresetButton.addTooltip(new TooltipBuilder()
				.add(new TranslatableText("gui.cas.gender.unknown")));
		unknownPresetButton.setOnClick(() -> simData.gender = Gender.GENERIC_UNKNOWN);
		
		WWidget[] genderPresets = new WWidget[] {malePresetButton, femalePresetButton, unknownPresetButton};
		for (int i = 0; i < genderPresets.length; i++) {
			personalityTallPanel.add(genderPresets[i], i * 3, yOff, 3, 1);
		}
		yOff += 2;
		
		
		// Custom identification fields
		
		WTextField simGenderIDField = new WTextField(new TranslatableText("gui.cas.gender.id"));
		simGenderIDField.addTooltip(new TooltipBuilder()
				.add(new TranslatableText("gui.cas.gender.id.tooltip")));
		personalityTallPanel.add(simGenderIDField, 0, 0, width, 1);
		
		WTextField simSubjectPronounField = new WTextField(new TranslatableText("gui.cas.gender.subject"));
		simSubjectPronounField.addTooltip(new TooltipBuilder()
				.add(new TranslatableText("gui.cas.gender.subject.tooltip")));
		personalityTallPanel.add(simSubjectPronounField, 0, 1, width, 1);
		
		WTextField simObjectPronounField = new WTextField(new TranslatableText("gui.cas.gender.object"));
		simObjectPronounField.addTooltip(new TooltipBuilder()
				.add(new TranslatableText("gui.cas.gender.object.tooltip")));
		personalityTallPanel.add(simObjectPronounField, 0, 2, width, 1);
		
		WTextField simDeterminerField = new WTextField(new TranslatableText("gui.cas.gender.determiner"));
		simDeterminerField.addTooltip(new TooltipBuilder()
				.add(new TranslatableText("gui.cas.gender.determiner.tooltip")));
		personalityTallPanel.add(simDeterminerField, 0, 3, width, 1);
		
		WTextField simPossessiveField = new WTextField(new TranslatableText("gui.cas.gender.possessive"));
		simPossessiveField.addTooltip(new TooltipBuilder()
				.add(new TranslatableText("gui.cas.gender.possessive.tooltip")));
		
		WTextField simReflexiveField = new WTextField(new TranslatableText("gui.cas.gender.reflexive"));
		simReflexiveField.addTooltip(new TooltipBuilder()
				.add(new TranslatableText("gui.cas.gender.reflexive.tooltip")));
		
		WTextField[] genderFields = new WTextField[] {
				simGenderIDField, simSubjectPronounField, simObjectPronounField,
				simDeterminerField, simPossessiveField, simReflexiveField
		};
		for (WTextField genderField : genderFields) {
			personalityTallPanel.add(genderField, 0, yOff++, width, 1);
		}
		
		SimsMain.logger.devInfo("Y offset in Personality panel: " + yOff);
		
		personalityPanel = new WScrollPanel(personalityTallPanel);
		settingsPanel.add(personalityPanel, (tab) -> {
			tab.tooltip(new TranslatableText("gui.cas.personality"));
			tab.icon(new ItemIcon(new ItemStack(Items.PINK_DYE))); // FIXME: Use a custom icon!
		});
	}
	
	private void initInventoryPanel() {
		inventoryPanel = new WGridPanel();
		
		inventoryPanel.add(WItemSlot.of(new SimpleInventory(4), 0, 1, 4), 0,0);
		
		inventoryPanel.add(this.createPlayerInventoryPanel(), 0, 5);
		
		settingsPanel.add(inventoryPanel, (tab) -> {
			tab.tooltip(new TranslatableText("gui.cas.inventory"));
			tab.icon(new ItemIcon(new ItemStack(Items.LEATHER_CHESTPLATE)));
		});
	}
	
	private void initPreviewPanel() {
		previewPanel = new WGridPanel();
		
		WTextField firstNameBox = new WTextField(new TranslatableText("gui.cas.first_name"));
		WTextField middleNameBox = new WTextField(new TranslatableText("gui.cas.middle_name"));
		WTextField lastNameBox = new WTextField(new TranslatableText("gui.cas.last_name"));
		
		WButton closeButton = new WButton().setLabel(new TranslatableText("gui.cas.close"))
				.setOnClick(() -> MinecraftClient.getInstance().openScreen(null));
		closeButton.addTooltip(new TooltipBuilder().add(new TranslatableText("gui.cas.finalize.tooltip")));
		
		
		previewPanel.add(firstNameBox,0, 0, 3, 1);
		previewPanel.add(middleNameBox, 3, 0, 3, 1);
		previewPanel.add(lastNameBox,6, 0, 3, 1);
		
		previewPanel.add(closeButton, 0, 10, 4, 1);
		
		settingsPanel.add(previewPanel, (tab) -> {
			tab.tooltip(new TranslatableText("gui.cas.preview"));
			tab.icon(new ItemIcon(new ItemStack(Items.BONE)));
		});
	}
	
	private void initLooksPanel() {
		WGridPanel looksTallPanel = new WGridPanel();
		
		WLabeledSlider breastSlider = new WLabeledSlider(0,100, Axis.VERTICAL,
				new TranslatableText("gui.cas.looks.breasts"));
		
		looksTallPanel.add(breastSlider, 0, 0, 7, 1);
		
		looksPanel = new WScrollPanel(looksTallPanel);
		
		settingsPanel.add(looksPanel, (tab) -> {
			tab.icon(new ItemIcon(new ItemStack(Items.PLAYER_HEAD)));
			tab.tooltip(new TranslatableText("gui.cas.looks"));
		});
	}
	
	@Environment(EnvType.CLIENT)
	public static class Screen extends CottonInventoryScreen<CreateASimGUI> {
		public Screen(CreateASimGUI gui, PlayerInventory inventory) {
			super(gui, inventory.player, TITLE);
		}
		
		@Override
		public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
			super.render(matrices, mouseX, mouseY, partialTicks);
				InventoryScreen.drawEntity(196, 100, 30, 0, 0, this.handler.player); // FIXME: Should draw the SimEntity, not the player!
		}
	}
}
