package ml.peya.plugins.Moderate;

import ml.peya.plugins.DetectClasses.WatchEyeManagement;
import ml.peya.plugins.Utils.Utils;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import static ml.peya.plugins.Variables.banKick;

/**
 * Bansのメソッド群。
 */
public class BanAnalyzer
{
    /**
     * プレイヤーのBAN履歴の取得
     *
     * @param uuid UUID。
     * @param type 取得タイプ(BAN, KICK, MUTE, *ALL*)
     *
     * @return BANデータ
     */
    public static ArrayList<Bans> getAbuse(UUID uuid, Type type)
    {
        ArrayList<Bans> abuses = new ArrayList<>();

        switch (type)
        {
            case ALL:
            case KICK:
                try (Connection connection = banKick.getConnection();
                     Statement statement = connection.createStatement())
                {
                    ResultSet set = statement.executeQuery("SeLeCt * FrOm KiCk WhErE UUID='" + uuid.toString() + "'");
                    while (set.next())
                    {
                        abuses.add(new Bans(
                                set.getLong("DATE"),
                                set.getString("REASON"),
                                set.getString("PLAYER"),
                                set.getString("UUID"),
                                set.getString("KICKID").replace("#", ""),
                                Type.KICK
                        ));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Utils.errorNotification(Utils.getStackTrace(e));
                }
                if (type == Type.KICK)
                    break;
            case BAN:
                try (Connection connection = banKick.getConnection();
                     Statement statement = connection.createStatement())
                {
                    ResultSet set = statement.executeQuery("SeLeCt * FrOm ban WhErE UUID='" + uuid.toString() + "'");
                    while (set.next())
                    {
                        abuses.add(new Bans(
                                set.getLong("DATE"),
                                set.getString("REASON"),
                                set.getString("PLAYER"),
                                set.getString("UUID"),
                                set.getString("BANID").replace("#", ""),
                                Type.BAN
                        ));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Utils.errorNotification(Utils.getStackTrace(e));
                }
                if (type == Type.BAN)
                    break;
            default:
                return abuses;
        }

        return abuses;
    }

    /**
     * Ban情報を<b>登録</b>
     * 実際にはBANせず、登録するだけです。
     *
     * @param reason Ban理由
     * @param player Banプレイヤー
     */
    public static void ban(Player player, String reason)
    {
        reason = WatchEyeManagement.parseInjection(reason);
        String name = player.getDisplayName();
        name = WatchEyeManagement.parseInjection(name);

        StringBuilder id = new StringBuilder();
        Random random = new Random();
        IntStream.range(0, 8).parallel().forEachOrdered(i ->
        {
            id.append(random.nextBoolean() ? random.nextInt(9) : (char) (random.nextInt(5) + 'A'));
        });

        try (Connection connection = banKick.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.execute("INSERT INTO ban VALUES(" + String.format(
                    "'%s', '%s', '%s', %d, '%s', 1",
                    name,
                    player.getUniqueId(),
                    id.toString(),
                    new Date().getTime(),
                    reason
            ) + ")");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
        }
    }

    /**
     * BAN情報取得タイプ
     */
    public enum Type
    {
        /**
         * すべて
         */
        ALL,
        /**
         * KICKのみ
         */
        KICK,
        /**
         * BANのみ
         */
        BAN,
        /**
         * MUTEのみ
         */
        MUTE;

        /**
         * StringをTypeに変換する。
         *
         * @param name before
         *
         * @return AFTER
         */
        public static Type toType(String name)
        {
            switch (name.toLowerCase())
            {
                case "kick":
                    return KICK;
                case "ban":
                    return BAN;
                case "mute":
                    return MUTE;
                case "all":
                default:
                    return ALL;
            }
        }
    }

    /**
     * またタイプだわーいDoc(ry
     */
    public static class Bans
    {
        private final long date;
        private final String reason;
        private final String playerId;
        private final String uuId;
        private final String id;
        private final Type type;

        /**
         * コンストラクタで組み立てる。
         *
         * @param date     UNIX時間。
         * @param reason   判定タイプ。
         * @param playerId 管理ID？
         * @param uuId     UUID。
         * @param id       管理ID。
         * @param type     処罰方法。
         */
        public Bans(long date, String reason, String playerId, String uuId, String id, Type type)
        {
            this.date = date;
            this.reason = reason;
            this.id = id;
            this.playerId = playerId;
            this.uuId = uuId;
            this.type = type;
        }

        /**
         * UNIX時間のゲッター。
         *
         * @return UNIX時間。
         */
        public long getDate()
        {
            return date;
        }

        /**
         * 判定タイプのゲッター。
         *
         * @return 判定タイプ。
         */
        public String getReason()
        {
            return reason;
        }

        /**
         * 管理IDのゲッター。
         *
         * @return 管理ID。
         */
        public String getId()
        {
            return id;
        }

        /**
         * 管理ID？のゲッター。
         *
         * @return 管理ID？
         */
        public String getPlayerId()
        {
            return playerId;
        }

        /**
         * UUIDのゲッター。
         *
         * @return UUID。
         */
        public String getUuId()
        {
            return uuId;
        }

        /**
         * 処罰方法のゲッター。
         *
         * @return 処罰方法。
         */
        public Type getType()
        {
            return type;
        }
    }

}

