package ml.peya.plugins.DetectClasses;

import ml.peya.plugins.Enum.EnumCheatType;
import ml.peya.plugins.Utils.Utils;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;

import static ml.peya.plugins.Variables.eye;

/**
 * WatchEyeの管理をするクラス。
 */
public class WatchEyeManagement
{
    /**
     * WatchEyeにカラムに入れるべきもの含めて追加する。
     *
     * @param target   ターゲット。
     * @param fromName 名前。
     * @param fromUUID UUID。
     * @param level    レベル。
     *
     * @return 管理ID。エラーが発生したら空白。
     */
    public static String add(Player target, String fromName, String fromUUID, int level)
    {

        fromName = parseInjection(fromName);
        fromUUID = parseInjection(fromUUID);
        final String manageId = UUID.randomUUID().toString().replace("-", "");
        try (Connection connection = eye.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.execute(String.format(
                    "InSeRt InTo WaTcHeYe VaLuEs ('%s', '%s', %s, '%s', '%s', '%s', %s)",
                    target.getUniqueId().toString().replace("-", ""),
                    target.getName(),
                    new Date().getTime(),
                    fromName,
                    fromUUID,
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
     *
     * @return 設定が成功したかどうか。
     */
    public static boolean setReason(String id, EnumCheatType reason, int vl)
    {
        id = parseInjection(id);
        try (Connection connection = eye.getConnection();
             Statement statement = connection.createStatement())
        {
            String reasonString = reason.getSysName();
            if (reasonString.endsWith(" "))
                reasonString = reasonString.substring(0, reasonString.length() - 1);
            reasonString = parseInjection(reasonString);
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
     * レコードが存在するかチェックする
     *
     * @param targetUuid ターゲットのUUID。
     * @param fromUuid   普通のUUID。
     *
     * @return 同じレコードであるかどうか。
     */
    public static boolean isExistsRecord(String targetUuid, String fromUuid)
    {
        targetUuid = parseInjection(targetUuid);
        fromUuid = parseInjection(fromUuid);
        try (Connection connection = eye.getConnection();
             Statement statement = connection.createStatement())
        {
            return statement
                    .executeQuery("SeLeCt * FrOm WaTcHeYe WhErE UUID = '" + targetUuid + "' AND ISSUEBYUUID = '" + fromUuid + "'")
                    .isBeforeFirst();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            return false;
        }
    }

    /**
     * レコードが存在するかチェックする
     *
     * @param id 管理ID。
     *
     * @return 同じレコードであるかどうか。
     */
    public static boolean isExistsRecord(String id)
    {
        id = parseInjection(id);
        try (Connection connection = eye.getConnection();
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
     *
     * @return インジェクションだった場合はエスケープ
     */
    public static String parseInjection(String sql)
    {
        sql = sql.replace("\\", "\\\\")
                 .replace("'", "''")
                 .replace("\"", "\"\"");
        return sql;
    }
}
