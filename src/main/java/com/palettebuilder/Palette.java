package com.palettebuilder;

import java.util.List;
import java.util.Random;
import net.minecraft.world.level.block.Block;

public record Palette(String name, List<Entry> entries) {
	public record Entry(Block block, int weight) {
	}

	public Block pick(Random random) {
		int total = 0;
		for (Entry e : entries) {
			total += e.weight();
		}
		int roll = random.nextInt(Math.max(1, total));
		for (Entry e : entries) {
			roll -= e.weight();
			if (roll < 0) {
				return e.block();
			}
		}
		return entries.get(0).block();
	}
}
