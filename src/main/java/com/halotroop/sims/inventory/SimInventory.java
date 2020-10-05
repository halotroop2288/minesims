package com.halotroop.sims.inventory;

import com.google.common.collect.ImmutableList;
import com.halotroop.sims.entity.SimEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;

import java.util.Iterator;
import java.util.List;

public class SimInventory implements Inventory {
	public final DefaultedList<ItemStack> armor;
	public final DefaultedList<ItemStack> hands;
	public final SimEntity owner;
	private final List<DefaultedList<ItemStack>> combinedInventory;
	
	public SimInventory(SimEntity owner) {
		armor = DefaultedList.ofSize(4, ItemStack.EMPTY);
		hands = DefaultedList.ofSize(2, ItemStack.EMPTY);
		this.combinedInventory = ImmutableList.of(this.armor, this.hands);
		this.owner = owner;
	}
	
	@Override
	public int size() {
		return this.armor.size() + this.hands.size();
	}
	
	/**
	 * @return true if ALL the ItemStacks in the inventory are empty
	 */
	@Override
	public boolean isEmpty() {
		for (DefaultedList<ItemStack> list : combinedInventory) {
			for (ItemStack stack : list) {
				if (!stack.isEmpty()) return false;
			}
		}
		return true;
	}
	
	@Override
	public ItemStack getStack(int slot) {
		DefaultedList<ItemStack> list = null;
		
		DefaultedList<ItemStack> defaultedList;
		for (Iterator<DefaultedList<ItemStack>> var3 = this.combinedInventory.iterator(); var3.hasNext(); slot -= defaultedList.size()) {
			defaultedList = var3.next();
			if (slot < defaultedList.size()) {
				list = defaultedList;
				break;
			}
		}
		
		return list == null ? ItemStack.EMPTY : list.get(slot);
	}
	
	public ItemStack getArmorStack(int slot) {
		return this.armor.get(slot);
	}
	
	public ItemStack getHandStack(Hand hand) {
		return hand == Hand.MAIN_HAND ? hands.get(0) : hands.get(1);
	}
	
	@Override
	public ItemStack removeStack(int slot, int amount) {
		List<ItemStack> list = null;
		
		DefaultedList<ItemStack> defaultedList;
		for (Iterator<DefaultedList<ItemStack>> var4 = this.combinedInventory.iterator();
		     var4.hasNext(); slot -= defaultedList.size()) {
			defaultedList = var4.next();
			if (slot < defaultedList.size()) {
				list = defaultedList;
				break;
			}
		}
		
		return list != null && !(list.get(slot)).isEmpty()
				? Inventories.splitStack(list, slot, amount)
				: ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack removeStack(int slot) {
		DefaultedList<ItemStack> defaultedList = null;
		
		DefaultedList<ItemStack> defaultedList2;
		for (Iterator<DefaultedList<ItemStack>> var3 = this.combinedInventory.iterator();
		     var3.hasNext(); slot -= defaultedList2.size()) {
			defaultedList2 = var3.next();
			if (slot < defaultedList2.size()) {
				defaultedList = defaultedList2;
				break;
			}
		}
		
		if (defaultedList != null && !(defaultedList.get(slot)).isEmpty()) {
			ItemStack itemStack = defaultedList.get(slot);
			defaultedList.set(slot, ItemStack.EMPTY);
			return itemStack;
		} else {
			return ItemStack.EMPTY;
		}
	}
	
	@Override
	public void setStack(int slot, ItemStack stack) {
		DefaultedList<ItemStack> defaultedList = null;
		
		DefaultedList<ItemStack> defaultedList2;
		for (Iterator<DefaultedList<ItemStack>> var4 = this.combinedInventory.iterator();
		     var4.hasNext(); slot -= defaultedList2.size()) {
			defaultedList2 = var4.next();
			if (slot < defaultedList2.size()) {
				defaultedList = defaultedList2;
				break;
			}
		}
		
		if (defaultedList != null) {
			defaultedList.set(slot, stack);
		}
	}
	
	@Override
	public void markDirty() {
	}
	
	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return player.squaredDistanceTo(this.owner) <= 64.0D;
	}
	
	@Override
	public void clear() {
		for (List<ItemStack> list : this.combinedInventory) {
			list.clear();
		}
	}
}
