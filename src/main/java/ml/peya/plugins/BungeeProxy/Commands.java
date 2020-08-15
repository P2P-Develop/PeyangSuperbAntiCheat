package ml.peya.plugins.BungeeProxy;

import com.google.common.io.*;
import ml.peya.plugins.BungeeStructure.*;
import net.md_5.bungee.api.*;

import static ml.peya.plugins.Bukkit.Variables.*;

public class Commands implements CommandExecutor
{
    @Command(label = "ping")
    public void ping(CommandComponent cmd)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("pong");

        ProxyServer.getInstance().getServerInfo(cmd.getServer()).sendData(bungeeChannel, output.toByteArray());
    }

    @Command(label = "pong")
    public void pong(CommandComponent cmd)
    {
        PeyangSuperbAntiCheatProxy.servers.add(cmd.getServer());
        logger.info("<-> " + cmd + " has connected");
    }
}
