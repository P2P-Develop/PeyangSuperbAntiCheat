package ml.peya.plugins;

import com.fasterxml.jackson.databind.*;
import com.zaxxer.hikari.*;
import ml.peya.plugins.BungeeStructure.*;
import ml.peya.plugins.Commands.CmdTst.*;
import ml.peya.plugins.Commands.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Gui.Items.Main.*;
import ml.peya.plugins.Gui.Items.Target.*;
import ml.peya.plugins.Gui.Items.Target.Page2.*;
import ml.peya.plugins.Task.*;
import ml.peya.plugins.Utils.*;
import org.apache.commons.io.*;
import org.bukkit.*;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.logging.*;

import static ml.peya.plugins.Variables.*;

/**
 * プラグイン自体ではおさまらない初期化とかするとこ。
 */
public class Init
{
    /**
     * プラグイン有効チェック
     *
     * @param name プラグイン名
     * @return 有効否
     */
    public static boolean isEnablePlugin(String name)
    {
        return Bukkit.getServer().getPluginManager().getPlugin(name) != null && Bukkit.getServer().getPluginManager().getPlugin(name).isEnabled();
    }

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

        hConfig.setDriverClassName("org.sqlite.JDBC");
        hConfig.setJdbcUrl("jdbc:sqlite:" + path);

