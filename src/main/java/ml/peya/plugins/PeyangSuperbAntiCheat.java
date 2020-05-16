package ml.peya.plugins;

import com.zaxxer.hikari.*;
import ml.peya.plugins.Commands.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Utils.*;
import net.citizensnpcs.api.*;
import net.citizensnpcs.api.trait.*;
import org.apache.logging.log4j.core.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.*;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Logger;

public class PeyangSuperbAntiCheat extends JavaPlugin
{
    public static Logger logger= Bukkit.getServer().getLogger();

    public static FileConfiguration config;
    public static String databasePath;
    public static HikariDataSource hManager = null;
    public static CheatDetectNowMeta cheatMeta;

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

        hManager =  new HikariDataSource(initMngDatabase(getDataFolder().getAbsolutePath() + "/"));
        createDefaultTables();
        getCommand("report").setExecutor(new CommandReport());
        getCommand("peyangsuperbanticheat").setExecutor(new CommandPeyangSuperbAntiCheat());
        getServer().getPluginManager().registerEvents(new Events(), this);

        logger.info("PeyangSuperbAntiCheat has started!");
    }

    @Override
    public void onDisable()
    {
        if (hManager != null)
            hManager.close();
        logger.info("PeyangSuperbAntiCheat has stopped!");
    }

    public static PeyangSuperbAntiCheat getPlugin()
    {
        return plugin;
    }


    public static HikariConfig initMngDatabase(String path)
    {
        HikariConfig hConfig = new HikariConfig();
        String liteFullPath = path + databasePath;
        File file = new File(liteFullPath);
        file.getParentFile().mkdirs();

        hConfig.setDriverClassName("org.sqlite.JDBC");
        hConfig.setJdbcUrl("jdbc:sqlite:" + liteFullPath);

        return hConfig;
    }

    public static void createDefaultTables()
    {
        try(Connection connection = hManager.getConnection();
        Statement statement = connection.createStatement())
        {
            statement.execute("CREATE TABLE IF NOT EXISTS watchreason(" +
                    "MNGID nchar," +
                    "REASON nchar," +
                    "VL int" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS watcheye(" +
                    "UUID nchar(32), " +
                    "ID nchar, " +
                    "ISSUEDATE int, " +
                    "ISSUEBYID nchar," +
                    "ISSUEBYUUID nchar," +
                    "MNGID nchar," +
                    "LEVEL int" +
                    ");");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }
    }
}
