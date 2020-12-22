package ml.peya.plugins;

import com.comphenix.protocol.*;
import com.comphenix.protocol.events.*;
import com.fasterxml.jackson.databind.*;
import ml.peya.plugins.DetectClasses.Packets;
import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Learn.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.Module.*;
import org.bukkit.*;
import org.bukkit.plugin.java.*;

import java.io.*;
import java.nio.file.*;
import java.util.logging.*;

import static ml.peya.plugins.Variables.*;

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

    public static double lv = 0.02;

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
                mp.inputWeight = network.inputWeight;
                mp.middleWeight = network.middleWeight;
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
        if (skin != null)
            skin.close();
        if (trust != null)
            trust.close();
        eye = null;
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
