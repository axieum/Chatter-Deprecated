package me.axieum.mcmod.chatter;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("chatter")
public class Chatter
{
    public Chatter()
    {
        // Register configuration
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Settings.COMMON_CONFIG);
        Settings.loadConfig(Settings.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("chatter-common.toml"));
    }
}
