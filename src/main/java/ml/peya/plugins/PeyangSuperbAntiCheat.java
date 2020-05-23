package ml.peya.plugins;

import com.zaxxer.hikari.*;
import ml.peya.plugins.Commands.*;
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

    private static PeyangSuperbAntiCheat plugin;
    @Override
    public void onEnable()
    {
        if (getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled())
        {
            logger.log(Level.SEVERE, "This plugin is require Citizens 2.0~");
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

        if (!(Init.createDefaultTables() && Init.initBypass()))
            Bukkit.getPluginManager().disablePlugin(this);

        getCommand("report").setExecutor(new CommandReport());
        getCommand("peyangsuperbanticheat").setExecutor(new CommandPeyangSuperbAntiCheat());

        getServer().getPluginManager().registerEvents(new Events(), this);

        logger.info("PeyangSuperbAntiCheat has started!");
    }

    @Override
    public void onDisable()
    {
        if (eye != null)
            eye.close();
        logger.info("PeyangSuperbAntiCheat has stopped!");
    }

    public static PeyangSuperbAntiCheat getPlugin()
    {
        return plugin;
    }


}
