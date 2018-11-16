package me.axieum.mcmod.chatter;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@Config(modid = Chatter.MOD_ID)
public class Settings
{
    /**
     * Should the mod be enabled?
     */
    @Config.LangKey("config.enabled")
    public static boolean enabled = true;

    @Config.LangKey("config.gap")
    public static boolean gap = false;

    @Config.LangKey("config.chat")
    public static Chat chat = new Chat();

    public static class Chat
    {
        /**
         * Chat format for standard players / single-player.
         */
        @Config.LangKey("config.chat.formatGeneric")
        public String formatGeneric = "<&e{NAME}&r> {MESSAGE}";

        /**
         * Chat format for server operators.
         */
        @Config.LangKey("config.chat.formatOperator")
        public String formatOperator = "<&c{NAME}&r> {MESSAGE}";
    }

    @SubscribeEvent
    public static void sync(ConfigChangedEvent event)
    {
        if (event.getModID().equals(Chatter.MOD_ID))
            ConfigManager.sync(Chatter.MOD_ID, Config.Type.INSTANCE);
    }
}
