package ml.peya.plugins.DetectClasses;

import ml.peya.plugins.Enum.EnumCheatType;
import ml.peya.plugins.Utils.SQL;
import ml.peya.plugins.Utils.Utils;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
     * @return 管理ID。エラーが発生したら空白。
     */
    public static String add(Player target, String fromName, String fromUUID, int level)
    {
        final String manageId = UUID.randomUUID().toString().replace("-", "");
        try (Connection connection = eye.getConnection())
        {
            SQL.insert(connection, "watcheye",
                    target.getUniqueId().toString().replace("-", ""),
                    target.getName(),
                    new Date().getTime(),
                    fromName,
                    fromUUID,
                    manageId,
                    level);
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
        try (Connection connection = eye.getConnection())
        {
            String reasonString = reason.getSysName();
            if (reasonString.endsWith(" "))
                reasonString = reasonString.substring(0, reasonString.length() - 1);

            SQL.insert(connection, "watchreason",
                    id,
                    reasonString,
                    vl);
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
     * @return 同じレコードであるかどうか。
     */
    public static boolean isExistsRecord(String targetUuid, String fromUuid)
    {
        try (Connection connection = eye.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT MNGID FROM watcheye WHERE UUID=? AND ISSUEBYUUID=?"))
        {
            statement.setString(1, targetUuid);
            statement.setString(2, fromUuid);
            return statement.executeQuery().isBeforeFirst();
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
     * @return レコードが存在するかどうか
     */
    public static boolean isExistsRecord(String id) //メソッドの名前的に反転しなくておｋ
    {
        try (Connection connection = eye.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT MNGID FROM watcheye WHERE MNGID=?"))
        {
            statement.setString(1, id);

            return statement.executeQuery().isBeforeFirst();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            return false;
        }
    }

    /**
     * SQLインジェクションを防止する。
     *
     * @param sql インジェクションと見られるSQL。
     * @return インジェクションだった場合はエスケープ
     */
    public static String parseInjection(String sql)
    {
        return sql.replace("\\", "\\\\")
                .replace("'", "''")
                .replace("\"", "\"\"");
    }
}
