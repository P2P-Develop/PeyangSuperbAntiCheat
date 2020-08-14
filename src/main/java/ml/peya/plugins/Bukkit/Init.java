package ml.peya.plugins.Bukkit;

import com.zaxxer.hikari.*;
import ml.peya.plugins.Bukkit.Utils.*;

import java.io.*;
import java.sql.*;

/**
 * プラグイン自体ではおさまらない初期化とかするとこ。
 */
public class Init
{
    /**
     * データベースの.dbファイルをめっちゃ作成する。
     *
     * @param path 相対パスでいいお。
     * @return こいつをHikariDataSourceに突っ込むといい感じになります。
     */
    public static HikariConfig initMngDatabase(String path)
    {
        HikariConfig hConfig = new HikariConfig();
        new File(path).getParentFile().mkdirs();

        hConfig.setDriverClassName("org.sqlite.JDBC");
        hConfig.setJdbcUrl("jdbc:sqlite:" + path);

        return hConfig;
    }

    /**
     * なかったらテーブル作る。あったらそのまま。
     *
     * @return 処理が正常に終わればtrueを返してくれます。
     */
    public static boolean createDefaultTables()
    {
        try (Connection connection = Variables.eye.getConnection();
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
            Utils.errorNotification(Utils.getStackTrace(e));
            return false;
        }

        try (Connection connection = Variables.banKick.getConnection();
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
            Utils.errorNotification(Utils.getStackTrace(e));
            return false;
        }

        try (Connection connection = Variables.trust.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.execute("CrEaTe TaBlE If NoT ExIsTs trust(" +
                    "PLAYER nchar" +
                    ");");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            return false;
        }
    }
}
