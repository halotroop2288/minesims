package com.halotroop.sims.entity;

import com.halotroop.sims.entity.simdata.SimData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Arrays;

public class SimEntity extends MobEntity implements Inventory {
	public SimData simData;
	
	public SimEntity(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
		this.simData = SimData.GENERIC;
	}
	
	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		tag.putByteArray("SimData", simData.serialize());
		super.writeCustomDataToTag(tag);
	}
	
	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		this.simData = SimData.deserialize(tag.getByteArray("SimData"));
		super.readCustomDataFromTag(tag);
	}
	
	ItemStack[] outfit = new ItemStack[4];
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
	}
	
	@Override
	protected void initGoals() {
		super.initGoals();
	}
	
	@Override
	protected float getSoundPitch() {
		return MathHelper.clamp(simData.voicePitch, 0, 2);
	}
	
	@Override
	public int size() {
		return outfit.length;
	}
	
	@Override
	public boolean isEmpty() {
		for (ItemStack slot : outfit) {
			if (!slot.isEmpty()) return false;
		}
		return true;
	}
	
	@Override
	public ItemStack getStack(int slot) {
		return outfit[slot];
	}
	
	@Override
	public ItemStack removeStack(int slot, int amount) {
		outfit[slot].decrement(amount);
		return outfit[slot];
	}
	
	@Override
	public ItemStack removeStack(int slot) {
		outfit[slot] = ItemStack.EMPTY;
		return outfit[slot];
	}
	
	@Override
	public void setStack(int slot, ItemStack stack) {
		outfit[slot] = stack;
	}
	
	@Override
	public void markDirty() {
	
	}
	
	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return false;
	}
	
	@Override
	public void clear() {
		Arrays.fill(outfit, ItemStack.EMPTY);
	}
}
