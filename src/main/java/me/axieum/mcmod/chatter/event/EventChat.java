package me.axieum.mcmod.chatter.event;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.axieum.mcmod.chatter.Settings;
import me.axieum.mcmod.chatter.util.PlayerUtils;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

@Mod.EventBusSubscriber
public class EventChat
{
    private static UUID last = null;

    /**
     * Transform server chat messages.
     *
     * @param event the ServerChatEvent instance
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onServerChat(ServerChatEvent event)
    {
        if (!Settings.enabled)
            return;

        String msg = "<{NAME}> {MESSAGE}";

        // Tweak the formatting.
        if (PlayerUtils.isSinglePlayer(event.getPlayer()) || !PlayerUtils.isOp(event.getPlayer()))
            msg = Settings.chat.formatGeneric; // Generic
        else if (PlayerUtils.isOp(event.getPlayer()))
            msg = Settings.chat.formatOperator; // Is operator

        // Handle formatting codes.
        msg = msg.replaceAll("&([0-9a-fk-or])", ChatFormatting.PREFIX_CODE + "$1");
        msg = msg.replace("\\n", "\n");

        // Substitute the message variables into the format.
        msg = msg.replace("{NAME}", event.getUsername());
        msg = msg.replace("{MESSAGE}", event.getMessage());

        // Should we add a line break if new player?
        if (Settings.gap && !event.getPlayer().getUniqueID().equals(last))
        {
            msg = "\n".concat(msg);
            last = event.getPlayer().getUniqueID();
        }

        // Override the message contents.
        event.setComponent(new TextComponentString(msg));
    }
}
