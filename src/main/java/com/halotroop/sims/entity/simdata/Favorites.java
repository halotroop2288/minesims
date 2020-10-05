package com.halotroop.sims.entity.simdata;

import blue.endless.jankson.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.DyeColor;

import java.io.Serializable;

/**
 * A sim's favorite things, to be stored in their SimData.
 */
public class Favorites implements Serializable {
	@Nullable
	public DyeColor favColor = DyeColor.WHITE;
	@Nullable
	public Item favFood = Items.APPLE;
	@Nullable
	public Block favBlock = Blocks.DIRT;
	@Nullable
	public MusicDiscItem favMusic = (MusicDiscItem) Items.MUSIC_DISC_CAT;
	
	public Favorites setFavColor(DyeColor favColor) {
		this.favColor = favColor;
		return this;
	}
	
	public Favorites setFavFood(Item favFood) {
		this.favFood = favFood;
		return this;
	}
	
	public Favorites setFavBlock(Block favBlock) {
		this.favBlock = favBlock;
		return this;
	}
	
	public Favorites setFavMusic(MusicDiscItem favMusic) {
		this.favMusic = favMusic;
		return this;
	}
}
