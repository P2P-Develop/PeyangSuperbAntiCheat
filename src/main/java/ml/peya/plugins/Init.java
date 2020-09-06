package ml.peya.plugins;

import com.zaxxer.hikari.*;
import ml.peya.plugins.Commands.CmdTst.*;
import ml.peya.plugins.Commands.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Gui.Items.Main.*;
import ml.peya.plugins.Gui.Items.Target.*;
import ml.peya.plugins.Gui.Items.Target.Page2.*;
import ml.peya.plugins.Utils.*;

import java.io.*;
import java.sql.*;

import static ml.peya.plugins.Variables.*;

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
        try (Connection connection = eye.getConnection();
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

        try (Connection connection = banKick.getConnection();
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

            statement.execute("CrEaTe TaBlE If NoT ExIsTs ban(" +
                    "PLAYER nchar," +
                    "UUID nchar," +
                    "BANID nchar," +
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

        try (Connection connection = trust.getConnection();
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

    public static Item initItem()
    {
        Item item = new Item();

        item.register(new AuraBotItem());  //====Page1
        item.register(new AuraPanicItem());
        item.register(new TestKnockBack());
        item.register(new CompassTracker3000_tm());
        item.register(new BanBook());
        item.register(new ToPage2());                                   //====Page2
        item.register(new BackButton());

        item.register(new BackToPage1());                              //====Page2
        item.register(new Lead());
        item.register(new ModList());

        item.register(new TargetStick());                              //====Main

        return item;
    }

    public static void registerCommand()
    {
        PeyangSuperbAntiCheat.getPlugin().getCommand("report").setExecutor(new CommandReport());
        PeyangSuperbAntiCheat.getPlugin().getCommand("peyangsuperbanticheat").setExecutor(new CommandPeyangSuperbAntiCheat());
        PeyangSuperbAntiCheat.getPlugin().getCommand("aurabot").setExecutor(new AuraBot());
        PeyangSuperbAntiCheat.getPlugin().getCommand("acpanic").setExecutor(new AuraPanic());
        PeyangSuperbAntiCheat.getPlugin().getCommand("testknockback").setExecutor(new TestKnockback());
        PeyangSuperbAntiCheat.getPlugin().getCommand("bans").setExecutor(new CommandBans());
        PeyangSuperbAntiCheat.getPlugin().getCommand("pull").setExecutor(new CommandPull());
        PeyangSuperbAntiCheat.getPlugin().getCommand("target").setExecutor(new CommandTarget());
        PeyangSuperbAntiCheat.getPlugin().getCommand("mods").setExecutor(new CommandMods());
        PeyangSuperbAntiCheat.getPlugin().getCommand("tracking").setExecutor(new CommandTracking());
        PeyangSuperbAntiCheat.getPlugin().getCommand("trust").setExecutor(new CommandTrust());
        PeyangSuperbAntiCheat.getPlugin().getCommand("silentteleport").setExecutor(new CommandSilentTeleport());
        PeyangSuperbAntiCheat.getPlugin().getCommand("userinfo").setExecutor(new CommandUserInfo());
    }

}
