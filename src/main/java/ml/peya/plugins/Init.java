package ml.peya.plugins;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ml.peya.plugins.BungeeStructure.CommandManager;
import ml.peya.plugins.Commands.CmdTst.AuraBot;
import ml.peya.plugins.Commands.CmdTst.AuraPanic;
import ml.peya.plugins.Commands.CmdTst.TestKnockback;
import ml.peya.plugins.Commands.CommandBans;
import ml.peya.plugins.Commands.CommandPeyangSuperbAntiCheat;
import ml.peya.plugins.Commands.CommandPull;
import ml.peya.plugins.Commands.CommandReport;
import ml.peya.plugins.Commands.CommandSilentTeleport;
import ml.peya.plugins.Commands.CommandTarget;
import ml.peya.plugins.Commands.CommandTracking;
import ml.peya.plugins.Commands.CommandTrust;
import ml.peya.plugins.Commands.CommandUserInfo;
import ml.peya.plugins.Commands.VanillaCommands;
import ml.peya.plugins.Gui.Events.Drop;
import ml.peya.plugins.Gui.Events.Run;
import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.Items.Main.TargetStick;
import ml.peya.plugins.Gui.Items.Target.AuraBotItem;
import ml.peya.plugins.Gui.Items.Target.AuraPanicItem;
import ml.peya.plugins.Gui.Items.Target.BackButton;
import ml.peya.plugins.Gui.Items.Target.BanBook;
import ml.peya.plugins.Gui.Items.Target.CompassTracker3000_tm;
import ml.peya.plugins.Gui.Items.Target.Lead;
import ml.peya.plugins.Gui.Items.Target.Page2.BackToPage1;
import ml.peya.plugins.Gui.Items.Target.TestKnockBack;
import ml.peya.plugins.Gui.Items.Target.ToPage2;
import ml.peya.plugins.Learn.NeuralNetwork;
import ml.peya.plugins.Task.AutoMessageTask;
import ml.peya.plugins.Task.TrackerTask;
import ml.peya.plugins.Utils.Utils;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import static ml.peya.plugins.PeyangSuperbAntiCheat.getPlugin;
import static ml.peya.plugins.Variables.autoMessage;
import static ml.peya.plugins.Variables.banKick;
import static ml.peya.plugins.Variables.banKickPath;
import static ml.peya.plugins.Variables.bungeeChannel;
import static ml.peya.plugins.Variables.bungeeCommand;
import static ml.peya.plugins.Variables.config;
import static ml.peya.plugins.Variables.databasePath;
import static ml.peya.plugins.Variables.eye;
import static ml.peya.plugins.Variables.isAutoMessageEnabled;
import static ml.peya.plugins.Variables.isTrackEnabled;
import static ml.peya.plugins.Variables.learnCount;
import static ml.peya.plugins.Variables.logger;
import static ml.peya.plugins.Variables.skin;
import static ml.peya.plugins.Variables.time;
import static ml.peya.plugins.Variables.trackerTask;
import static ml.peya.plugins.Variables.trust;
import static ml.peya.plugins.Variables.trustPath;

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
        banKickPath = config.getString("database.logPath");
        trustPath = config.getString("database.trustPath");
        eye = new HikariDataSource(Init.initMngDatabase(Paths.get(databasePath).isAbsolute()
                ? databasePath
                : getPlugin().getDataFolder().getAbsolutePath() + "/" + databasePath));
        banKick = new HikariDataSource(Init.initMngDatabase(Paths.get(banKickPath).isAbsolute()
                ? banKickPath
                : getPlugin().getDataFolder().getAbsolutePath() + "/" + banKickPath));
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
        getPlugin().getCommand("bans").setExecutor(new CommandBans());
        getPlugin().getCommand("pull").setExecutor(new CommandPull());
        getPlugin().getCommand("target").setExecutor(new CommandTarget());
        getPlugin().getCommand("tracking").setExecutor(new CommandTracking());
        getPlugin().getCommand("trust").setExecutor(new CommandTrust());
        getPlugin().getCommand("silentteleport").setExecutor(new CommandSilentTeleport());
        getPlugin().getCommand("userinfo").setExecutor(new CommandUserInfo());
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
        isAutoMessageEnabled = config.getBoolean("autoMessage.enabled");
        if (time == 0L)
            time = 1L;

        autoMessage = new AutoMessageTask();
        trackerTask = new TrackerTask();

        isTrackEnabled = config.getBoolean("mod.tracking.enabled");


        if (isTrackEnabled)
        {
            logger.info("Starting Tracker Task...");
            trackerTask.runTaskTimer(getPlugin(), 0, config.getInt("mod.tracking.trackTicks"));
        }

        if (isAutoMessageEnabled)
        {
            logger.info("Starting Auto-Message Task...");
            autoMessage.runTaskTimer(getPlugin(), 0, 20 * (time * 60));
        }
    }

    /**
     * 学習データ読み込み
     */
    public static void loadLearn()
    {
        try
        {
            File file = new File(getPlugin().getDataFolder().getAbsolutePath() + "/" + config.getString("database.learnPath"));
            if (file.exists() && file.length() >= 16)
            {
                JsonNode node = new ObjectMapper().readTree(file);
                int i = 0;
                for (double[] aIW : NeuralNetwork.inputWeight)
                {
                    for (int i1 = 0; i1 < aIW.length; i1++)
                        NeuralNetwork.inputWeight[i][i1] = node.get("inputWeight")
                                .get(i)
                                .get(i1)
                                .asDouble();
                    i++;
                }

                Arrays.parallelSetAll(NeuralNetwork.middleWeight, i2 -> node.get("middleWeight")
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
        Bukkit.getServer().getPluginManager().registerEvents(new VanillaCommands(), getPlugin());
    }


}
