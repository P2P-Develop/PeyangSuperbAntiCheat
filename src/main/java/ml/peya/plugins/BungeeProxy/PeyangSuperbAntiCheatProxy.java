package ml.peya.plugins.BungeeProxy;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import ml.peya.plugins.BungeeStructure.CommandManager;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;

import static ml.peya.plugins.Variables.bungeeChannel;
import static ml.peya.plugins.Variables.bungeeCommand;
import static ml.peya.plugins.Variables.logger;

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

    /**
     * プラグインが有効になったときの。
     */
    @Override
    public void onEnable()
    {
        new Metrics(this, __BSTATS_PLUGIN_ID);
        logger = getLogger();
        bungeeChannel = "PSACProxy"; //チャンネル設定
        bungeeCommand = new CommandManager();
        bungeeCommand.registerCommand(new Commands());

        proxy = this;

        servers = new ArrayList<>();

        getProxy().registerChannel(bungeeChannel);

        getProxy().getPluginManager().registerListener(this, new Events());

        getLogger().info("PeyangSuperbAntiCheatProxy has been activated!");
    }

    /**
     * プラグインが無効になったときの。
     */
    @Override
    public void onDisable()
    {
        getLogger().info("Sending Disconnect packet...");
        servers.parallelStream()
                .forEach(s -> {
                    sendData(getProxy().getServerInfo(s), "dc");
                });

        servers = null;
        getLogger().info("PeyangSuperbAntiCheatProxy has been disabled!");
    }
}
