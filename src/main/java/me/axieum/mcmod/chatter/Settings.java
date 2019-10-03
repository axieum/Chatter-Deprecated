package me.axieum.mcmod.chatter;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class Settings
{
    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_CHAT = "chat";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.BooleanValue ENABLED;
    public static ForgeConfigSpec.ConfigValue<String> FORMAT_GENERIC, FORMAT_OPERATOR;
    public static ForgeConfigSpec.ConfigValue<List<List<String>>> REPLACEMENTS;

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
