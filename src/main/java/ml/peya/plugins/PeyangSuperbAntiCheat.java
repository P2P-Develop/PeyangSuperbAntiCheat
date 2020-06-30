package ml.peya.plugins;

import com.comphenix.protocol.*;
import com.comphenix.protocol.events.*;
import com.zaxxer.hikari.*;
import ml.peya.plugins.Commands.CmdTst.*;
import ml.peya.plugins.Commands.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Gui.Events.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.*;
import org.bukkit.scheduler.*;

import java.util.logging.*;

public class PeyangSuperbAntiCheat extends JavaPlugin
{
    public static Logger logger = Logger.getLogger("PeyangSuperbAntiCheat");

    public static FileConfiguration config;

    public static String databasePath;
    public static String banKickPath;

    public static DetectingList cheatMeta;
    public static KillCounting counting;
    public static ProtocolManager protocolManager;
    public static Item item;

    public static long time = 0L;
    public static int banLeft;

    public static HikariDataSource eye = null;
    public static HikariDataSource banKick = null;
    public static boolean isAutoMessageEnabled = false;
    public static BukkitRunnable autoMessage = null;

    private static PeyangSuperbAntiCheat plugin;
    @Override
    public void onEnable()
    {
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


        eye =  new HikariDataSource(Init.initMngDatabase(getDataFolder().getAbsolutePath() + "/" + databasePath));
        banKick =  new HikariDataSource(Init.initMngDatabase(getDataFolder().getAbsolutePath() + "/" + banKickPath));

        cheatMeta = new DetectingList();
        counting = new KillCounting();
        protocolManager = ProtocolLibrary.getProtocolManager();

        item = new Item();

        item.register(new ml.peya.plugins.Gui.Items.AuraBot());
        item.register(new ml.peya.plugins.Gui.Items.AuraPanic());
        item.register(new ml.peya.plugins.Gui.Items.TestKnockBack());

        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY)
        {
            @Override
            public void onPacketReceiving(PacketEvent event)
            {
                new Packets().onPacketReceiving(event);
            }
        });

        protocolManager.addPacketListener(new PacketAdapter(this, PacketType.Play.Server.PLAYER_INFO)
        {
            @Override
            public void onPacketSending(PacketEvent event)
            {
                new Packets().onPacketSending(event);
            }
        });

        if (!(Init.createDefaultTables() && Init.initBypass()))
            Bukkit.getPluginManager().disablePlugin(this);

        getCommand("report").setExecutor(new CommandReport());
        getCommand("peyangsuperbanticheat").setExecutor(new CommandPeyangSuperbAntiCheat());
        getCommand("aurabot").setExecutor(new AuraBot());
        getCommand("acpanic").setExecutor(new AuraPanic());
        getCommand("testknockback").setExecutor(new TestKnockback());
        getCommand("bans").setExecutor(new CommandBans());
        getCommand("pull").setExecutor(new CommandPull());
        getCommand("target").setExecutor(new CommandTarget());

        getServer().getPluginManager().registerEvents(new Events(), this);
        getServer().getPluginManager().registerEvents(new Run(), this);
        getServer().getPluginManager().registerEvents(new Drop(), this);
        getServer().getPluginManager().registerEvents(new PickUp(), this);

        isAutoMessageEnabled = config.getBoolean("autoMessage.enabled");
        time = config.getLong("autoMessage.time");

        if (time == 0L)
            time = 1L;

        autoMessage = new AutoMessageTask();

        logger.info("Starting Auto-Message Task...");

        if (isAutoMessageEnabled)
            autoMessage.runTaskTimer(this, 0, 20 * (time * 60));

        logger.info("PeyangSuperbAntiCheat has been activated!");
    }

    @Override
    public void onDisable()
    {
        if (eye != null)
            eye.close();
        if (banKick != null)
            banKick.close();
        eye = null;
        banKick = null;
        logger.info("Stopping Auto-Message Task...");
        if (autoMessage != null && RunnableUtil.isStarted(autoMessage))
            autoMessage.cancel();
        logger.info("PeyangSuperbAntiCheat has disabled!");
    }

    public static PeyangSuperbAntiCheat getPlugin()
    {
        return plugin;
    }


}
