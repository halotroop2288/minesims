package com.halotroop.sims.entity;

import com.halotroop.sims.SimsMain;
import com.halotroop.sims.client.gui.screen.CASScreen;
import com.halotroop.sims.entity.simdata.SimData;
import com.halotroop.sims.inventory.SimInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SimEntity extends MobEntity {
	public SimData data;
	public SimInventory inventory;
	
	public SimEntity(EntityType<? extends SimEntity> type, World world) {
		super(type, world);
		this.data = SimData.GENERIC;
		this.inventory = new SimInventory(this);
	}
	
	public void setPos(Entity entity) {
		super.setPos(entity.getX(), entity.getY(), entity.getZ());
	}
	
	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		tag.putByteArray("SimData", data.serialize());
		super.writeCustomDataToTag(tag);
	}
	
	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		this.data = SimData.deserialize(tag.getByteArray("SimData"));
		super.readCustomDataFromTag(tag);
	}
	
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
		return MathHelper.clamp(data.voicePitch, 0, 2);
	}
	
	public Identifier getSkinTexture() {
		return DefaultSkinHelper.getTexture();
	}
}
