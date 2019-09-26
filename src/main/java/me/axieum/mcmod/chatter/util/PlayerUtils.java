package me.axieum.mcmod.chatter.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;

public class PlayerUtils
{
    /**
     * Check whether a player is a server operator.
     *
     * @param player the player instance
     * @return true if the player is a server operator
     */
    public static boolean isOp(ServerPlayerEntity player)
    {
        MinecraftServer server = player.getServer();

        if (server != null)
            return player.hasPermissionLevel(server.getOpPermissionLevel());

        return false;
    }

    /**
     * Check if a player is in single-player.
     *
     * @param player the player instance
     * @return true if the player is in single-player
     */
    public static boolean isSinglePlayer(ServerPlayerEntity player)
    {
        return player.getServer() != null && player.getServer().isSinglePlayer();
    }
}