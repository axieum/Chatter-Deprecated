package me.axieum.mcmod.chatter.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOpsEntry;

public class PlayerUtils
{
    /**
     * Check whether a player is a server operator.
     *
     * @param player the player instance
     * @return true if the player is a server operator
     */
    public static boolean isOp(EntityPlayer player)
    {
        MinecraftServer server = player.getServer();

        if (server != null)
        {
            UserListOpsEntry opEntry = server.getPlayerList().getOppedPlayers().getEntry(player.getGameProfile());

            if (opEntry != null)
                return opEntry.getPermissionLevel() >= server.getOpPermissionLevel();
        }

        return false;
    }

    /**
     * Check if a player is in single-player.
     *
     * @param player the player instance
     * @return true if the player is in single-player
     */
    public static boolean isSinglePlayer(EntityPlayer player)
    {
        return player.getServer() != null && player.getServer().isSinglePlayer();
    }
}
