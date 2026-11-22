package com.palettebuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class Palettes {
	private Palettes() {
	}

	public static final List<Palette> WALLS = new ArrayList<>();
	public static final List<Palette> PATHS = new ArrayList<>();
	private static Map<String, Block> blockMap;

	private static Palette.Entry e(Block b, int w) {
		return new Palette.Entry(b, w);
	}

	private static Palette pal(String name, Palette.Entry... entries) {
		return new Palette(name, List.of(entries));
	}

	static {
		// ---------- WALLS ----------
		WALLS.add(pal("Old Castle", e(Blocks.STONE, 3), e(Blocks.COBBLESTONE, 4), e(Blocks.MOSSY_COBBLESTONE, 3), e(Blocks.ANDESITE, 2)));
		WALLS.add(pal("Mossy Ruins", e(Blocks.MOSSY_COBBLESTONE, 4), e(Blocks.MOSSY_STONE_BRICKS, 3), e(Blocks.COBBLESTONE, 2), e(Blocks.STONE_BRICKS, 1), e(Blocks.MOSS_BLOCK, 1)));
		WALLS.add(pal("Castle Bricks", e(Blocks.STONE_BRICKS, 5), e(Blocks.CRACKED_STONE_BRICKS, 2), e(Blocks.MOSSY_STONE_BRICKS, 2), e(Blocks.STONE, 1)));
		WALLS.add(pal("Dark Keep", e(Blocks.DEEPSLATE_BRICKS, 4), e(Blocks.COBBLED_DEEPSLATE, 3), e(Blocks.CRACKED_DEEPSLATE_BRICKS, 2), e(Blocks.DEEPSLATE_TILES, 2), e(Blocks.TUFF, 1)));
		WALLS.add(pal("Granite & Diorite", e(Blocks.ANDESITE, 3), e(Blocks.GRANITE, 2), e(Blocks.DIORITE, 2), e(Blocks.POLISHED_ANDESITE, 2), e(Blocks.STONE, 1)));
		WALLS.add(pal("Blackstone Fort", e(Blocks.BLACKSTONE, 3), e(Blocks.POLISHED_BLACKSTONE_BRICKS, 4), e(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, 2), e(Blocks.POLISHED_BLACKSTONE, 2)));
		WALLS.add(pal("Desert Sandstone", e(Blocks.SANDSTONE, 4), e(Blocks.CUT_SANDSTONE, 3), e(Blocks.SMOOTH_SANDSTONE, 2), e(Blocks.TERRACOTTA, 1)));
		WALLS.add(pal("Timber Frame", e(Blocks.OAK_PLANKS, 4), e(Blocks.SPRUCE_PLANKS, 3), e(Blocks.DARK_OAK_PLANKS, 2), e(Blocks.STRIPPED_SPRUCE_LOG, 1)));
		WALLS.add(pal("Mud Village", e(Blocks.MUD_BRICKS, 4), e(Blocks.PACKED_MUD, 3), e(Blocks.TERRACOTTA, 2)));
		WALLS.add(pal("Red Brick", e(Blocks.BRICKS, 5), e(Blocks.STONE_BRICKS, 1), e(Blocks.COBBLESTONE, 1), e(Blocks.TERRACOTTA, 1)));
		WALLS.add(pal("Tuff Stronghold", e(Blocks.TUFF, 4), e(Blocks.TUFF_BRICKS, 3), e(Blocks.POLISHED_TUFF, 2), e(Blocks.COBBLED_DEEPSLATE, 1)));
		WALLS.add(pal("Nether Brick", e(Blocks.NETHER_BRICKS, 5), e(Blocks.CRACKED_NETHER_BRICKS, 2), e(Blocks.RED_NETHER_BRICKS, 2), e(Blocks.BLACKSTONE, 1)));
		WALLS.add(pal("Wild Stone & Moss", e(Blocks.STONE, 4), e(Blocks.MOSSY_COBBLESTONE, 2), e(Blocks.COBBLESTONE, 2), e(Blocks.MOSS_BLOCK, 1), e(Blocks.ANDESITE, 1)));

		// ---------- PATHS ----------
		PATHS.add(pal("Cobble Country Road", e(Blocks.COBBLESTONE, 4), e(Blocks.GRAVEL, 3), e(Blocks.MOSSY_COBBLESTONE, 2), e(Blocks.ANDESITE, 2), e(Blocks.STONE, 1)));
		PATHS.add(pal("Dirt Trail", e(Blocks.DIRT_PATH, 5), e(Blocks.COARSE_DIRT, 3), e(Blocks.PODZOL, 1), e(Blocks.ROOTED_DIRT, 1), e(Blocks.GRAVEL, 1)));
		PATHS.add(pal("Mossy Stone Path", e(Blocks.MOSSY_COBBLESTONE, 3), e(Blocks.MOSSY_STONE_BRICKS, 2), e(Blocks.COBBLESTONE, 3), e(Blocks.MOSS_BLOCK, 2), e(Blocks.STONE, 1)));
		PATHS.add(pal("Castle Courtyard", e(Blocks.STONE_BRICKS, 4), e(Blocks.CRACKED_STONE_BRICKS, 2), e(Blocks.MOSSY_STONE_BRICKS, 2), e(Blocks.STONE, 2), e(Blocks.ANDESITE, 1)));
		PATHS.add(pal("Gravel Lane", e(Blocks.GRAVEL, 5), e(Blocks.COBBLESTONE, 2), e(Blocks.ANDESITE, 2), e(Blocks.STONE, 1), e(Blocks.COARSE_DIRT, 1)));
		PATHS.add(pal("Wood Boardwalk", e(Blocks.OAK_PLANKS, 3), e(Blocks.SPRUCE_PLANKS, 3), e(Blocks.DARK_OAK_PLANKS, 2), e(Blocks.BIRCH_PLANKS, 1)));
		PATHS.add(pal("Desert Road", e(Blocks.SMOOTH_SANDSTONE, 3), e(Blocks.CUT_SANDSTONE, 3), e(Blocks.SANDSTONE, 2), e(Blocks.TERRACOTTA, 1)));
		PATHS.add(pal("Dark Road", e(Blocks.POLISHED_BLACKSTONE_BRICKS, 3), e(Blocks.BLACKSTONE, 3), e(Blocks.COBBLED_DEEPSLATE, 2), e(Blocks.DEEPSLATE_BRICKS, 2)));
		PATHS.add(pal("Mud Village Path", e(Blocks.MUD_BRICKS, 3), e(Blocks.PACKED_MUD, 3), e(Blocks.COARSE_DIRT, 2), e(Blocks.MUD, 1), e(Blocks.DIRT_PATH, 2)));
		PATHS.add(pal("Granite Plaza", e(Blocks.POLISHED_ANDESITE, 3), e(Blocks.POLISHED_GRANITE, 2), e(Blocks.POLISHED_DIORITE, 2), e(Blocks.SMOOTH_STONE, 2), e(Blocks.STONE, 1)));
		PATHS.add(pal("Deepslate Walk", e(Blocks.DEEPSLATE_TILES, 3), e(Blocks.DEEPSLATE_BRICKS, 3), e(Blocks.COBBLED_DEEPSLATE, 2), e(Blocks.POLISHED_DEEPSLATE, 2), e(Blocks.TUFF, 1)));
		PATHS.add(pal("Red Brick Street", e(Blocks.BRICKS, 5), e(Blocks.STONE_BRICKS, 1), e(Blocks.COBBLESTONE, 1), e(Blocks.GRAVEL, 1)));
	}

	public static List<Palette> list(boolean wall) {
		return wall ? WALLS : PATHS;
	}

	/** Number of selectable palettes, including the trailing "Custom" slot. */
	public static int count(boolean wall) {
		return list(wall).size() + 1;
	}

	public static boolean isCustom(boolean wall, int index) {
		return index >= list(wall).size();
	}

	public static String describe(boolean wall, int index) {
		if (isCustom(wall, index)) {
			return "Custom: type block ids in the box below";
		}
		Palette p = list(wall).get(index);
		StringBuilder sb = new StringBuilder(p.name()).append(": ");
		boolean first = true;
		for (Palette.Entry en : p.entries()) {
			if (!first) {
				sb.append(", ");
			}
			first = false;
			sb.append(BuiltInRegistries.BLOCK.getKey(en.block()).getPath()).append(" x").append(en.weight());
		}
		String s = sb.toString();
		return s.length() > 62 ? s.substring(0, 59) + "..." : s;
	}

	private static Map<String, Block> blockMap() {
		if (blockMap == null) {
			blockMap = new HashMap<>();
			for (Block b : BuiltInRegistries.BLOCK) {
				if (b.defaultBlockState().isAir()) {
					continue;
				}
				String key = BuiltInRegistries.BLOCK.getKey(b).toString();
				blockMap.put(key, b);
				if (key.startsWith("minecraft:")) {
					blockMap.put(key.substring("minecraft:".length()), b);
				}
			}
		}
		return blockMap;
	}

	/** Parses "stone*3, mossy_cobblestone, minecraft:andesite*2". Unknown entries are added to problems. */
	public static Palette parseCustom(String text, List<String> problems) {
		Map<String, Block> map = blockMap();
		List<Palette.Entry> out = new ArrayList<>();
		for (String raw : text.split(",")) {
			String part = raw.trim().toLowerCase(Locale.ROOT);
			if (part.isEmpty()) {
				continue;
			}
			int weight = 1;
			int star = part.indexOf('*');
			if (star > 0) {
				try {
					weight = Math.max(1, Math.min(100, Integer.parseInt(part.substring(star + 1).trim())));
				} catch (NumberFormatException ex) {
					problems.add(part);
					continue;
				}
				part = part.substring(0, star).trim();
			}
			Block b = map.get(part);
			if (b == null) {
				problems.add(part);
				continue;
			}
			out.add(new Palette.Entry(b, weight));
		}
		if (out.isEmpty()) {
			return null;
		}
		return new Palette("Custom", out);
	}
}
