package me.axieum.mcmod.chatter.event;

import me.axieum.mcmod.chatter.Settings;
import me.axieum.mcmod.chatter.util.PlayerUtils;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class EventChat
{
    private static final String PREFIX_CODE = "\u00A7";

    /**
     * Transform server chat messages.
     *
     * @param event the ServerChatEvent instance
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onServerChat(ServerChatEvent event)
    {
        if (!Settings.ENABLED.get())
            return;

        String msg = "<{NAME}> {MESSAGE}";

        // Tweak the formatting
        if (PlayerUtils.isSinglePlayer(event.getPlayer()) || !PlayerUtils.isOp(event.getPlayer()))
            msg = Settings.FORMAT_GENERIC.get(); // Generic
        else if (PlayerUtils.isOp(event.getPlayer()))
            msg = Settings.FORMAT_OPERATOR.get(); // Is operator

        // Substitute the message variables into the format
        msg = msg.replace("{NAME}", event.getUsername());
        msg = msg.replace("{MESSAGE}", event.getMessage());

        // Handle replacements
        for (List<String> entry : Settings.REPLACEMENTS.get()) {
            try {
                msg = msg.replaceAll(entry.get(0), entry.size() > 1 ? entry.get(1) : "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Handle formatting codes
        msg = msg.replaceAll("&([0-9a-fk-or])", PREFIX_CODE + "$1");
        msg = msg.replace("\\n", "\n");

        // Override the message contents.
        event.setComponent(new StringTextComponent(msg));
    }
}