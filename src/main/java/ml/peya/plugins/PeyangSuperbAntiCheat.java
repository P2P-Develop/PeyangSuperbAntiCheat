package ml.peya.plugins;

import com.comphenix.protocol.*;
import com.comphenix.protocol.events.*;
import com.fasterxml.jackson.databind.*;
import com.zaxxer.hikari.*;
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
import org.apache.commons.io.*;
import org.bukkit.*;
import org.bukkit.plugin.java.*;

import java.io.*;
import java.util.*;
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
        for (String path : Arrays.asList("npc.seconds", "npc.kill", "npc.vllevel", "npc.learncount", "mod.trackTicks", "kick.defaultKick", "autoMessage.time"))
        {
            try
            {
                if (config.getInt(path) < 0)
                    throw new ArithmeticException();
            }
            catch (Exception ignored)
            {
                logger.log(Level.WARNING, path + " is minus value! set value to 0");
                config.set(path, 0);
            }
        }

        databasePath = config.getString("database.path");
        banKickPath = config.getString("database.logPath");
        trustPath = config.getString("database.trustPath");

        banLeft = config.getInt("npc.vlLevel");

        network = new NeuralNetwork();

        eye = new HikariDataSource(Init.initMngDatabase(getDataFolder().getAbsolutePath() + "/" + databasePath));
        banKick = new HikariDataSource(Init.initMngDatabase(getDataFolder().getAbsolutePath() + "/" + banKickPath));
        trust = new HikariDataSource(Init.initMngDatabase(getDataFolder().getAbsolutePath() + "/" + trustPath));

        try
        {
            FileUtils.copyInputStreamToFile(this.getResource("skin.db"), new File(getDataFolder().getAbsolutePath() + "/skin.db"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        skin = new HikariDataSource(Init.initMngDatabase(getDataFolder().getAbsolutePath() + "/skin.db"));

        if (!isEnabled())
            return;

        item = new Item();

        item.register(new AuraBotItem());  //====Page1
        item.register(new AuraPanicItem());
        item.register(new TestKnockBack());
        item.register(new CompassTracker3000_tm());
        item.register(new BanBook());
        item.register(new ToPage2());                                   //
        item.register(new BackButton());

        item.register(new BackToPage1());                              //====Page2
        item.register(new Lead());
        item.register(new ModList());

        item.register(new TargetStick());                              //====Main

        cheatMeta = new DetectingList();
        counting = new KillCounting();
        tracker = new Tracker();

        protocolManager = ProtocolLibrary.getProtocolManager();

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
        getCommand("userinfo").setExecutor(new CommandUserInfo());

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
            File file = new File(getDataFolder().getAbsolutePath() + "/" + config.getString("database.learnPath"));
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
            else
                throw new FileNotFoundException();
        }
        catch (FileNotFoundException ignored)
        {
            logger.warning("Learning data file not found.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
        try (FileWriter fw = new FileWriter(getDataFolder().getAbsolutePath() + "/" + config.getString("database.learnPath"));
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


        if (eye != null)
            eye.close();
        if (banKick != null)
            banKick.close();
        if (skin != null)
            skin.close();
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


        logger.info("PeyangSuperbAntiCheat has disabled!");
    }
}
