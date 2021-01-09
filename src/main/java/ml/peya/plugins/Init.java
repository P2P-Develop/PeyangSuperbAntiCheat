package ml.peya.plugins;

import com.fasterxml.jackson.databind.*;
import com.zaxxer.hikari.*;
import ml.peya.plugins.BungeeStructure.*;
import ml.peya.plugins.Commands.CmdTst.AuraBot;
import ml.peya.plugins.Commands.CmdTst.AuraPanic;
import ml.peya.plugins.Commands.CmdTst.*;
import ml.peya.plugins.Commands.*;
import ml.peya.plugins.Gui.Events.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Gui.Items.Main.*;
import ml.peya.plugins.Gui.Items.Target.*;
import ml.peya.plugins.Gui.Items.Target.Page2.*;
import ml.peya.plugins.Task.*;
import ml.peya.plugins.Utils.*;
import org.apache.commons.io.*;
import org.bukkit.*;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.logging.*;

import static ml.peya.plugins.PeyangSuperbAntiCheat.*;
import static ml.peya.plugins.Variables.*;

/**
 * メインクラスではおさまらない初期化とかするとこ。
 */
public class Init
{

    /**
     * データベースの.dbファイルをめっちゃ作成する。
     *
     * @param path 相対パスでいいお。
     * @return こいつをHikariDataSourceに突っ込むといい感じになります。
     */
    public static HikariConfig initMngDatabase(String path)
    {
        HikariConfig hConfig = new HikariConfig();
        new File(path).getParentFile().mkdirs();
        hConfig.setDriverClassName(config.getString("database.method"));
        hConfig.setJdbcUrl(config.getString("database.url") + path);
        return hConfig;
    }

    /**
     * コンフィグ
     *
     * @return 正常終了
     */
    public static boolean loadConfig()
    {
        logger.info("Loading configuration...");
        config = getPlugin().getConfig();

        AtomicInteger error = new AtomicInteger();

        Arrays.asList("npc.seconds", "npc.kill", "npc.vllevel", "npc.learncount", "mod.trackTicks", "kick.defaultKick", "autoMessage.time")
            .parallelStream().forEachOrdered(path ->
        {
            try
            {
                if (config.getInt(path) < 0)
                    throw new ArithmeticException();
            }
            catch (Exception ignored)
            {
                logger.log(Level.SEVERE, path + " is minus value! set value to 0");
                error.getAndIncrement();
            }
        });

        logger.info("Done.");

        if (error.get() != 0)
        {
            logger.warning(error.get() + " Error(s) found.");
            return false;
        }

        return true;

    }

    /**
     * DB初期化
     */
    public static void initDataBase()
    {
        databasePath = config.getString("database.path");
        trustPath = config.getString("database.trustPath");
        if (!config.getString("database.method").contains("sqlite"))
        {
            eye = new HikariDataSource(Init.initMngDatabase(databasePath));
            trust = new HikariDataSource(Init.initMngDatabase(trustPath));
            return;
        }

        eye = new HikariDataSource(Init.initMngDatabase(Paths.get(databasePath).isAbsolute()
            ? databasePath
            : getPlugin().getDataFolder().getAbsolutePath() + "/" + databasePath));
        trust = new HikariDataSource(Init.initMngDatabase(Paths.get(trustPath).isAbsolute()
            ? trustPath
            : getPlugin().getDataFolder().getAbsolutePath() + "/" + trustPath));
    }

    /**
     * なかったらテーブル作る。あったらそのまま。
     *
     * @return 処理が正常に終わればtrueを返してくれます。
     */
    public static boolean createDefaultTables()
    {
        try (Connection connection = eye.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.execute("CrEaTe TaBlE If NoT ExIsTs watchreason(" +
                "MNGID nchar," +
                "REASON nchar," +
                "VL int" +
                ");");
            statement.execute("CrEaTe TaBlE If NoT ExIsTs watcheye(" +
                "UUID nchar(32), " +
                "ID nchar, " +
                "ISSUEDATE int, " +
                "ISSUEBYID nchar," +
                "ISSUEBYUUID nchar," +
                "MNGID nchar," +
                "LEVEL int" +
                ");");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            return false;
        }

        try (Connection connection = trust.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.execute("CrEaTe TaBlE If NoT ExIsTs trust(" +
                "PLAYER nchar" +
                ");");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            return false;
        }
    }

