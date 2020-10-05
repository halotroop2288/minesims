package com.halotroop.sims.entity.simdata;

import com.halotroop.sims.SimsMain;

import java.io.*;

public class SimData implements Serializable {
	public static SimData GENERIC = new SimData();
	// Basics
	/* First name is implied by super class (custom name) */
	public final String lastName; // Optional
	public final LifeStage lifeStage;
	// Voice
	public final float voicePitch; // Pitch modifier for playing back voice samples
	public PersonalityTrait[] traits;
	
	// Favourites
	public Favorites favorites = new Favorites();
	// Gender
	public Gender gender = Gender.GENERIC_UNKNOWN;
	/**
	 * Sex, anatomical, and biological features of the character
	 * <p>
	 * Sex could be quite difficult to implement,
	 * so for now, I'm going to leave it out.
	 */
//	public boolean sitsToPee = true;
//	public boolean canImpregnateOthers = false;
//	public boolean canBecomePregnant = false;
	public float breastSize = 0.0F; // 0 = flat (no breast cuboid rendered) 1 = max (breast y and z size are equal)
	
	public SimData(String lastName, Gender gender, Favorites favorites, float pitch, LifeStage stage) {
		this.lastName = lastName;
		this.gender = gender;
		this.favorites = favorites;
		this.voicePitch = pitch;
		this.lifeStage = stage;
	}
	
	
	private SimData() {
		this("", Gender.GENERIC_UNKNOWN, new Favorites(), 1, LifeStage.YOUNG);
	}
	
	public static SimData deserialize(byte[] bytes) {
		try {
			ByteArrayInputStream b = new ByteArrayInputStream(bytes);
			ObjectInputStream o = new ObjectInputStream(b);
			return (SimData) o.readObject();
		} catch (IOException | NullPointerException | ClassNotFoundException e) {
			SimsMain.logger.error("Failed to deserialize sim data! Was the file edited externally?");
			e.printStackTrace();
		}
		return null;
	}
	
	// TODO: Set artificial limits on traits based on lifeStage
	public void setTraits(PersonalityTrait[] traits) {
		this.traits = traits;
	}
	
	public byte[] serialize() {
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(b);
			o.writeObject(this);
			return b.toByteArray();
		} catch (IOException | NullPointerException e) {
			SimsMain.logger.error("Failed to serialize sim data! Something is coded wrong!");
			e.printStackTrace();
		}
		return null;
	}
	
	public enum LifeStage {
		BABY, TODDLER, CHILD, TEEN,
		YOUNG, ADULT, ELDER, GHOST;
	}
}
