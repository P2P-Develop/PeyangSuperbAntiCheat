package ml.peya.plugins.BungeeProxy;

import com.google.common.io.*;
import ml.peya.plugins.BungeeStructure.*;
import net.md_5.bungee.api.config.*;
import net.md_5.bungee.api.plugin.*;

import java.util.*;

import static ml.peya.plugins.Bukkit.Variables.*;

/**
 * Bungeeプロキシ用のPSACプラグイン実装。プロキシ用プラグイン情報とサーバーリストの管理をします。
 */
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
        logger = getLogger();
        bungeeChannel = "PSACProxy";
        bungeeCommand = new CommandManager();

        servers = new ArrayList<>();

        getProxy().registerChannel(bungeeChannel);

        getProxy().getPluginManager().registerListener(this, new Events());
        
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF(bungeeChannel);

        out.writeUTF("ping");

        for (ServerInfo info : getProxy().getServers().values())
        {
            logger.info("<-> " + info.getName() + " pinging...");
            info.sendData(bungeeChannel, out.toByteArray());
        }

        getLogger().info("PeyangSuperbAntiCheatProxy has been activated!");
    }
}
