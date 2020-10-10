package ml.peya.plugins.Moderate;

import ml.peya.plugins.Utils.SQL;
import ml.peya.plugins.Utils.Utils;
import ml.peya.plugins.Variables;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class BanModifier
{
    /**
     * BAN!!!!!
     *
     * @param player 対象プレイヤー
     * @param reason 理由
     * @param date   解除日時(Nullable)
     */
    public static void ban(UUID player, String name, String reason, @Nullable Date date)
    {
        String id = Abuse.genRandomId(8);

        try (Connection connection = Variables.banKick.getConnection())
        {
            SQL.insert(connection, "ban",
                    name,
                    player.toString().replace("-", ""),
                    id,
                    new Date().getTime(),
                    reason,
                    date == null ? "_PERM": date.getTime(),
                    1,
                    0,
                    ""
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
        }
    }

    /**
     * BAN解除!
     *
     * @param player 対象
     */
    public static void pardon(UUID player)
    {
        try (Connection connection = Variables.banKick.getConnection();
             PreparedStatement found = connection.prepareStatement("SELECT BANID, PLAYER FROM ban WHERE UNBAN=? AND UUID=?"))
        {
            found.setInt(1, 0);
            found.setString(2, player.toString().replace("-", ""));

            ResultSet set = found.executeQuery();

            while (set.next())
            {
                try (PreparedStatement update = connection.prepareStatement("UPDATE ban SET UNBAN=?, UNBANDATE=? WHERE BANID=?"))
                {
                    update.setInt(1, 1);
                    update.setString(2, String.valueOf(new Date().getTime()));
                    update.setString(3, set.getString("BANID"));
                    update.execute();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * BANの詳細を取得します。
     * BANがない場合、banInfo.banned にfalseを格納して返します。
     *
     * @param uuid 対象
     * @return Info
     */
    public static HashMap<String, String> getBanInfo(UUID uuid)
    {
        HashMap<String, String> banInfo = new HashMap<>();
        boolean banned = false;
        try (Connection connection = Variables.banKick.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT UNBAN, EXPIRE, BANID, REASON FROM ban WHERE UUID=?"))
        {
            statement.setString(1, uuid.toString().replace("-", ""));
            ResultSet set = statement.executeQuery();
            while (set.next())
            {
                if (set.getInt("UNBAN") == 0)
                {
                    banInfo.put("id", set.getString("BANID"));
                    banInfo.put("reason", set.getString("REASON"));
                    banInfo.put("expire", set.getString("EXPIRE"));
                    banned = true;
                    break;
                }
            }
        }
        catch (Exception ignored)
        {
        }

        banInfo.put("banned", String.valueOf(banned));
        return banInfo;
    }
}
