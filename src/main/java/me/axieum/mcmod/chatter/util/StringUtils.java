package me.axieum.mcmod.chatter.util;

public class StringUtils
{
    /**
     * Replace ampersand Minecraft format codes.
     *
     * @param str  input string
     * @param code Minecraft prefix code for symbols (i.e. §)
     * @return new string with ampersand codes replaced
     */
    public static String applyFormattingCodes(final String str, final String code)
    {
        return str.replaceAll("&([0-9a-fk-or])", code + "$1")
                  .replace("\\n", "\n");
    }
}
