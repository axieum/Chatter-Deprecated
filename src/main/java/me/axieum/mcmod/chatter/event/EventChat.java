package me.axieum.mcmod.chatter.event;

import me.axieum.mcmod.chatter.Settings;
import me.axieum.mcmod.chatter.util.PlayerUtils;
import me.axieum.mcmod.chatter.util.StringUtils;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.event.ClickEvent;
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

        // Fetch desired format
        String msg = "<{NAME}> {MESSAGE}";
        if (PlayerUtils.isSinglePlayer(event.getPlayer()) || !PlayerUtils.isOp(event.getPlayer()))
            msg = Settings.FORMAT_GENERIC.get(); // Generic
        else if (PlayerUtils.isOp(event.getPlayer()))
            msg = Settings.FORMAT_OPERATOR.get(); // Is operator

        // Fetch event details
        final String username = event.getUsername();
        final String message = event.getMessage();

        // Substitute the message variables into the format
        msg = msg.replace("{MESSAGE}", message);

        // Handle regex replacements
        for (List<String> entry : Settings.REPLACEMENTS.get()) {
            try {
                msg = msg.replaceAll(entry.get(0), entry.size() > 1 ? entry.get(1) : "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Handle formatting codes
        msg = StringUtils.applyFormattingCodes(msg, PREFIX_CODE);

        // Build username text component (with click event)
        String usernameClickValue = Settings.USERNAME_CLICK_VALUE.get()
                                                                 .replace("{NAME}", username)
                                                                 .replace("{MESSAGE}", message);
        StringTextComponent usernameComp = new StringTextComponent(username);
        usernameComp.applyTextStyle(style -> style.setClickEvent(new ClickEvent(Settings.USERNAME_CLICK_ACTION.get(),
                                                                                usernameClickValue)));

        // Inject username component into message
        final StringTextComponent component = new StringTextComponent("");
        String[] segments = msg.split("\\{NAME}"); // 2 parts - before and after username

        final boolean startsWithName = msg.startsWith("{NAME}");
        final boolean endsWithName = msg.endsWith("{NAME}");
        for (int i = 0; i < segments.length; i++) {
            if (i == 0 && startsWithName) // on first, handle prefixing
                component.appendSibling(usernameComp);

            component.appendSibling(new StringTextComponent(segments[i]));

            if ((!startsWithName || i > 0) && i < segments.length - 1) // in between, inject name
                component.appendSibling(usernameComp);

            if (i == segments.length - 1 && endsWithName) // on last, handle suffixing
                component.appendSibling(usernameComp);
        }

        // Override the message contents
        event.setComponent(component);
    }
}