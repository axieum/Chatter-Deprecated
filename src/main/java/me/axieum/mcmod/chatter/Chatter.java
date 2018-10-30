package me.axieum.mcmod.chatter;

import net.minecraftforge.fml.common.Mod;

@Mod(modid = Chatter.MOD_ID,
     name = Chatter.NAME,
     version = Chatter.VERSION,
     acceptableRemoteVersions = "*",
     useMetadata = true)
public class Chatter
{
    public static final String MOD_ID = "chatter";
    public static final String NAME = "Chatter";
    public static final String VERSION = "@VERSION@";

    @Mod.Instance(Chatter.MOD_ID)
    public static Chatter instance;
}
