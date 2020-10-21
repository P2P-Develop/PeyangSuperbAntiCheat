package ml.peya.plugins.BungeeProxy;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ml.peya.plugins.BungeeStructure.CommandManager;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;

import static ml.peya.plugins.Variables.ban;
import static ml.peya.plugins.Variables.banPath;
import static ml.peya.plugins.Variables.bungeeChannel;
import static ml.peya.plugins.Variables.bungeeCommand;
import static ml.peya.plugins.Variables.log;
import static ml.peya.plugins.Variables.logPath;
import static ml.peya.plugins.Variables.logger;

/**
 * Bungeeプロキシ用のPSACプラグイン実装。プロキシ用プラグイン情報とサーバーリストの管理をします。
 */
public class PeyangSuperbAntiCheatProxy extends Plugin
{
    /**
     * bStats
     */
    private static final int __BSTATS_PLUGIN_ID = 9068;

    /**
     * PSAC有効サーバリスト
     */
    public static ArrayList<String> servers;

    /**
     * Config
     */
    public static Configuration config;

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

    private static HikariConfig getDBSetting(String path)
    {
        HikariConfig hConfig = new HikariConfig();
        new File(path).getParentFile().mkdirs();
        hConfig.setDriverClassName(config.getString("database.method"));
        hConfig.setJdbcUrl(config.getString("database.url") + path);
        return hConfig;
    }

    /**
     * プラグインを無効化
     */
    public void severeDisable()
    {
        getProxy().getPluginManager().unregisterListeners(this);
        getProxy().getPluginManager().unregisterCommands(this);
        this.onDisable();
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

        BungeeCordConfiguration cordConfiguration = new BungeeCordConfiguration("bungee-config.yml");
        try
        {
            getLogger().info("Loading config...");
            cordConfiguration.loadConfig();
            config = cordConfiguration.getConfig();
        }
        catch (IOException e)
        {
            getLogger().log(Level.SEVERE, "An critical error has occurred.");
            e.printStackTrace();
            severeDisable();
        }

        banPath = config.getString("database.banPath");
        logPath = config.getString("database.logPath");

        if (config.getString("database.method").contains("sqlite"))
        {
            if (getProxy().getPluginManager().getPlugin("SQLiteBungeecord") == null)
            {
                getLogger().log(Level.SEVERE, "This server doesn't support SQLite!\n");
                getLogger().log(Level.INFO, "NOTE: Try installing SQLiteBungeeCord to solve this problem.");
                getLogger().log(Level.INFO, "Download >>> https://www.spigotmc.org/resources/sqlite-for-bungeecord.57191/ <<<");
                severeDisable();
            }

            ban = new HikariDataSource(getDBSetting(Paths.get(banPath).isAbsolute()
                ? banPath
                : getPlugin().getDataFolder().getAbsolutePath() + "/" + banPath));

            log = new HikariDataSource(getDBSetting(Paths.get(logPath).isAbsolute()
                ? logPath
                : getPlugin().getDataFolder().getAbsolutePath() + "/" + logPath));
        }
        else
            ban = new HikariDataSource(getDBSetting(banPath));


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
