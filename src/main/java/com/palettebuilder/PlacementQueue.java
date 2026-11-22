package com.palettebuilder;

import java.util.ArrayDeque;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public final class PlacementQueue {
	private PlacementQueue() {
	}

	public record Placement(BlockPos pos, String blockId) {
	}

	private static final ArrayDeque<Placement> QUEUE = new ArrayDeque<>();
	private static int total = 0;
	private static int ticks = 0;

	public static void enqueue(List<Placement> placements) {
		if (QUEUE.isEmpty()) {
			total = 0;
		}
		QUEUE.addAll(placements);
		total += placements.size();
	}

	public static int clear() {
		int left = QUEUE.size();
		QUEUE.clear();
		total = 0;
		return left;
	}

	public static void tick(Minecraft mc) {
		if (QUEUE.isEmpty()) {
			return;
		}
		if (mc.player == null || mc.getConnection() == null) {
			QUEUE.clear();
			total = 0;
			return;
		}
		int n = Settings.speed;
		while (n-- > 0 && !QUEUE.isEmpty()) {
			Placement p = QUEUE.poll();
			BlockPos pos = p.pos();
			mc.getConnection().sendCommand("setblock " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " " + p.blockId());
		}
		ticks++;
		if (QUEUE.isEmpty()) {
			mc.player.displayClientMessage(Component.literal("Palette Builder: finished (" + total + " blocks)"), true);
		} else if (ticks % 10 == 0) {
			mc.player.displayClientMessage(Component.literal("Palette Builder: " + (total - QUEUE.size()) + " / " + total), true);
		}
	}
}
