package com.halotroop.sims.entity.simdata;

import java.io.Serializable;

public class Gender implements Serializable {
	// Common presets | Don't kill me, it's not cis-normative, it's just to make sim-creation faster!
	public static final Gender GENERIC_MALE = new Gender("male", "he", "him",
			"his", "his", "himself");
	public static final Gender GENERIC_FEMALE = new Gender("female", "she", "her",
			"her", "hers", "herself");
	public static final Gender GENERIC_UNKNOWN = new Gender();
	
	public String genderID = "unknown"; // "They identify as *unknown*."
	public String subjectPronoun = "they"; // "*They* went to the park"
	public String objectPronoun = "them"; // "I went with *them*."
	public String possessiveDeterminer = "their"; // "They brought *their* frisbee"
	public String possessivePronoun = "theirs"; // "At least I think it was *theirs*"
	public String reflexiveSingular = "themself"; // "They threw the frisbee to themself."
	
	public Gender(String genderID, String subject, String object, String posDeterminer, String possessive,
	              String reflexive) {
		this.genderID = genderID;
		this.subjectPronoun = subject;
		this.objectPronoun = object;
		this.possessiveDeterminer = posDeterminer;
		this.possessivePronoun = possessive;
		this.reflexiveSingular = reflexive;
	}
	
	private Gender() {
	} // GENERIC_UNKNOWN
	
	public Gender genderID(String genderID) {
		this.genderID = genderID;
		return this;
	}
	
	public Gender subjectPronoun(String subjectPronoun) {
		this.subjectPronoun = subjectPronoun;
		return this;
	}
	
	public Gender objectPronoun(String objectPronoun) {
		this.objectPronoun = objectPronoun;
		return this;
	}
	
	public Gender possessiveDeterminer(String possessiveDeterminer) {
		this.possessiveDeterminer = possessiveDeterminer;
		return this;
	}
	
	public Gender possessivePronoun(String possessivePronoun) {
		this.possessivePronoun = possessivePronoun;
		return this;
	}
	
	public Gender reflexiveSingular(String reflexiveSingular) {
		this.reflexiveSingular = reflexiveSingular;
		return this;
	}
}