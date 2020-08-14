package ml.peya.plugins.Bukkit.DetectClasses;

import ml.peya.plugins.Bukkit.*;
import ml.peya.plugins.Bukkit.Enum.*;
import ml.peya.plugins.Bukkit.Utils.*;
import org.bukkit.entity.*;

import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * WatchEyeの管理をするクラス。
 */
public class WatchEyeManagement
{
    /**
     * WatchEyeにカラムに入れるべきもの含めて追加する。
     *
     * @param target   ターゲット。
     * @param FromName 名前。
     * @param FromUUID UUID。
     * @param level    レベル。
     * @return 管理ID。エラーが発生したら空白。
     */
    public static String add(Player target, String FromName, String FromUUID, int level)
    {

        if (isInjection(FromName) || isInjection(FromUUID))
            return "";
        String manageId = UUID.randomUUID().toString().replace("-", "");
        try (Connection connection = Variables.eye.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.execute(String.format(
                    "InSeRt InTo WaTcHeYe VaLuEs ('%s', '%s', %s, '%s', '%s', '%s', %s)",
                    target.getUniqueId().toString().replace("-", ""),
                    target.getName(),
                    new Date().getTime(),
                    FromName,
                    FromUUID,
                    manageId,
                    level
            ));
            statement.close();
            return manageId;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            return "";
        }
    }

    /**
     * 事由を設定する。
     *
     * @param id     管理ID。
     * @param reason 事由。
     * @param vl     VL。
     * @return 設定が成功したかどうか。
     */
    public static boolean setReason(String id, EnumCheatType reason, int vl)
    {

        if (isInjection(id))
            return false;
        try (Connection connection = Variables.eye.getConnection();
             Statement statement = connection.createStatement())
        {
            String reasonString = reason.getSysName();
            if (reasonString.endsWith(" "))
                reasonString = reasonString.substring(0, reasonString.length() - 1);
            statement.execute(String.format(
                    "InSeRt InTo WaTcHrEaSoN VaLuEs ('%s', '%s', %s)",
                    id,
                    reasonString,
                    vl
            ));
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            return false;
        }
    }

    /**
     * 同じレコードであるか確認する。
     *
     * @param targetUuid ターゲットのUUID。
     * @param fromUuid   普通のUUID。
     * @return 同じレコードであるかどうか。
     */
    public static boolean isExistsRecord(String targetUuid, String fromUuid)
    {
        if (isInjection(targetUuid) || isInjection(fromUuid))
            return false;
        try (Connection connection = Variables.eye.getConnection();
             Statement statement = connection.createStatement())
        {
            return statement.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE UUID = '" + targetUuid + "' AND ISSUEBYUUID = '" + fromUuid + "'").isBeforeFirst();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            return false;
        }
    }

    /**
     * 同じレコードであるか確認する。
     *
     * @param id 管理ID。
     * @return 同じレコードであるかどうか。
     */
    public static boolean isExistsRecord(String id)
    {
        if (isInjection(id))
            return true;
        try (Connection connection = Variables.eye.getConnection();
             Statement statement = connection.createStatement())
        {
            return !statement.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE MnGiD = '" + id + "'").isBeforeFirst();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            return true;
        }
    }

    /**
     * SQLインジェクションを防止する。
     *
     * @param sql インジェクションと見られるSQL。
     * @return インジェクションだった場合はtrue。
     */
    public static boolean isInjection(String sql)
    {
        return sql.contains("'") || sql.contains("\"");
    }
}
