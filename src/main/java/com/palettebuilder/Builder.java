package com.palettebuilder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class Builder {
	private Builder() {
	}

	private static final int MAX_BLOCKS = 60000;
	private static final Deque<List<PlacementQueue.Placement>> UNDO = new ArrayDeque<>();

	private static void say(LocalPlayer player, String msg) {
		player.sendSystemMessage(Component.literal("Palette Builder: " + msg));
	}

	private static String id(Block b) {
		return BuiltInRegistries.BLOCK.getKey(b).toString();
	}

	private static boolean isGround(BlockState s) {
		return !s.isAir() && s.getFluidState().isEmpty() && !s.canBeReplaced() && !s.is(BlockTags.LEAVES);
	}

	/** Finds the top solid block near aroundY in this column. */
	private static int surfaceY(ClientLevel level, int x, int z, int aroundY) {
		BlockPos.MutableBlockPos p = new BlockPos.MutableBlockPos();
		for (int y = aroundY + 3; y >= aroundY - 6; y--) {
			p.set(x, y, z);
			if (isGround(level.getBlockState(p))) {
				return y;
			}
		}
		return aroundY;
	}

	public static void build(Minecraft mc) {
		LocalPlayer player = mc.player;
		ClientLevel level = mc.level;
		if (player == null || level == null || mc.getConnection() == null) {
			return;
		}

		boolean wall = Settings.wall;
		int index = Settings.palette(wall);
		Palette palette;
		if (Palettes.isCustom(wall, index)) {
			List<String> problems = new ArrayList<>();
			palette = Palettes.parseCustom(Settings.customText, problems);
			if (!problems.isEmpty()) {
				say(player, "unknown blocks ignored: " + String.join(", ", problems));
			}
			if (palette == null) {
				say(player, "custom palette has no valid blocks, nothing built.");
				return;
			}
		} else {
			palette = Palettes.list(wall).get(index);
		}

		Direction d = player.getDirection();
		Direction r = d.getClockWise();
		int ox = player.getBlockX();
		int oz = player.getBlockZ();
		int oy = player.getBlockY() - 1;
		boolean follow = Settings.follow;
		int len = Settings.length;
		int across = wall ? Settings.thickness : Settings.width;

		Random rng = new Random();
		List<PlacementQueue.Placement> place = new ArrayList<>();
		List<PlacementQueue.Placement> undo = new ArrayList<>();
		int centerY = oy;

		for (int i = 0; i < len; i++) {
			int cx = ox + d.getStepX() * (i + 1);
			int cz = oz + d.getStepZ() * (i + 1);
			int cy = follow ? surfaceY(level, cx, cz, centerY) : oy;
			centerY = cy;

			for (int k = 0; k < across; k++) {
				int off = k - (across - 1) / 2;
				int x = cx + r.getStepX() * off;
				int z = cz + r.getStepZ() * off;
				int ground = follow ? surfaceY(level, x, z, cy) : oy;

				int bottom = ground;
				int top = ground;
				if (wall) {
					bottom = ground + 1 - Settings.foundation;
					top = ground + Settings.height;
				}
				for (int y = bottom; y <= top; y++) {
					if (wall && Settings.battlements && y == top && (i & 1) == 1) {
						continue;
					}
					BlockPos pos = new BlockPos(x, y, z);
					Block target = palette.pick(rng);
					Block prev = level.getBlockState(pos).getBlock();
					if (prev == target) {
						continue;
					}
					place.add(new PlacementQueue.Placement(pos, id(target)));
					undo.add(new PlacementQueue.Placement(pos, id(prev)));
					if (place.size() > MAX_BLOCKS) {
						say(player, "too big (over " + MAX_BLOCKS + " blocks). Reduce the size.");
						return;
					}
				}
			}
		}

		if (place.isEmpty()) {
			say(player, "nothing to place.");
			return;
		}
		UNDO.push(undo);
		while (UNDO.size() > 10) {
			UNDO.removeLast();
		}
		PlacementQueue.enqueue(place);
		say(player, "queued " + place.size() + " blocks (" + (wall ? "wall" : "path") + ", " + palette.name()
				+ "). Needs command permission; stay close so chunks stay loaded.");
	}

	public static void undo(Minecraft mc) {
		LocalPlayer player = mc.player;
		if (player == null) {
			return;
		}
		List<PlacementQueue.Placement> last = UNDO.poll();
		if (last == null) {
			say(player, "nothing to undo.");
			return;
		}
		PlacementQueue.clear();
		List<PlacementQueue.Placement> reversed = new ArrayList<>(last);
		Collections.reverse(reversed);
		PlacementQueue.enqueue(reversed);
		say(player, "undoing " + reversed.size() + " blocks (restores block types, not block states).");
	}
}