        return hConfig;
    }

    /**
     * コンフィグ
     *
     * @return 正常終了
     */
    public static boolean loadConfig()
    {
        logger.info("Loading configuration.");
        config = PeyangSuperbAntiCheat.getPlugin().getConfig();

        AtomicInteger error = new AtomicInteger();

        logger.info("Loading configuration...");
        Arrays.asList("npc.seconds", "npc.kill", "npc.vllevel", "npc.learncount", "mod.trackTicks", "kick.defaultKick", "autoMessage.time").parallelStream().forEachOrdered(path -> {
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
        banKickPath = config.getString("database.logPath");
        trustPath = config.getString("database.trustPath");
        eye = new HikariDataSource(Init.initMngDatabase(PeyangSuperbAntiCheat.getPlugin().getDataFolder().getAbsolutePath() + "/" + databasePath));
        banKick = new HikariDataSource(Init.initMngDatabase(PeyangSuperbAntiCheat.getPlugin().getDataFolder().getAbsolutePath() + "/" + banKickPath));
        trust = new HikariDataSource(Init.initMngDatabase(PeyangSuperbAntiCheat.getPlugin().getDataFolder().getAbsolutePath() + "/" + trustPath));

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

        try (Connection connection = banKick.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.execute("CrEaTe TaBlE If NoT ExIsTs kick(" +
                    "PLAYER nchar," +
                    "UUID nchar," +
                    "KICKID nchar," +
                    "DATE bigint," +
                    "REASON nchar," +
                    "STAFF int" +
                    ");");

            statement.execute("CrEaTe TaBlE If NoT ExIsTs ban(" +
                    "PLAYER nchar," +
                    "UUID nchar," +
                    "BANID nchar," +
                    "DATE bigint," +
                    "REASON nchar," +
                    "STAFF int" +
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
        item.register(new BanBook());
        item.register(new ToPage2());                                   //====Page2
        item.register(new BackButton());

        item.register(new BackToPage1());                              //====Page2
        item.register(new Lead());
        item.register(new ModList());

        item.register(new TargetStick());                              //====Main

        return item;
    }

    /**
     * コマンドを登録
     */
    public static void registerCommand()
    {
        PeyangSuperbAntiCheat.getPlugin().getCommand("report").setExecutor(new CommandReport());
        PeyangSuperbAntiCheat.getPlugin().getCommand("peyangsuperbanticheat").setExecutor(new CommandPeyangSuperbAntiCheat());
        PeyangSuperbAntiCheat.getPlugin().getCommand("aurabot").setExecutor(new AuraBot());
        PeyangSuperbAntiCheat.getPlugin().getCommand("acpanic").setExecutor(new AuraPanic());
        PeyangSuperbAntiCheat.getPlugin().getCommand("testknockback").setExecutor(new TestKnockback());
        PeyangSuperbAntiCheat.getPlugin().getCommand("bans").setExecutor(new CommandBans());
        PeyangSuperbAntiCheat.getPlugin().getCommand("pull").setExecutor(new CommandPull());
        PeyangSuperbAntiCheat.getPlugin().getCommand("target").setExecutor(new CommandTarget());
        PeyangSuperbAntiCheat.getPlugin().getCommand("mods").setExecutor(new CommandMods());
        PeyangSuperbAntiCheat.getPlugin().getCommand("tracking").setExecutor(new CommandTracking());
        PeyangSuperbAntiCheat.getPlugin().getCommand("trust").setExecutor(new CommandTrust());
        PeyangSuperbAntiCheat.getPlugin().getCommand("silentteleport").setExecutor(new CommandSilentTeleport());
        PeyangSuperbAntiCheat.getPlugin().getCommand("userinfo").setExecutor(new CommandUserInfo());
    }

    /**
     * スキンをDBからロード
     */
    public static void loadSkin()
    {
        try
        {
            FileUtils.copyInputStreamToFile(PeyangSuperbAntiCheat.getPlugin().getResource("skin.db"), new File(PeyangSuperbAntiCheat.getPlugin().getDataFolder().getAbsolutePath() + "/skin.db"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Bukkit.getServer().getPluginManager().disablePlugin(PeyangSuperbAntiCheat.getPlugin());
            return;
        }
        skin = new HikariDataSource(Init.initMngDatabase(PeyangSuperbAntiCheat.getPlugin().getDataFolder().getAbsolutePath() + "/skin.db"));

    }

    /**
     * タイマーを有効化
     */
    public static void enableTimer()
    {
        isAutoMessageEnabled = config.getBoolean("autoMessage.enabled");
        if (time == 0L)
            time = 1L;

        autoMessage = new AutoMessageTask();
        trackerTask = new TrackerTask();

        isTrackEnabled = config.getBoolean("mod.tracking.enabled");


        if (isTrackEnabled)
        {
            logger.info("Starting Tracker Task...");
            trackerTask.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, config.getInt("mod.tracking.trackTicks"));
        }

        if (isAutoMessageEnabled)
        {
            logger.info("Starting Auto-Message Task...");
            autoMessage.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, 20 * (time * 60));
        }
    }

    /**
     * 学習データ読み込み
     */
    public static void loadLearn()
    {
        try
        {
            File file = new File(PeyangSuperbAntiCheat.getPlugin().getDataFolder().getAbsolutePath() + "/" + config.getString("database.learnPath"));
            if (file.exists() && file.length() >= 16)
            {
                JsonNode node = new ObjectMapper().readTree(file);
                int i = 0;
                for (double[] aIW : network.inputWeight)
                {
                    for (int i1 = 0; i1 < aIW.length; i1++)
                        network.inputWeight[i][i1] = node.get("inputWeight").get(i).get(i1).asDouble();
                    i++;
                }

                Arrays.parallelSetAll(network.middleWeight, i2 -> node.get("middleWeight").get(i2).asDouble());

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
        Bukkit.getMessenger().registerOutgoingPluginChannel(PeyangSuperbAntiCheat.getPlugin(), "FML|HS");
        Bukkit.getMessenger().registerIncomingPluginChannel(PeyangSuperbAntiCheat.getPlugin(), "FML|HS", new ClientModGetter());
        Bukkit.getMessenger().registerOutgoingPluginChannel(PeyangSuperbAntiCheat.getPlugin(), bungeeChannel);
        Bukkit.getMessenger().registerIncomingPluginChannel(PeyangSuperbAntiCheat.getPlugin(), bungeeChannel, new Bungee());
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

}
