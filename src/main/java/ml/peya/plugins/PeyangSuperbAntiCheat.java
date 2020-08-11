package ml.peya.plugins;

import com.comphenix.protocol.*;
import com.comphenix.protocol.events.*;
import com.fasterxml.jackson.databind.*;
import com.zaxxer.hikari.*;
import ml.peya.plugins.Commands.CmdTst.AuraBot;
import ml.peya.plugins.Commands.CmdTst.AuraPanic;
import ml.peya.plugins.Commands.CmdTst.*;
import ml.peya.plugins.Commands.*;
import ml.peya.plugins.DetectClasses.Packets;
import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Gui.Events.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Gui.Items.Main.*;
import ml.peya.plugins.Gui.Items.Target.*;
import ml.peya.plugins.Gui.Items.Target.Page2.*;
import ml.peya.plugins.Learn.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.Task.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.*;
import org.bukkit.scheduler.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 * このプラグインの中枢です。必ずここからスタートします。
 * 全体で使用する値などはここで初期化します。
 * また、リソースの破棄もここで行います。
 */
public class PeyangSuperbAntiCheat extends JavaPlugin
{
    /**
     * プラグインIDですねわかります。
     */
    private static final int __BSTATS_PLUGIN_ID = 8084;
    /**
     * ログを出力する対象をloggerとして格納します。
     */
    public static Logger logger = Logger.getLogger("PeyangSuperbAntiCheat");
    /**
     * ...おん？まぁファイルの設定いじるんじゃね？しらんけど。
     */
    public static FileConfiguration config;
    /**
     * Watcheyeのデータベースの置き場所が入ります。
     */
    public static String databasePath;
    /**
     * Bankickのデータベースの置き場所が入ります。
     */
    public static String banKickPath;
    /**
     * Trustのデータベースの置き場所が入ります。
     */
    public static String trustPath;
    /**
     * いろいろ値を入手するのに使います。
     * 地味に便利。
     */
    public static DetectingList cheatMeta;
    /**
     * キル回数をグローバルに格納するらしいです。意味わからんけど。
     */
    public static KillCounting counting;
    /**
     * ProtocolLibでNetな対策をばっちり行います。
     */
    public static ProtocolManager protocolManager;
    /**
     * ヘルプの表示とかに使います。
     * 後で色々追加されるヤツ。
     */
    public static Item item;
    /**
     * {@code CommandTracking}で追跡が入った時にこの値に動きがあるそうです。
     */
    public static Tracker tracker;
    /**
     * {@code CommandMods}に関与するクラスの関数とかで出てきたmodsをここに格納するそうです。
     */
    public static HashMap<UUID, HashMap<String, String>> mods;
    /**
     * 定期メッセージに使います。なんかあったのか初期化されてる。
     */
    public static long time = 0L;
    /**
     * まだ学習しきれていないひよっこAIの代わりにVLリミットとして登場します。
     */
    public static int banLeft;
    /**
     * この数を超えたらムキムキAIが代わりにキック評価をしてくれるようになります。
     */
    public static int learnCountLimit;
    /**
     * 学習回数がここに格納されるそうです。
     */
    public static int learnCount;
    /**
     * AIの脳ですね(パワーワード)。
     * ここにlearn()関数などをぶち込みます。
     */
    public static NeuralNetwork network;
    /**
     * Watcheyeデータベースをデシリアライズしてあげます。
     */
    public static HikariDataSource eye;
    /**
     * Bankickデータベースをデシリアライズしてやります。
     */
    public static HikariDataSource banKick;
    /**
     * Trustデータベースをデシリアライズしときます。
     */
    public static HikariDataSource trust;
    /**
     * 定期メッセージが有効になっているかどうか。
     */
    public static boolean isAutoMessageEnabled;
    /**
     * Trackingが有効だったまま投げ出していたかどうか。
     */
    public static boolean isTrackEnabled;
    /**
     * どういう定期メッセージを行うのかのアクション。
     */
    public static BukkitRunnable autoMessage;
    /**
     * Tracking時にやること。
     */
    public static BukkitRunnable trackerTask;
    /**
     * this.
     */
    private static PeyangSuperbAntiCheat plugin;

    /**
     * this入手。
     *
     * @return こいつ。
     */
    public static PeyangSuperbAntiCheat getPlugin()
    {
        return plugin;
    }

