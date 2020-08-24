package ml.peya.plugins.BungeeProxy;

import ml.peya.plugins.BungeeStructure.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.config.*;

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

    @Command(label = "report")
    public void report(CommandComponent cmd)
    {
        String[] args = cmd.getArgs();

        if (args.length > 2)
            return;
        String id = args[0];
        String player = args[1];
        String sender = cmd.getServer();

        logger.info("Reported Player [" + player + "](id=" + id + ")");


        for (String serverName : PeyangSuperbAntiCheatProxy.servers)
        {
            if (sender.equals(serverName))
                continue;
            ServerInfo server = PeyangSuperbAntiCheatProxy.getPlugin().getProxy().getServerInfo(serverName);
            if (server == null)
                continue;
            PeyangSuperbAntiCheatProxy.sendData(server, "report " + id + " " + player);
        }
    }
}
