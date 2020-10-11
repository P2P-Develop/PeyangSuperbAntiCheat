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
    public static void ban(UUID player, String reason, @Nullable Date date)
    {
        String id = Abuse.genRandomId(8);

        try (Connection connection = Variables.ban.getConnection())
        {
            SQL.insert(connection, "ban",
                    player.toString().replace("-", ""),
                    id,
                    new Date().getTime(),
                    reason,
                    date == null ? "_PERM": date.getTime(),
                    1
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
        try (Connection connection = Variables.ban.getConnection();
             PreparedStatement found = connection.prepareStatement("SELECT BANID, DATE, REASON, EXPIRE, STAFF FROM ban WHERE UUID=?"))
        {
            found.setString(1, player.toString().replace("-", ""));

            ResultSet set = found.executeQuery();

            while (set.next())
            {
                try (Connection log = Variables.log.getConnection())
                {
                    SQL.insert(log, "ban",
                            player.toString().replace("-", ""),
                            set.getString("BANID"),
                            set.getString("DATE"),
                            set.getString("REASON"),
                            set.getString("EXPIRE"),
                            set.getString("STAFF")
                    );
                }
                
                SQL.delete(connection, "ban", new HashMap<String, String>()
                {{
                    put("BANID", set.getString("BANID"));
                }});
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
        try (Connection connection = Variables.ban.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT BANID, REASON, EXPIRE FROM ban WHERE UUID=?"))
        {
            statement.setString(1, uuid.toString().replace("-", ""));
            ResultSet set = statement.executeQuery();
            if (set.next())
            {
                banInfo.put("id", set.getString("BANID"));
                banInfo.put("reason", set.getString("REASON"));
                banInfo.put("expire", set.getString("EXPIRE"));
                banned = true;

            }

        }
        catch (Exception ignored)
        {
        }

        banInfo.put("banned", String.valueOf(banned));
        return banInfo;
    }
}