    /**
     * itemを初期化
     *
     * @return アイテム
     */
    public static Item initItem()
    {
        Item item = new Item();

        item.register(new AuraBotItem());  //====Page1
        item.register(new AuraPanicItem());
        item.register(new TestKnockBack());
        item.register(new CompassTracker3000_tm());
        //item.register(new BanBook());
        item.register(new ToPage2());                                   //====Page2
        item.register(new BackButton());

        item.register(new BackToPage1());                              //====Page2
        item.register(new Lead());

        item.register(new TargetStick());                              //====Main

        return item;
    }

    /**
     * コマンドを登録
     */
    public static void registerCommand()
    {
        getPlugin().getCommand("report").setExecutor(new CommandReport());
        getPlugin().getCommand("peyangsuperbanticheat").setExecutor(new CommandPeyangSuperbAntiCheat());
        getPlugin().getCommand("aurabot").setExecutor(new AuraBot());
        getPlugin().getCommand("acpanic").setExecutor(new AuraPanic());
        getPlugin().getCommand("testknockback").setExecutor(new TestKnockback());
        getPlugin().getCommand("pull").setExecutor(new CommandPull());
        getPlugin().getCommand("target").setExecutor(new CommandTarget());
        getPlugin().getCommand("tracking").setExecutor(new CommandTracking());
        getPlugin().getCommand("trust").setExecutor(new CommandTrust());
        getPlugin().getCommand("silentteleport").setExecutor(new CommandSilentTeleport());
        getPlugin().getCommand("kick").setExecutor(new CommandKick());
    }

    /**
     * スキンをDBからロード
     */
    public static void loadSkin()
    {
        try
        {
            FileUtils.copyInputStreamToFile(
                getPlugin().getResource("skin.db"),
                new File(getPlugin().getDataFolder().getAbsolutePath() + "/skin.db")
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Bukkit.getServer().getPluginManager().disablePlugin(getPlugin());
            return;
        }
        skin = new HikariDataSource(Init.initMngDatabase(getPlugin().getDataFolder().getAbsolutePath() + "/skin.db"));

    }

    /**
     * タイマーを有効化
     */
    public static void enableTimer()
    {
        trackerTask = new TrackerTask();

        isTrackEnabled = config.getBoolean("mod.tracking.enabled");


        if (isTrackEnabled)
        {
            logger.info("Starting Tracker Task...");
            trackerTask.runTaskTimer(getPlugin(), 0, config.getInt("mod.tracking.trackTicks"));
        }

    }

    /**
     * 学習データ読み込み
     */
    public static void loadLearn()
    {
        try
        {
            File file = new File(Paths.get(config.getString("database.learnPath")).isAbsolute()
                ? config.getString("database.learnPath")
                : getPlugin().getDataFolder().getAbsolutePath() + "/" + config.getString("database.learnPath"));

            if (file.exists() && file.length() >= 16)
            {
                JsonNode node = new ObjectMapper().readTree(file);

                int i = 0;
                for (double[] aIW : network.inputWeight)
                    for (int j = 0; j < aIW.length; j++)
                    {
                        if (i >= network.inputWeight.length)
                            break;
                        network.inputWeight[i]
                            [j] = node.get("inputWeight")
                            .get(i)
                            .get(j)
                            .asDouble();
                        i++;
                    }

                Arrays.parallelSetAll(network.middleWeight, i2 -> node.get("middleWeight")
                    .get(i2)
                    .asDouble());

                learnCount = node.get("learnCount").asInt();
                logger.info("Weights setting completed successfully!");
            }
        }
        catch (FileNotFoundException ignored)
        {
            logger.warning("Learning data file not found.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * プラグインメッセージチャンネル登録
     */
    public static void registerChannel()
    {
        bungeeChannel = "PSACProxy";
        Bukkit.getMessenger().registerOutgoingPluginChannel(getPlugin(), bungeeChannel);
        Bukkit.getMessenger().registerIncomingPluginChannel(getPlugin(), bungeeChannel, new Bungee());
    }

    /**
     * バンジーコマンド初期化
     */
    public static void registerBungeeCommand()
    {
        CommandManager manager = new CommandManager();

        manager.registerCommand(new BungeeCommands());

        bungeeCommand = manager;
    }

    /**
     * イベント登録
     */
    public static void registerEvents()
    {
        Bukkit.getServer().getPluginManager().registerEvents(new Events(), getPlugin());
        Bukkit.getServer().getPluginManager().registerEvents(new Run(), getPlugin());
        Bukkit.getServer().getPluginManager().registerEvents(new Drop(), getPlugin());
    }
}
