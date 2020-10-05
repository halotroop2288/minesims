package com.halotroop.sims.entity.simdata;

import blue.endless.jankson.annotation.Nullable;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

/**
 * Creating new goals for custom traits is encouraged.
 */
public enum PersonalityTrait {
	;
	
	public final Text nameText, description;
	public final Goal[] goals;
	public final PersonalityTrait[] conflicts;
	
	/**
	 * For personality traits with
	 *
	 * @param description The description that will be shown in the tooltip when the trait is hovered over
	 * @param goals       The goals that will be added to the entity if it has this trait
	 */
	PersonalityTrait(Text description, Goal... goals) {
		this(description, goals, null, null);
	}
	
	/**
	 * For personality traits with
	 *
	 * @param description The description that will be shown in the tooltip when the trait is hovered over
	 * @param goal        The goal that will be added to the entity if it has this trait
	 * @param conflicts   Any other traits that this one is / should be incompatible with
	 */
	PersonalityTrait(Text description, Goal goal, @Nullable PersonalityTrait... conflicts) {
		this(description, new Goal[]{goal}, conflicts);
	}
	
	/**
	 * For personality traits with
	 *
	 * @param description The description that will be shown in the tooltip when the trait is hovered over
	 * @param goals       The goals that will be added to the entity if it has this trait
	 * @param conflicts   Any other traits that this one is / should be incompatible with
	 */
	PersonalityTrait(Text description, Goal[] goals, @Nullable PersonalityTrait... conflicts) { // TODO: Add icons
		this.description = description;
		this.nameText = new TranslatableText("gui.cas.personality.trait." + name());
		this.goals = goals;
		this.conflicts = conflicts;
	}
}
