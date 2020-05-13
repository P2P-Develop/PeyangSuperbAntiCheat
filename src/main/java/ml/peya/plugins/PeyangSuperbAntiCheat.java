package develop.p2p.plugin;

import com.zaxxer.hikari.*;
import develop.p2p.plugin.Commands.*;
import develop.p2p.plugin.Gui.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.*;

import java.io.*;
import java.sql.*;
import java.util.logging.*;

public class PeyangSuperbAntiCheat extends JavaPlugin
{
    public static Logger logger= Bukkit.getServer().getLogger();

    public static FileConfiguration config;
    public static String databasePath;
    public static HikariDataSource hSource = null;

    private static PeyangSuperbAntiCheat plugin;
    @Override
    public void onEnable()
    {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null)
        {
            logger.log(Level.SEVERE, "PeyangSuperbAntiCheat has Require ProtocolLib");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();

        plugin = this;
        config = getConfig();
        databasePath = config.getString("database.path");

        Book.init();
        hSource =  new HikariDataSource(initDatabaseConfig(getDataFolder().getAbsolutePath() + "/"));
        getCommand("report").setExecutor(new CommandReport());
        logger.info("PeyangSuperbAntiCheat has started!");
    }

    public static PeyangSuperbAntiCheat getPlugin()
    {
        return plugin;
    }


    public static HikariConfig initDatabaseConfig(String path)
    {
        HikariConfig hConfig = new HikariConfig();
        String liteFullPath = path + databasePath;
        File file = new File(liteFullPath);
        file.mkdirs();

        hConfig.setDriverClassName("org.sqlite.JDBC");
        hConfig.setJdbcUrl("jdbc:sqlite:" + liteFullPath);



        hConfig.setConnectionInitSql("CREATE TABLE IF NOT EXISTS watcheye(" +
                "UUID nchar(32), " +
                "ID nchar, " +
                "ISSUEDATE date, " +
                "ISSUEBYID nchar" +
                "ISSUEBYUUID nchar" +
                "MNGID int" +
                ")");
        return hConfig;
    }
}
