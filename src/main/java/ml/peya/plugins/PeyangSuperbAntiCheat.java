package ml.peya.plugins;

import com.comphenix.protocol.*;
import com.comphenix.protocol.events.*;
import com.zaxxer.hikari.*;
import ml.peya.plugins.Commands.*;
import ml.peya.plugins.Commands.CmdTst.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.*;

import java.util.logging.*;

public class PeyangSuperbAntiCheat extends JavaPlugin
{
    public static Logger logger= Bukkit.getServer().getLogger();

    public static FileConfiguration config;
    public static String databasePath;
    public static String banKickPath;
    public static HikariDataSource eye = null;
    public static HikariDataSource banKick = null;
    public static DetectingList cheatMeta;
    public static int banLeft;
    public static KillCounting counting;
    public static ProtocolManager protocolManager;


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

        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY)
        {
            @Override
            public void onPacketReceiving(PacketEvent event)
            {
                new Packets().onPacketReceiving(event);
            }
        });

        if (!(Init.createDefaultTables() && Init.initBypass()))
            Bukkit.getPluginManager().disablePlugin(this);

        getCommand("report").setExecutor(new CommandReport());
        getCommand("peyangsuperbanticheat").setExecutor(new CommandPeyangSuperbAntiCheat());
        getCommand("aurabot").setExecutor(new AuraBot());
        getCommand("acpanic").setExecutor(new AuraPanic());
        getCommand("bans").setExecutor(new CommandBans());

        getServer().getPluginManager().registerEvents(new Events(), this);

        logger.info("PeyangSuperbAntiCheat has been activated!");
    }

    @Override
    public void onDisable()
    {
        if (eye != null)
            eye.close();
        logger.info("PeyangSuperbAntiCheat has disabled!");
    }

    public static PeyangSuperbAntiCheat getPlugin()
    {
        return plugin;
    }


}
