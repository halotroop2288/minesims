package com.halotroop.sims.client.gui;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.control.Control;
import net.minecraft.client.util.math.MatrixStack;

public class CASBackgroundControl<C extends CASBackgroundControl<C>> extends Control<C> {
	public CASBackgroundControl(int x, int y) {
		size(111, 19);
		position(144, 0);
	}
}
