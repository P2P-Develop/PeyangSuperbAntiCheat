package ml.peya.plugins.BungeeProxy;

import com.google.common.io.*;
import ml.peya.plugins.Bukkit.*;
import ml.peya.plugins.BungeeStructure.*;
import net.md_5.bungee.api.plugin.*;

import java.util.*;

public class PeyangSuperbAntiCheatProxy extends Plugin
{
    /**
     * bStats
     */
    private static final int __BSTATS_PLUGIN_ID = 8084;

    /**
     * PSAC有効サーバリスト
     */
    public static ArrayList<String> servers;

    @Override
    public void onEnable()
    {
        new Metrics(this, __BSTATS_PLUGIN_ID);
        Variables.logger = getLogger();
        Variables.bungeeChannel = "PSACProxy";
        Variables.bungeeCommand = new CommandManager();

        servers = new ArrayList<>();

        getProxy().registerChannel(Variables.bungeeChannel);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("ping");

        for (String server : getProxy().getServers().keySet())
        {
            getProxy().getServerInfo(server).sendData(Variables.bungeeChannel, out.toByteArray());
        }


        getLogger().info("PeyangSuperbAntiCheat has been activated!");
    }
}
