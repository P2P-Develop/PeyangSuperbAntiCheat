package ml.peya.plugins.Bukkit;

import com.google.common.io.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.messaging.*;

import java.io.*;

public class Bungee implements PluginMessageListener
{
    public static void sendMessage(String message)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        for (String string : message.split(" "))
            output.writeUTF(string);
        Bukkit.getServer().sendPluginMessage(PeyangSuperbAntiCheat.getPlugin(), Variables.bungeeChannel, output.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data)
    {
        if (!channel.equals(Variables.bungeeChannel))
            return;
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(data));
        String message;
        try
        {
            message = input.readUTF();

        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        Variables.bungeeCommand.runCommand(message);

    }
}
