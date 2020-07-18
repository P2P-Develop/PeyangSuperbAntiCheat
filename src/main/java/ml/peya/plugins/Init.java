package ml.peya.plugins;

import com.zaxxer.hikari.*;
import ml.peya.plugins.Utils.*;

import java.io.*;
import java.sql.*;

public class Init
{
    public static HikariConfig initMngDatabase(String path)
    {
        HikariConfig hConfig = new HikariConfig();
        File file = new File(path);
        if (!file.getParentFile().mkdirs())
            throw new SecurityException();

        hConfig.setDriverClassName("org.sqlite.JDBC");
        hConfig.setJdbcUrl("jdbc:sqlite:" + path);

        return hConfig;
    }

    public static boolean createDefaultTables()
    {
        try (Connection connection = PeyangSuperbAntiCheat.eye.getConnection();
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

        }
        catch (Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
            return false;
        }

        try (Connection connection = PeyangSuperbAntiCheat.banKick.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.execute("CrEaTe TaBlE If NoT ExIsTs kick(" +
                    "PLAYER nchar," +
                    "UUID nchar," +
                    "KICKID nchar," +
                    "DATE bigint," +
                    "REASON nchar," +
                    "STAFF int" +
                    ");");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
            return false;
        }

        try (Connection connection = PeyangSuperbAntiCheat.learn.getConnection();
            Statement statement = connection.createStatement())
        {
            statement.execute("CrEaTe TaBlE If NoT ExIsTs wdlearn(" +
                    "standard int," +
                    "MNGID nchar," +
                    "middleweight nchar" +
                    ");");

            statement.execute("Create table if not exists wdWeight(" +
                    "MNGID nchar," +
                    "inputweight nchar" +
                    ");");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
            return false;
        }
    }
}
