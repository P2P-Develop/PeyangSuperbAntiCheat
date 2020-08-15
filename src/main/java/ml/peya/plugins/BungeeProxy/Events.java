package ml.peya.plugins.BungeeProxy;

import com.google.common.io.*;
import ml.peya.plugins.Bukkit.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.event.*;

public class Events implements Listener
{
    @EventHandler
    public void onPluginMessage(PluginMessageEvent e)
    {
        if (!e.getTag().equals(Variables.bungeeChannel))
            return;


        if (!(e.getSender() instanceof Server))
            return;


        byte[] data = e.getData();

        ByteArrayDataInput input = ByteStreams.newDataInput(data);

        String message = input.readUTF();

        Server sender = (Server) e.getSender();

        Variables.bungeeCommand.runCommand(message, sender.getInfo().getName());
    }

    /*@EventHandler
    public void onServerDisconnect(ServerDisconnectEvent e)
    {
        PeyangSuperbAntiCheatProxy.servers.remove(e.getTarget().getName());
        Variables.logger.info("<-> " + e.getTarget().getName() + " has disconnected");
    }*/
}
