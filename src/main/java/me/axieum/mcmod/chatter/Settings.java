package me.axieum.mcmod.chatter;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class Settings
{
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;

    private static final String CATEGORY_GENERAL = "general";
    public static ForgeConfigSpec.BooleanValue ENABLED;

    private static final String CATEGORY_CHAT = "chat";
    public static ForgeConfigSpec.ConfigValue<String> FORMAT_GENERIC, FORMAT_OPERATOR;
    public static ForgeConfigSpec.ConfigValue<List<List<String>>> REPLACEMENTS;

    private static final String CATEGORY_EVENT = "event";
    public static ForgeConfigSpec.BooleanValue USERNAME_CLICK_ENABLED;
    public static ForgeConfigSpec.EnumValue<ClickEvent.Action> USERNAME_CLICK_ACTION;
    public static ForgeConfigSpec.ConfigValue<String> USERNAME_CLICK_VALUE;

    static {
        // GENERAL
        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

        ENABLED = COMMON_BUILDER.comment("Should the mod be enabled?")
                                .translation("config.enabled")
                                .define("enabled", true);

        COMMON_BUILDER.pop();

        // CHAT
        COMMON_BUILDER.comment("Chat settings").push(CATEGORY_CHAT);

        FORMAT_GENERIC = COMMON_BUILDER.comment("Chat format used for standard players")
                                       .translation("config.chat.formatGeneric")
                                       .define("formatGeneric",
                                               "<&e{NAME}&r> {MESSAGE}");

        FORMAT_OPERATOR = COMMON_BUILDER.comment("Chat format used for server operators")
                                        .translation("config.chat.formatOperator")
                                        .define("formatOperator",
                                                "<&c{NAME}&r> {MESSAGE}");

        REPLACEMENTS = COMMON_BUILDER.comment("Regular expressions to match and replace")
                                     .translation("config.chat.replacements")
                                     .define("replacements", new ArrayList<>());

        COMMON_BUILDER.pop();

        // EVENT
        COMMON_BUILDER.comment("Event settings").push(CATEGORY_EVENT);

        USERNAME_CLICK_ENABLED = COMMON_BUILDER.comment("Enable Username Clicking")
                                               .translation("config.event.usernameClickEnabled")
                                               .define("usernameClickEnabled", true);

        USERNAME_CLICK_ACTION = COMMON_BUILDER.comment("Username Click Action")
                                              .translation("config.event.usernameClickAction")
                                              .defineEnum("usernameClickAction", ClickEvent.Action.SUGGEST_COMMAND);

        USERNAME_CLICK_VALUE = COMMON_BUILDER.comment("Username Click Value")
                                             .translation("config.event.usernameClickValue")
                                             .define("usernameClickValue", "/tell {NAME} ");

        // Publish config
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path)
    {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                                                                  .sync()
                                                                  .autosave()
                                                                  .writingMode(WritingMode.REPLACE)
                                                                  .build();

        configData.load();
        spec.setConfig(configData);
    }
}
