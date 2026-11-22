# Palette Builder (client-side Fabric mod, Minecraft Java 26.3)

Press **B** (rebindable under Controls > Palette Builder) to open the menu.

* **Path** mode: builds a road in front of you, using a weighted mix of blocks, optionally following the terrain.
* **Wall** mode: builds a wall in front of you with height, thickness, foundation and optional battlements.
* 12+ built-in palettes per mode, plus a **Custom** palette: `stone*3, mossy_cobblestone*2, andesite`
  (block id, optional `*weight`).
* Build / Undo last / Cancel queue.

It is fully client side: it places blocks by sending `/setblock` commands, so you need
command permission (singleplayer with cheats, or an op on the server/Realm).
Stay close so the chunks are loaded.

## Build
CI: push to GitHub, the `build` workflow produces the jar (Actions > build > Artifacts).
Local: JDK 25 and Gradle 9.6+, then `gradle build`; jar is in `build/libs/`.
(Run `gradle wrapper --gradle-version 9.6.0` once if you want a `gradlew`.)
