package ml.peya.plugins.Enum;

import net.md_5.bungee.api.*;

public enum EnumSeverity
{
    LOW("Low", ChatColor.GREEN, 1),
    NORMAL("Normal", ChatColor.BLUE, 2),
    PRIORITY("Priority", ChatColor.YELLOW,3),
    REQUIRE_FAST("Require Fast", ChatColor.LIGHT_PURPLE, 4),
    SEVERE("Severe", ChatColor.DARK_RED, 5),
    UNKNOWN("Unknown", ChatColor.GRAY, 0);

    private String text;
    private net.md_5.bungee.api.ChatColor color;
    private int level;

    EnumSeverity(String text, net.md_5.bungee.api.ChatColor color, int level)
    {
        this.text = text;
        this.color = color;
        this.level = level;
    }

    public String getText()
    {
        return text;
    }

    public net.md_5.bungee.api.ChatColor getColor()
    {
        return color;
    }

    public int getLevel()
    {
        return level;
    }
}
