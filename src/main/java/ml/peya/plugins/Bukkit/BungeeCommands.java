package ml.peya.plugins.Bukkit;

import ml.peya.plugins.BungeeStructure.*;

public class BungeeCommands implements CommandExecutor
{
    @Command(label = "ping")
    public void ping(CommandComponent command)
    {
        Bungee.sendMessage("pong");
        Variables.bungeeCord = true;
    }
}
