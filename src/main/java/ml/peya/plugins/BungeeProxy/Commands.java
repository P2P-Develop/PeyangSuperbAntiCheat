package ml.peya.plugins.BungeeProxy;

import com.google.common.io.*;
import ml.peya.plugins.Bukkit.*;
import ml.peya.plugins.BungeeStructure.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.config.*;

public class Commands implements CommandExecutor
{
    @Command(label = "ping")
    public void ping(CommandComponent cmd)
    {
        ServerInfo server = ProxyServer.getInstance().getServerInfo(cmd.getServer());

        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("pong");

        server.sendData(Variables.bungeeChannel, output.toByteArray());
    }

    @Command(label = "pong")
    public void pong(CommandComponent cmd)
    {
        PeyangSuperbAntiCheatProxy.servers.add(cmd.getServer());
        Variables.logger.info("<-> " + cmd + " has connected");
    }
}
