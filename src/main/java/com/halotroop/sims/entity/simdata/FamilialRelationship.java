package com.halotroop.sims.entity.simdata;

// Not sure how I'm going to implement this, yet. Probably with UUIDs?
public enum FamilialRelationship {
	SIBLING, // Ex: Brother, Sister
	PARENT, CHILD, // Ex: Mother, Father || Daughter, Son
	PIBLING, NIBLING, // Ex: Aunt, Uncle || Niece, Nephew
	
	COUSIN; // Ex: In family tree but none of the above.
}
