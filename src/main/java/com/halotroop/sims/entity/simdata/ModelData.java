package com.halotroop.sims.entity.simdata;

import net.minecraft.client.util.math.Vector3f;

import java.io.Serializable;

public class ModelData implements Serializable {
	protected static final ModelData DEFAULT = new ModelData(true);
	protected static final ModelData DEFAULT_SLIM = new ModelData(false);
	
	
	ModelPartData
			head,
			torso,
			arms,
			legs,
			cape,
			ears,
			breasts;
	
	private ModelData(boolean slim) {
		this();
	}
	
	private ModelData() {
	
	}
	
	public static class ModelPartData {
		Vector3f size, pos, pivot;
		
		public ModelPartData() {
		
		}
	}
}
