package ml.peya.plugins.Bukkit;

import ml.peya.plugins.BungeeStructure.*;

public class BungeeCommands implements CommandExecutor
{
    @Command(label = "ping")
    public static void ping(CommandComponent command)
    {
        Bungee.sendMessage("pong");
        Variables.bungeeCord = true;
    }
}
