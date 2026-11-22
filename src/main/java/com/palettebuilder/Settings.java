package com.palettebuilder;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import net.fabricmc.loader.api.FabricLoader;

public final class Settings {
	private Settings() {
	}

	public static boolean wall = false;
	public static boolean follow = true;
	public static boolean battlements = true;
	public static int pathPalette = 0;
	public static int wallPalette = 0;
	public static int length = 24;
	public static int width = 3;
	public static int height = 5;
	public static int thickness = 2;
	public static int foundation = 1;
	public static int speed = 5;
	public static String customText = "stone*3, cobblestone*3, mossy_cobblestone*2, andesite";

	private static Path file() {
		return FabricLoader.getInstance().getConfigDir().resolve("palettebuilder.properties");
	}

	private static int clamp(int v, int min, int max) {
		return Math.max(min, Math.min(max, v));
	}

	private static int geti(Properties p, String key, int def) {
		try {
			return Integer.parseInt(p.getProperty(key, Integer.toString(def)).trim());
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public static void load() {
		try {
			Path f = file();
			if (!Files.exists(f)) {
				return;
			}
			Properties p = new Properties();
			try (InputStream in = Files.newInputStream(f)) {
				p.load(in);
			}
			wall = Boolean.parseBoolean(p.getProperty("wall", "false"));
			follow = Boolean.parseBoolean(p.getProperty("follow", "true"));
			battlements = Boolean.parseBoolean(p.getProperty("battlements", "true"));
			pathPalette = Math.max(0, geti(p, "pathPalette", 0));
			wallPalette = Math.max(0, geti(p, "wallPalette", 0));
			length = clamp(geti(p, "length", 24), 1, 256);
			width = clamp(geti(p, "width", 3), 1, 15);
			height = clamp(geti(p, "height", 5), 1, 32);
			thickness = clamp(geti(p, "thickness", 2), 1, 8);
			foundation = clamp(geti(p, "foundation", 1), 0, 4);
			speed = clamp(geti(p, "speed", 5), 1, 20);
			customText = p.getProperty("customText", customText);
		} catch (Exception ignored) {
			// bad config file: keep defaults
		}
	}

	public static void save() {
		try {
			Properties p = new Properties();
			p.setProperty("wall", Boolean.toString(wall));
			p.setProperty("follow", Boolean.toString(follow));
			p.setProperty("battlements", Boolean.toString(battlements));
			p.setProperty("pathPalette", Integer.toString(pathPalette));
			p.setProperty("wallPalette", Integer.toString(wallPalette));
			p.setProperty("length", Integer.toString(length));
			p.setProperty("width", Integer.toString(width));
			p.setProperty("height", Integer.toString(height));
			p.setProperty("thickness", Integer.toString(thickness));
			p.setProperty("foundation", Integer.toString(foundation));
			p.setProperty("speed", Integer.toString(speed));
			p.setProperty("customText", customText);
			try (OutputStream out = Files.newOutputStream(file())) {
				p.store(out, "Palette Builder");
			}
		} catch (Exception ignored) {
			// not fatal
		}
	}

	public static int palette(boolean forWall) {
		return forWall ? wallPalette : pathPalette;
	}

	public static void setPalette(boolean forWall, int index) {
		if (forWall) {
			wallPalette = index;
		} else {
			pathPalette = index;
		}
	}

	public static int clampLength(int v) {
		return clamp(v, 1, 256);
	}

	public static int clampWidth(int v) {
		return clamp(v, 1, 15);
	}

	public static int clampHeight(int v) {
		return clamp(v, 1, 32);
	}

	public static int clampThickness(int v) {
		return clamp(v, 1, 8);
	}

	public static int clampFoundation(int v) {
		return clamp(v, 0, 4);
	}

	public static int clampSpeed(int v) {
		return clamp(v, 1, 20);
	}
}
