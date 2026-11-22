package com.palettebuilder;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class BuilderScreen extends Screen {
	private static final int ROW = 20;

	public BuilderScreen() {
		super(Component.literal("Palette Builder"));
	}

	private void label(int x, int y, int w, String text) {
		addRenderableWidget(new StringWidget(x, y, w, 9, Component.literal(text), this.font));
	}

	private void button(int x, int y, int w, String text, Runnable action) {
		addRenderableWidget(Button.builder(Component.literal(text), b -> action.run()).bounds(x, y, w, 20).build());
	}

	private void step(int x, int y, String text, int delta, IntSupplier get, IntConsumer set, IntUnaryOperator clamp) {
		button(x, y, 26, text, () -> {
			set.accept(clamp.applyAsInt(get.getAsInt() + delta));
			Settings.save();
			rebuildWidgets();
		});
	}

	private void param(int cx, int y, String name, IntSupplier get, IntConsumer set, IntUnaryOperator clamp) {
		label(cx - 155, y + 6, 120, name);
		step(cx - 5, y, "-5", -5, get, set, clamp);
		step(cx + 23, y, "-1", -1, get, set, clamp);
		label(cx + 57, y + 6, 30, Integer.toString(get.getAsInt()));
		step(cx + 89, y, "+1", 1, get, set, clamp);
		step(cx + 117, y, "+5", 5, get, set, clamp);
	}

	@Override
	protected void init() {
		int cx = this.width / 2;
		int y = Math.max(4, (this.height - 250) / 2);

		label(cx - 60, y, 120, "Palette Builder");
		y += 14;

		button(cx - 155, y, 150, "Mode: " + (Settings.wall ? "WALL" : "PATH"), () -> {
			Settings.wall = !Settings.wall;
			Settings.save();
			rebuildWidgets();
		});
		button(cx + 5, y, 150, "Follow terrain: " + (Settings.follow ? "ON" : "OFF"), () -> {
			Settings.follow = !Settings.follow;
			Settings.save();
			rebuildWidgets();
		});
		y += ROW + 2;

		final boolean wall = Settings.wall;
		final int count = Palettes.count(wall);
		int current = Math.min(Settings.palette(wall), count - 1);
		final int cur = current;
		button(cx - 155, y, 150, "< Previous palette", () -> {
			Settings.setPalette(wall, (cur + count - 1) % count);
			Settings.save();
			rebuildWidgets();
		});
		button(cx + 5, y, 150, "Next palette >", () -> {
			Settings.setPalette(wall, (cur + 1) % count);
			Settings.save();
			rebuildWidgets();
		});
		y += ROW + 2;

		label(cx - 155, y, 310, "(" + (cur + 1) + "/" + count + ") " + Palettes.describe(wall, cur));
		y += 14;

		param(cx, y, "Length", () -> Settings.length, v -> Settings.length = v, Settings::clampLength);
		y += ROW + 1;
		if (wall) {
			param(cx, y, "Height", () -> Settings.height, v -> Settings.height = v, Settings::clampHeight);
			y += ROW + 1;
			param(cx, y, "Thickness", () -> Settings.thickness, v -> Settings.thickness = v, Settings::clampThickness);
			y += ROW + 1;
			param(cx, y, "Foundation depth", () -> Settings.foundation, v -> Settings.foundation = v, Settings::clampFoundation);
			y += ROW + 1;
			button(cx - 155, y, 310, "Battlements on top: " + (Settings.battlements ? "ON" : "OFF"), () -> {
				Settings.battlements = !Settings.battlements;
				Settings.save();
				rebuildWidgets();
			});
			y += ROW + 1;
		} else {
			param(cx, y, "Width", () -> Settings.width, v -> Settings.width = v, Settings::clampWidth);
			y += ROW + 1;
		}
		param(cx, y, "Blocks per tick", () -> Settings.speed, v -> Settings.speed = v, Settings::clampSpeed);
		y += ROW + 2;

		label(cx - 155, y, 310, "Custom palette (last in list): stone*3, mossy_cobblestone, andesite*2");
		y += 11;
		EditBox custom = new EditBox(this.font, cx - 155, y, 310, 18, Component.literal("Custom palette"));
		custom.setMaxLength(500);
		custom.setValue(Settings.customText);
		custom.setResponder(s -> Settings.customText = s);
		addRenderableWidget(custom);
		y += 24;

		button(cx - 155, y, 76, "Build", () -> {
			Settings.save();
			Minecraft mc = Minecraft.getInstance();
			mc.setScreen(null);
			Builder.build(mc);
		});
		button(cx - 76, y, 76, "Undo last", () -> {
			Settings.save();
			Minecraft mc = Minecraft.getInstance();
			mc.setScreen(null);
			Builder.undo(mc);
		});
		button(cx + 3, y, 76, "Cancel queue", () -> PlacementQueue.clear());
		button(cx + 82, y, 73, "Close", () -> {
			Settings.save();
			Minecraft.getInstance().setScreen(null);
		});
	}

	@Override
	public void onClose() {
		Settings.save();
		super.onClose();
	}
}
