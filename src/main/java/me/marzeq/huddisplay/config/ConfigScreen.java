package me.marzeq.huddisplay.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.marzeq.huddisplay.HudDisplay;
import me.marzeq.huddisplay.config.enums.Alignment;
import me.marzeq.huddisplay.config.enums.Line;
import me.marzeq.huddisplay.config.enums.Position;
import me.marzeq.huddisplay.config.enums.SystemTime;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ConfigScreen implements ModMenuApi {
    private Screen screen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.of("Hud Display Config"));

        Config config = HudDisplay.config;

        ConfigEntryBuilder entry = builder.entryBuilder();

        builder.getOrCreateCategory(Text.of("Lines"))
                .addEntry(entry
                        .startBooleanToggle(Text.of("Show FPS"), config.showFps)
                        .setDefaultValue(Defaults.defaultFps)
                        .setSaveConsumer(v -> config.showFps = v).build()
                )
                .addEntry(entry
                        .startBooleanToggle(Text.of("Show Ping"), config.showPing)
                        .setDefaultValue(Defaults.defaultPing)
                        .setSaveConsumer(v -> config.showPing = v).build()
                )
                .addEntry(entry
                        .startBooleanToggle(Text.of("Show XYZ"), config.showXYZ)
                        .setDefaultValue(Defaults.defaultXYZ)
                        .setSaveConsumer(v -> config.showXYZ = v).build()
                )
                .addEntry(entry
                        .startBooleanToggle(Text.of("Show other dimension XYZ"), config.showNetherXYZ)
                        .setTooltip(Text.of("Shows the overworld XYZ when in the nether and vice versa (doesn't display in the end)"))
                        .setDefaultValue(Defaults.defaultNetherXYZ)
                        .setSaveConsumer(v -> config.showNetherXYZ = v).build()
                )
                .addEntry(entry
                        .startBooleanToggle(Text.of("Show Biome"), config.showBiome)
                        .setDefaultValue(Defaults.defaultBiome)
                        .setSaveConsumer(v -> config.showBiome = v).build()
                )
                .addEntry(entry
                        .startBooleanToggle(Text.of("Show Player Name"), config.showPlayerName)
                        .setDefaultValue(Defaults.defaultPlayerName)
                        .setSaveConsumer(v -> config.showPlayerName = v).build()
                )
                .addEntry(entry
                        .startBooleanToggle(Text.of("Show Light Level"), config.showLightLevel)
                        .setDefaultValue(Defaults.defaultLightLevel)
                        .setSaveConsumer(v -> config.showLightLevel = v).build()
                )
                .addEntry(entry
                        .startBooleanToggle(Text.of("Show System Time"), config.systemTime)
                        .setDefaultValue(Defaults.defaultSystemTime)
                        .setSaveConsumer(v -> config.systemTime = v).build()
                )
                .addEntry(entry
                        .startEnumSelector(Text.of("Time Format"), SystemTime.class, config.systemTimeFormat)
                        .setDefaultValue(Defaults.defaultSystemTimeFormat)
                        .setSaveConsumer(v -> config.systemTimeFormat = v).build()
                )
                .addEntry(entry
                        .startBooleanToggle(Text.of("Show Server Address"), config.showServerAddress)
                        .setDefaultValue(Defaults.defaultServerAddress)
                        .setSaveConsumer(v -> config.showServerAddress = v).build()
                );

        System.out.println(Defaults.defaultFpsColor);

        builder.getOrCreateCategory(Text.of("Colors"))
                .addEntry(entry
                        .startColorField(Text.of("FPS Color"), config.fpsColor)
                        .setDefaultValue(Defaults.defaultFpsColor)
                        .setSaveConsumer(v -> config.fpsColor = v).build()

                )
                .addEntry(entry
                        .startColorField(Text.of("Ping Color"), config.pingColor)
                        .setDefaultValue(Defaults.defaultPingColor)
                        .setSaveConsumer(v -> config.pingColor = v).build()

                )
                .addEntry(entry
                        .startColorField(Text.of("XYZ Color"), config.xyzColor)
                        .setDefaultValue(Defaults.defaultXyzColor)
                        .setSaveConsumer(v -> config.xyzColor = v).build()

                )
                .addEntry(entry
                        .startColorField(Text.of("Other Dimension XYZ Color"), config.netherXyzColor)
                        .setDefaultValue(Defaults.defaultNetherXyzColor)
                        .setSaveConsumer(v -> config.netherXyzColor = v).build()
                )
                .addEntry(entry
                        .startColorField(Text.of("Biome Color"), config.biomeColor)
                        .setDefaultValue(Defaults.defaultBiomeColor)
                        .setSaveConsumer(v -> config.biomeColor = v).build()

                )
                .addEntry(entry
                        .startColorField(Text.of("Player Name Color"), config.playerNameColor)
                        .setDefaultValue(Defaults.defaultPlayerNameColor)
                        .setSaveConsumer(v -> config.playerNameColor = v).build()

                )
                .addEntry(entry
                        .startColorField(Text.of("Light Level Color"), config.lightLevelColor)
                        .setDefaultValue(Defaults.defaultLightLevelColor)
                        .setSaveConsumer(v -> config.lightLevelColor = v).build()

                )
                .addEntry(entry
                        .startColorField(Text.of("System Time Color"), config.systemTimeColor)
                        .setDefaultValue(Defaults.defaultSystemTimeColor)
                        .setSaveConsumer(v -> config.systemTimeColor = v).build()
                )
                .addEntry(entry
                        .startColorField(Text.of("Server Address Color"), config.serverAddressColor)
                        .setDefaultValue(Defaults.defaultServerAddressColor)
                        .setSaveConsumer(v -> config.serverAddressColor = v).build()
                );

        ConfigCategory category = builder.getOrCreateCategory(Text.of("Order"));

        for (int i = 0; i < config.order.length; i++) {
            int finalI = i;
            category.addEntry(entry
                    .startEnumSelector(Text.of("Line " + (i + 1)), Line.class, config.order[i])
                    .setDefaultValue(Defaults.defaultOrder[i])
                    .setSaveConsumer(v -> {
                        // check if under an index different from the current one there is the same value
                        for (int j = 0; j < config.order.length; j++) {
                            if (j != finalI && config.order[j].equals(v)) {
                                config.order[j] = config.order[finalI];
                            }
                        }
                        config.order[finalI] = v;
                    }).build()
            );
        }


        builder.getOrCreateCategory(Text.of("Position & Alignment"))
                .addEntry(entry
                        .startEnumSelector(Text.of("Position"), Position.class, config.position)
                        .setDefaultValue(Defaults.defaultPosition)
                        .setSaveConsumer(v -> config.position = v).build()
                )
                .addEntry(entry
                        .startEnumSelector(Text.of("Alignment"), Alignment.class, config.alignment)
                        .setDefaultValue(Defaults.defaultAlignment)
                        .setSaveConsumer(v -> config.alignment = v).build()
                );

        builder.setSavingRunnable(config::save);

        return builder.build();
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            try {
                return screen(parent);
            } catch (Exception e) {
                Config config = HudDisplay.config;
                config.reset();
                return screen(parent);
            }
        };
    }
}
