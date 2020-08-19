package ml.peya.plugins.BungeeProxy;

import ml.peya.plugins.BungeeStructure.*;
import net.md_5.bungee.api.*;

import static ml.peya.plugins.Bukkit.Variables.logger;

public class Commands implements CommandExecutor
{
    @Command(label = "ping")
    public void ping(CommandComponent cmd)
    {
        logger.info("<-> Pinged from Server [" + cmd.getServer() + "]");
        PeyangSuperbAntiCheatProxy.sendData(ProxyServer.getInstance().getServerInfo(cmd.getServer()), "pong");
    }

    @Command(label = "pong")
    public void pong(CommandComponent cmd)
    {
        PeyangSuperbAntiCheatProxy.servers.add(cmd.getServer());
        logger.info("<-> Server [" + cmd.getServer() + "] has connected");
    }
}
