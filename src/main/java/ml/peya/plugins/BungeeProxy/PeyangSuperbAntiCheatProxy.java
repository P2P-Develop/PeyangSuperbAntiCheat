package ml.peya.plugins.BungeeProxy;

import com.google.common.io.*;
import ml.peya.plugins.BungeeStructure.*;
import net.md_5.bungee.api.config.*;
import net.md_5.bungee.api.plugin.*;

import java.util.*;

import static ml.peya.plugins.Variables.*;

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

    /**
     * this
     */
    public static PeyangSuperbAntiCheatProxy proxy;

    @Override
    public void onEnable()
    {
        new Metrics(this, __BSTATS_PLUGIN_ID);
        logger = getLogger();
        bungeeChannel = "PSACProxy";
        bungeeCommand = new CommandManager();
        bungeeCommand.registerCommand(new Commands());

        proxy = this;

        servers = new ArrayList<>();

        getProxy().registerChannel(bungeeChannel);

        getProxy().getPluginManager().registerListener(this, new Events());

        getLogger().info("PeyangSuperbAntiCheatProxy has been activated!");
    }

    /**
     * 特定のサーバーに文字列データを送信します。
     *
     * @param info サーバ
     * @param str  内容
     */
    public static void sendData(ServerInfo info, String str)
    {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF(bungeeChannel);

        out.writeUTF(str);

        info.sendData(bungeeChannel, out.toByteArray());
    }

    /**
     * this取得
     *
     * @return this
     */
    public static PeyangSuperbAntiCheatProxy getPlugin()
    {
        return proxy;
    }
}
