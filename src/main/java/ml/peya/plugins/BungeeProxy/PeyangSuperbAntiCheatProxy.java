package ml.peya.plugins.BungeeProxy;

import ml.peya.plugins.BungeeStructure.*;
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

        bungeeCommand.registerCommand(new Commands());

        servers = new ArrayList<>();

        getProxy().registerChannel(bungeeChannel);

        getProxy().getPluginManager().registerListener(this, new Events());

        getLogger().info("PeyangSuperbAntiCheatProxy has been activated!");
    }
}
