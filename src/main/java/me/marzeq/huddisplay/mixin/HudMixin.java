package me.marzeq.huddisplay.mixin;

import me.marzeq.huddisplay.HudDisplay;
import me.marzeq.huddisplay.config.Config;
import me.marzeq.huddisplay.config.enums.Alignment;
import me.marzeq.huddisplay.config.enums.Line;
import me.marzeq.huddisplay.config.enums.SystemTime;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Mixin(net.minecraft.client.gui.hud.InGameHud.class)
public class HudMixin {
    @Inject(at = @At("TAIL"), method = "render")
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        Config config = HudDisplay.config;
        MinecraftClient client = MinecraftClient.getInstance();
        MinecraftClientMixin clientMixin = (MinecraftClientMixin) client;

        if (client.options.debugEnabled) {
            return;
        }

        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        for (Line line : config.order) {
            switch (line) {
                case FPS -> {
                    if (config.showFps) {
                        lines.add("FPS: " + clientMixin.getCurrentFPS());
                        colors.add(config.fpsColor);
                    }
                }
                case PING -> {
                    if (config.showPing) {
                        lines.add("Ping: " + client.getNetworkHandler().getPlayerListEntry(client.player.getUuid()).getLatency() + "ms");
                        colors.add(config.pingColor);
                    }
                }
                case PLAYER_NAME -> {
                    if (config.showPlayerName) {
                        lines.add("Player: " + client.player.getName().getString());
                        colors.add(config.playerNameColor);
                    }
                }
                case XYZ -> {
                    if (config.showXYZ) {
                        lines.add("XYZ: " + client.player.getBlockPos().getX() + ", " + client.player.getBlockPos().getY() + ", " + client.player.getBlockPos().getZ());
                        colors.add(config.xyzColor);
                    }
                }
                case NETHER_XYZ -> {
                    if (config.showNetherXYZ) {
                        String worldName = client.player.world.getRegistryKey().getValue().getPath();

                        if (worldName.equals("the_nether") || worldName.equals("overworld")) {
                            String part1 = worldName.equals("the_nether") ? "Overworld XYZ: " : "Nether XYZ: ";
                            int y = worldName.equals("the_nether") ? client.player.getBlockPos().getY() / 8 : client.player.getBlockPos().getY() * 8;
                            int x = worldName.equals("the_nether") ? client.player.getBlockPos().getX() / 8 : client.player.getBlockPos().getX() * 8;
                            int z = worldName.equals("the_nether") ? client.player.getBlockPos().getZ() / 8 : client.player.getBlockPos().getZ() * 8;
                            lines.add(part1 + x + ", " + y + ", " + z);
                            colors.add(config.netherXyzColor);
                        }
                    }
                }
                case BIOME -> {
                    if (config.showBiome) {
                        lines.add("Biome: " + getBiomeString(client.player.world.getBiome(client.player.getBlockPos())));
                        colors.add(config.biomeColor);
                    }
                }
                case LIGHT_LEVEL -> {
                    if (config.showLightLevel) {
                        lines.add("Light level: " + client.world.getLightLevel(client.player.getBlockPos()));
                        colors.add(config.lightLevelColor);
                    }
                }
                case SYSTEM_TIME -> {
                    if (config.systemTime) {
                        DateTimeFormatter formatter;
                        if (config.systemTimeFormat == SystemTime.TWENTY_FOUR) {
                            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        } else {
                            formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                        }
                        lines.add("Time: " + formatter.format(java.time.LocalTime.now()).toUpperCase());
                        colors.add(config.systemTimeColor);
                    }
                }
            }
        }

        TextRenderer textRenderer = client.textRenderer;

        int x, y;

        int[] widths = lines.stream().mapToInt(textRenderer::getWidth).toArray();
        int maxWidth = max(widths);

        switch (config.position) {
            case TOP_LEFT -> {
                x = 4;
                y = 4;
            }
            case BOTTOM_LEFT -> {
                x = 4;
                int offset = textRenderer.fontHeight * lines.size() + (lines.size() - 1) * 2 + 2;
                y = client.getWindow().getScaledHeight() - offset;
            }
            case TOP_RIGHT -> {
                int offset = maxWidth + 2;
                x = client.getWindow().getScaledWidth() - offset;
                y = 4;
            }
            case BOTTOM_RIGHT -> {
                int offset = maxWidth + 2;
                x = client.getWindow().getScaledWidth() - offset;
                int offset2 = textRenderer.fontHeight * lines.size() + (lines.size() - 1) * 2 + 2;
                y = client.getWindow().getScaledHeight() - offset2;
            }
            default -> throw new IllegalStateException("Unexpected value: " + config.position);
        }

        for (String line : lines) {
            int color = colors.get(lines.indexOf(line));
            if (config.alignment == Alignment.LEFT) {
                textRenderer.drawWithShadow(matrices, line, x, y, color);
            } else if (config.alignment == Alignment.CENTER) {
                textRenderer.drawWithShadow(matrices, line, x + maxWidth / 2f - textRenderer.getWidth(line) / 2f, y, color);
            } else if (config.alignment == Alignment.RIGHT) {
                textRenderer.drawWithShadow(matrices, line, x + maxWidth - textRenderer.getWidth(line), y, color);
            } else {
                throw new IllegalStateException("Unexpected value: " + config.alignment);
            }
            y += textRenderer.fontHeight + 2;
        }
    }

    private int max(int[] array) {
        int highest = 0;
        for (int i : array) {
            if (i > highest) {
                highest = i;
            }
        }

        return highest;
    }

    private static String getBiomeString(RegistryEntry<Biome> biome) {
        return biome.getKeyOrValue().map(
                (biomeKey) -> biomeKey.getValue().toString(),
                (biome_) -> "[unregistered " + biome_ + "]"
        );
    }
}
