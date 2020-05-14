package ml.peya.plugins.Enum;

import org.bukkit.*;

public enum EnumSeverity
{
    LOW("Low", net.md_5.bungee.api.ChatColor.GREEN),
    NORMAL("Normal", net.md_5.bungee.api.ChatColor.BLUE),
    PRIORITY("Priority", net.md_5.bungee.api.ChatColor.YELLOW),
    SEVERE("Severe", net.md_5.bungee.api.ChatColor.DARK_RED);

    private String text;
    private net.md_5.bungee.api.ChatColor color;

    EnumSeverity(String text, net.md_5.bungee.api.ChatColor color)
    {
        this.text = text;
        this.color = color;
    }

    public String getText()
    {
        return text;
    }

    public net.md_5.bungee.api.ChatColor getColor()
    {
        return color;
    }
}
