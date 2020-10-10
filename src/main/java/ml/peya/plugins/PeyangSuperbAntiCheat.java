package ml.peya.plugins;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ml.peya.plugins.DetectClasses.DetectingList;
import ml.peya.plugins.DetectClasses.KillCounting;
import ml.peya.plugins.DetectClasses.Packets;
import ml.peya.plugins.Learn.Mapper;
import ml.peya.plugins.Learn.NeuralNetwork;
import ml.peya.plugins.Moderate.Tracker;
import ml.peya.plugins.Module.InitModule;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.logging.Level;

import static ml.peya.plugins.Variables.autoMessage;
import static ml.peya.plugins.Variables.banKick;
import static ml.peya.plugins.Variables.banLeft;
import static ml.peya.plugins.Variables.cheatMeta;
import static ml.peya.plugins.Variables.config;
import static ml.peya.plugins.Variables.counting;
import static ml.peya.plugins.Variables.eye;
import static ml.peya.plugins.Variables.initialized;
import static ml.peya.plugins.Variables.item;
import static ml.peya.plugins.Variables.learnCount;
import static ml.peya.plugins.Variables.learnCountLimit;
import static ml.peya.plugins.Variables.logger;
import static ml.peya.plugins.Variables.network;
import static ml.peya.plugins.Variables.protocolManager;
import static ml.peya.plugins.Variables.skin;
import static ml.peya.plugins.Variables.time;
import static ml.peya.plugins.Variables.tracker;
import static ml.peya.plugins.Variables.trackerTask;
import static ml.peya.plugins.Variables.trust;

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
     * プラグイン有効チェック
     *
     * @param name プラグイン名
     * @return 有効否
     */
    public static boolean isEnablePlugin(String name)
    {
        return Bukkit.getServer().getPluginManager().getPlugin(name) != null &&
                Bukkit.getServer().getPluginManager().getPlugin(name).isEnabled();
    }

    /**
     * プラグインがスタートした時に行う処理をします。
     * 大体初期化とか。
     */
    @Override
    public void onEnable()
    {
        new Metrics(this, __BSTATS_PLUGIN_ID);

        if (!isEnablePlugin("ProtocolLib"))
        {
            logger.log(Level.SEVERE, "This plugin requires ProtocolLib!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();

        plugin = this;

        if (!Init.loadConfig())
        {
            initialized = false;
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        banLeft = config.getInt("npc.vlLevel");

        network = new NeuralNetwork();

        logger.info("Loading Database...");
        Init.initDataBase();


        Init.loadSkin();

        if (!isEnabled())
            return;

        item = Init.initItem();

        cheatMeta = new DetectingList();
        counting = new KillCounting();
        tracker = new Tracker();

        protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.addPacketListener(
                new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY)
                {
                    @Override
                    public void onPacketReceiving(PacketEvent event)
                    {
                        Packets.useEntity(event);
                    }
                }
        );

        if (!Init.createDefaultTables())
            Bukkit.getPluginManager().disablePlugin(this);

        logger.info("Registering command...");
        Init.registerCommand(); //Command Register
        Init.registerBungeeCommand();

        logger.info("Registering event...");
        Init.registerEvents();

        time = config.getLong("autoMessage.time");
        learnCountLimit = config.getInt("npc.learncount");

        logger.info("Enabling timer...");
        Init.enableTimer();

        logger.info("Reading weights from learnPath...");
        Init.loadLearn();


        Init.registerChannel();

        Bungee.sendMessage("ping");

        InitModule.init();

        initialized = true;

        logger.info("PeyangSuperbAntiCheat has been activated!");
    }

    /**
     * プラグインが停止するときの処理をします。
     * リソース捨てないといけないやつのcloseとか保存とか。
     */
    @Override
    public void onDisable()
    {
        if (initialized)
        {
            try (FileWriter fw = new FileWriter(Paths.get(config.getString("database.learnPath")).isAbsolute()
                    ? config.getString("database.learnPath")
                    : getPlugin().getDataFolder().getAbsolutePath() + "/" + config.getString("database.learnPath"));
                 PrintWriter pw = new PrintWriter(new BufferedWriter(fw)))
            {
                logger.info("Saving learn weights to learning data file...");
                Mapper mp = new Mapper();
                mp.inputWeight = NeuralNetwork.inputWeight;
                mp.middleWeight = NeuralNetwork.middleWeight;
                mp.learnCount = learnCount;
                pw.print(new ObjectMapper()
                        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                        .configure(SerializationFeature.INDENT_OUTPUT, true)
                        .writeValueAsString(mp));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if (eye != null)
            eye.close();
        if (banKick != null)
            banKick.close();
        if (skin != null)
            skin.close();
        if (trust != null)
            trust.close();
        eye = null;
        banKick = null;
        trust = null;
        skin = null;
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

        logger.info("PeyangSuperbAntiCheat has been disabled!");
    }
}
