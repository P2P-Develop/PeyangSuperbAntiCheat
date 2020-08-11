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
import org.bukkit.plugin.java.*;

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
     * this入手。
     *
     * @return こいつ。
     */
    public static PeyangSuperbAntiCheat getPlugin()
    {
        return Variables.plugin;
    }

    /**
     * プラグインがスタートした時に行う処理をします。
     * 大体初期化とか。
     */
    @Override
    public void onEnable()
    {
        new Metrics(this, Variables.__BSTATS_PLUGIN_ID);

        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null || !getServer().getPluginManager().getPlugin("ProtocolLib").isEnabled())
        {
            Variables.logger.log(Level.SEVERE, "This plugin requires ProtocolLib!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();

        Variables.plugin = this;
        Variables.config = getConfig();
        Variables.databasePath = Variables.config.getString("database.path");
        Variables.banKickPath = Variables.config.getString("database.logPath");
        Variables.trustPath = Variables.config.getString("database.trustPath");

        Variables.banLeft = Variables.config.getInt("npc.vlLevel");

        Variables.network = new NeuralNetwork();

        Variables.eye = new HikariDataSource(Init.initMngDatabase(getDataFolder().getAbsolutePath() + "/" + Variables.databasePath));
        Variables.banKick = new HikariDataSource(Init.initMngDatabase(getDataFolder().getAbsolutePath() + "/" + Variables.banKickPath));
        Variables.trust = new HikariDataSource(Init.initMngDatabase(getDataFolder().getAbsolutePath() + "/" + Variables.trustPath));

        Variables.cheatMeta = new DetectingList();
        Variables.counting = new KillCounting();
        Variables.tracker = new Tracker();

        Variables.protocolManager = ProtocolLibrary.getProtocolManager();

        Variables.item = new Item();

        Variables.item.register(new ml.peya.plugins.Gui.Items.Target.AuraBot());  //====Page1
        Variables.item.register(new ml.peya.plugins.Gui.Items.Target.AuraPanic());
        Variables.item.register(new TestKnockBack());
        Variables.item.register(new CompassTracker3000_tm());
        Variables.item.register(new BanBook());
        Variables.item.register(new ToPage2());                                   //
        Variables.item.register(new BackButton());

        Variables.item.register(new BackToPage1());                              //====Page2
        Variables.item.register(new Lead());
        Variables.item.register(new ModList());

        Variables.item.register(new TargetStick());                              //====Main

        Variables.protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY)
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

        Variables.isAutoMessageEnabled = Variables.config.getBoolean("autoMessage.enabled");
        Variables.time = Variables.config.getLong("autoMessage.time");
        Variables.learnCountLimit = Variables.config.getInt("npc.learncount");

        if (Variables.time == 0L)
            Variables.time = 1L;

        Variables.autoMessage = new AutoMessageTask();
        Variables.trackerTask = new TrackerTask();

        Variables.isTrackEnabled = Variables.config.getBoolean("mod.tracking.enabled");

        if (Variables.isTrackEnabled)
        {
            Variables.logger.info("Starting Tracker Task...");
            Variables.trackerTask.runTaskTimer(this, 0, Variables.config.getInt("mod.tracking.trackTicks"));
        }

        if (Variables.isAutoMessageEnabled)
        {
            Variables.logger.info("Starting Auto-Message Task...");
            Variables.autoMessage.runTaskTimer(this, 0, 20 * (Variables.time * 60));
        }

        Variables.logger.info("Reading weights from learnPath...");
        try
        {
            File file = new File(getDataFolder().getAbsolutePath() + "/" + Variables.config.getString("database.learnPath"));
            if (file.exists() && file.length() >= 256)
            {
                JsonNode node = new ObjectMapper().readTree(file);
                int i = 0;
                for (double[] aIW : Variables.network.inputWeight)
                {
                    for (int i1 = 0; i1 < aIW.length; i1++)
                        Variables.network.inputWeight[i][i1] = node.get("inputWeight").get(i).get(i1).asDouble();
                    i++;
                }

                Arrays.parallelSetAll(Variables.network.middleWeight, i2 -> node.get("middleWeight").get(i2).asDouble());

                Variables.learnCount = node.get("learnCount").asInt();

                Variables.logger.info("Weights setting completed successfully!");
            }
            else
                throw new FileNotFoundException();
        }
        catch (Exception ignored)
        {
            Variables.logger.warning("Learning data file not found.");
        }

        Variables.mods = new HashMap<>();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "FML|HS");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "FML|HS", new PluginMessageListener());

        Variables.logger.info("PeyangSuperbAntiCheat has been activated!");
    }

    /**
     * プラグインが停止するときの処理をします。
     * リソース捨てないといけないやつのcloseとか保存とか。
     */
    @Override
    public void onDisable()
    {
        if (Variables.eye != null)
            Variables.eye.close();
        if (Variables.banKick != null)
            Variables.banKick.close();
        Variables.trust.close();
        Variables.eye = null;
        Variables.banKick = null;
        Variables.trust = null;
        if (Variables.autoMessage != null)
        {
            Variables.logger.info("Stopping Auto-Message Task...");
            Variables.autoMessage.cancel();
        }

        if (Variables.trackerTask != null)
        {
            Variables.logger.info("Stopping Tracker Task...");
            Variables.trackerTask.cancel();
        }

        try (FileWriter fw = new FileWriter(getDataFolder().getAbsolutePath() + "/" + Variables.config.getString("database.learnPath"));
             PrintWriter pw = new PrintWriter(new BufferedWriter(fw)))
        {
            Variables.logger.info("Saving learn weights to learning data file...");
            Mapper mp = new Mapper();
            mp.inputWeight = Variables.network.inputWeight;
            mp.middleWeight = Variables.network.middleWeight;
            mp.learnCount = Variables.learnCount;
            pw.print(new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).writeValueAsString(mp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Variables.logger.info("PeyangSuperbAntiCheat has disabled!");
    }
}
