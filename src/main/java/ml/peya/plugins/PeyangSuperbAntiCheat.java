package ml.peya.plugins;

import com.zaxxer.hikari.*;
import jdk.internal.dynalink.beans.*;
import ml.peya.plugins.Commands.*;
import ml.peya.plugins.Utils.*;
import org.apache.commons.io.filefilter.*;
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
    public static HikariDataSource hManager = null;
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

        hManager =  new HikariDataSource(initMngDatabase(getDataFolder().getAbsolutePath() + "/"));

        cheatMeta = new DetectingList();

        if (!(createDefaultTables() && initBypass()))
            Bukkit.getPluginManager().disablePlugin(this);

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

    public static boolean createDefaultTables()
    {
        try(Connection connection = hManager.getConnection();
        Statement statement = connection.createStatement())
        {
            statement.execute("CrEaTe TaBlE If NoT ExIsTs watchreason(" +
                    "MNGID nchar," +
                    "REASON nchar," +
                    "VL int" +
                    ");");
            statement.execute("CrEaTe TaBlE If NoT ExIsTs watcheye(" +
                    "UUID nchar(32), " +
                    "ID nchar, " +
                    "ISSUEDATE int, " +
                    "ISSUEBYID nchar," +
                    "ISSUEBYUUID nchar," +
                    "MNGID nchar," +
                    "LEVEL int" +
                    ");");

            statement.execute("CrEaTe TaBlE If NoT ExIsTs wdlearn(" +
                    "DATA int" +
                    ");");
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
            return false;
        }
    }

    public static boolean initBypass()
    {
        try(Connection connection = PeyangSuperbAntiCheat.hManager.getConnection();
            Statement statement = connection.createStatement())
        {
            long ctl = 0;
            int cBypass = 1;
            ResultSet resultSet = statement.executeQuery("SeLeCt * FrOm WdLeArN");
            while (resultSet.next())
            {
                int cot = resultSet.getInt("DATA");

                ctl += cot;

                cBypass++;
            }

            banLeft = Math.toIntExact(ctl / cBypass);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
