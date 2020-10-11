package ml.peya.plugins.BungeeProxy;

import ml.peya.plugins.Moderate.BanModifier;
import ml.peya.plugins.Variables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class BungeePlayerLogin
{

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
             PreparedStatement statement = connection.prepareStatement("SELECT EXPIRE, BANID, REASON FROM ban WHERE UUID=?"))
        {
            statement.setString(1, uuid.toString().replace("-", ""));
            ResultSet set = statement.executeQuery();
            while (set.next())
            {
                banInfo.put("id", set.getString("BANID"));
                banInfo.put("reason", set.getString("REASON"));
                banInfo.put("expire", set.getString("EXPIRE"));
                banned = true;
                break;
            }
        }
        catch (Exception ignored)
        {
        }

        banInfo.put("banned", String.valueOf(banned));
        return banInfo;
    }

    /**
     * プレイヤーのGGなIDを生成します
     *
     * @param seed シード値
     * @return GGなID
     */
    public static String getGGID(long seed)
    {
        StringBuilder builder = new StringBuilder();
        Random random = new Random(seed);
        for (int i = 0; i < 7; i++)
            builder.append(random.nextInt(9));
        return builder.toString();
    }

    /**
     * ログイン資格の審査をします。
     *
     * @param target ターゲット
     * @return kickメッセージ。Login可能なら空。
     */
    public static String preLoginPending(UUID target)
    {

        HashMap<String, String> banInfo = getBanInfo(target);


        if (!Boolean.parseBoolean(banInfo.get("banned")))
            return "";

        HashMap<String, Object> map = new HashMap<>();

        final String id = banInfo.get("id");

        map.put("reason", banInfo.get("reason"));
        map.put("ggid", getGGID(id.hashCode()));
        map.put("id", id);

        String message;

        if (banInfo.get("expire").equals("_PERM"))
            message = BungeeMessageEngine.get("ban.permReason", map);
        else
        {
            long time;
            try
            {
                time = Long.parseLong(banInfo.get("expire"));
            }
            catch (Exception ignored)
            {
                return "";
            }

            Date date = new Date(time);
            if (date.before(new Date()))
            {
                BanModifier.pardon(target);
                return "";
            }

            map.put("date", convertFromDate(date));
            message = BungeeMessageEngine.get("ban.tempReason", map);
        }

        return message;
    }

    /**
     * DateからStringに変換
     *
     * @param date DATE!!!!
     * @return 変換したやつ
     */
    public static String convertFromDate(Date date)
    {
        String d = BungeeMessageEngine.get("base.day");
        String h = BungeeMessageEngine.get("base.hour");
        String m = BungeeMessageEngine.get("base.minutes");
        String s = BungeeMessageEngine.get("base.seconds");

        long now = new Date().getTime();
        long ago = date.getTime();
        long diff = ago - now;

        long day = diff / (24 * 60 * 60 * 1000);
        long hour = diff / (60 * 60 * 1000) % 24;
        long minute = diff / (60 * 1000) % 60;
        long second = diff / 1000 % 60;

        return day + d + hour + h + minute + m + second + s;
    }
}