    /**
     * プラグインがスタートした時に行う処理をします。
     * 大体初期化とか。
     */
    @Override
    public void onEnable()
    {
        new Metrics(this, __BSTATS_PLUGIN_ID);

        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null || !getServer().getPluginManager().getPlugin("ProtocolLib").isEnabled())
        {
            logger.log(Level.SEVERE, "This plugin requires ProtocolLib!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();

        plugin = this;
        config = getConfig();
        databasePath = config.getString("database.path");
        banKickPath = config.getString("database.logPath");
        trustPath = config.getString("database.trustPath");

        banLeft = config.getInt("npc.vlLevel");

        network = new NeuralNetwork();

        eye = new HikariDataSource(Init.initMngDatabase(getDataFolder().getAbsolutePath() + "/" + databasePath));
        banKick = new HikariDataSource(Init.initMngDatabase(getDataFolder().getAbsolutePath() + "/" + banKickPath));
        trust = new HikariDataSource(Init.initMngDatabase(getDataFolder().getAbsolutePath() + "/" + trustPath));

        cheatMeta = new DetectingList();
        counting = new KillCounting();
        tracker = new Tracker();

        protocolManager = ProtocolLibrary.getProtocolManager();

        item = new Item();

        item.register(new ml.peya.plugins.Gui.Items.Target.AuraBot());  //====Page1
        item.register(new ml.peya.plugins.Gui.Items.Target.AuraPanic());
        item.register(new TestKnockBack());
        item.register(new CompassTracker3000_tm());
        item.register(new BanBook());
        item.register(new ToPage2());                                   //
        item.register(new BackButton());

        item.register(new BackToPage1());                              //====Page2
        item.register(new Lead());
        item.register(new ModList());

        item.register(new TargetStick());                              //====Main

        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY)
        {
            @Override
            public void onPacketReceiving(PacketEvent event)
            {
                Packets.useEntity(event);
            }
        });


        if (!Init.createDefaultTables())
            Bukkit.getPluginManager().disablePlugin(this);

        getCommand("report").setExecutor(new CommandReport());
        getCommand("peyangsuperbanticheat").setExecutor(new CommandPeyangSuperbAntiCheat());
        getCommand("aurabot").setExecutor(new AuraBot());
        getCommand("acpanic").setExecutor(new AuraPanic());
        getCommand("testknockback").setExecutor(new TestKnockback());
        getCommand("bans").setExecutor(new CommandBans());
        getCommand("pull").setExecutor(new CommandPull());
        getCommand("target").setExecutor(new CommandTarget());
        getCommand("mods").setExecutor(new CommandMods());
        getCommand("tracking").setExecutor(new CommandTracking());
        getCommand("trust").setExecutor(new CommandTrust());
        getCommand("silentteleport").setExecutor(new CommandSilentTeleport());

        getServer().getPluginManager().registerEvents(new Events(), this);
        getServer().getPluginManager().registerEvents(new Run(), this);
        getServer().getPluginManager().registerEvents(new Drop(), this);

        isAutoMessageEnabled = config.getBoolean("autoMessage.enabled");
        time = config.getLong("autoMessage.time");
        learnCountLimit = config.getInt("npc.learncount");

        if (time == 0L)
            time = 1L;

        autoMessage = new AutoMessageTask();
        trackerTask = new TrackerTask();

        isTrackEnabled = config.getBoolean("mod.tracking.enabled");

        if (isTrackEnabled)
        {
            logger.info("Starting Tracker Task...");
            trackerTask.runTaskTimer(this, 0, config.getInt("mod.tracking.trackTicks"));
        }

        if (isAutoMessageEnabled)
        {
            logger.info("Starting Auto-Message Task...");
            autoMessage.runTaskTimer(this, 0, 20 * (time * 60));
        }

        logger.info("Reading weights from learnPath...");
        try
        {
            File file = new File(getDataFolder().getAbsolutePath() + "/" + PeyangSuperbAntiCheat.config.getString("database.learnPath"));
            if (file.exists() && file.length() >= 256)
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
            else
                throw new FileNotFoundException();
        }
        catch (Exception ignored)
        {
            logger.warning("Learning data file not found.");
        }

        mods = new HashMap<>();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "FML|HS");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "FML|HS", new PluginMessageListener());

        logger.info("PeyangSuperbAntiCheat has been activated!");
    }

    /**
     * プラグインが停止するときの処理をします。
     * リソース捨てないといけないやつのcloseとか保存とか。
     */
    @Override
    public void onDisable()
    {
        if (eye != null)
            eye.close();
        if (banKick != null)
            banKick.close();
        trust.close();
        eye = null;
        banKick = null;
        trust = null;
        if (autoMessage != null)
        {
            logger.info("Stopping Auto-Message Task...");
            autoMessage.cancel();
        }

        if (trackerTask != null)
        {
            logger.info("Stopping Tracker Task...");
            trackerTask.cancel();
        }

        try (FileWriter fw = new FileWriter(getDataFolder().getAbsolutePath() + "/" + PeyangSuperbAntiCheat.config.getString("database.learnPath"));
             PrintWriter pw = new PrintWriter(new BufferedWriter(fw)))
        {
            logger.info("Saving learn weights to learning data file...");
            Mapper mp = new Mapper();
            mp.inputWeight = network.inputWeight;
            mp.middleWeight = network.middleWeight;
            mp.learnCount = learnCount;
            pw.print(new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).writeValueAsString(mp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        logger.info("PeyangSuperbAntiCheat has disabled!");
    }
}
