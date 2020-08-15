package ml.peya.plugins.BungeeProxy;

import com.google.common.io.*;
import ml.peya.plugins.Bukkit.*;
import ml.peya.plugins.BungeeStructure.*;
import net.md_5.bungee.api.config.*;
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

        getProxy().getPluginManager().registerListener(this, new Events());
        
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF(Variables.bungeeChannel);

        out.writeUTF("ping");

        for (ServerInfo info : getProxy().getServers().values())
        {
            Variables.logger.info("<-> " + info.getName() + " pinging...");
            info.sendData(Variables.bungeeChannel, out.toByteArray());
        }

        getLogger().info("PeyangSuperbAntiCheatProxy has been activated!");
    }
}
