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
        file.getParentFile().mkdirs();

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
                    "learncount int," +
                    "standard int," +
                    "MNGID nchar," +
                    "raw nchar" +
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

    public static boolean initBypass()
    {
        /*try (Connection connection = PeyangSuperbAntiCheat.eye.getConnection();
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

            if (cBypass - 1 != 0)
                PeyangSuperbAntiCheat.banLeft = Math.toIntExact(ctl / cBypass);
            else
                PeyangSuperbAntiCheat.banLeft = PeyangSuperbAntiCheat.config.getInt("kick.defaultKick");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }*/
        return true;
    }
}
