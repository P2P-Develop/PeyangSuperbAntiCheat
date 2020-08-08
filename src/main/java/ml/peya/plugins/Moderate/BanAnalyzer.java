package ml.peya.plugins.Moderate;

import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;

import java.sql.*;
import java.util.*;

/**
 * Bansのメソッド群。
 */
public class BanAnalyzer
{
    /** なんか大文字で表示されるやつ返す。
     * @param uuid UUID。
     * @param type 罪状。
     *
     * @return 大文字のアレ。
     */
    public static ArrayList<Bans> getAbuse(UUID uuid, Type type)
    {
        ArrayList<Bans> abuses = new ArrayList<>();

        switch (type)
        {
            case ALL:
            case KICK:
                try (Connection connection = PeyangSuperbAntiCheat.banKick.getConnection();
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
                    ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
                }
                if (type == Type.KICK)
                    break;
            case BAN:
                break;
            default:
                return abuses;
        }

        return abuses;
    }

    /**
     * タイプだわーいDoc書くのめんどーい
     */
    public enum Type
    {
        /**
         * 全部。
         */
        ALL,
        /**
         * キックのみ。
         */
        KICK,
        /**
         * BANのみ。
         */
        BAN;

        /** StringをTypeに変換する。
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
        /**
         * UNIX時間。
         */
        private final long date;
        /**
         * 罪状。
         */
        private final String reason;
        /**
         * 管理ID？
         */
        private final String playerId;
        /**
         * UUID。
         */
        private final String uuId;
        /**
         * 管理ID。
         */
        private final String id;
        /**
         * 処罰方法。
         */
        private final Type type;

        /** コンストラクタで組み立てる。
         * @param date UNIX時間。
         * @param reason 罪状。
         * @param playerId 管理ID？
         * @param uuId UUID。
         * @param id 管理ID。
         * @param type 処罰方法。
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

        /** UNIX時間のゲッター。
         * @return UNIX時間。
         */
        public long getDate()
        {
            return date;
        }

        /** 罪状のゲッター。
         * @return 罪状。
         */
        public String getReason()
        {
            return reason;
        }

        /** 管理IDのゲッター。
         * @return 管理ID。
         */
        public String getId()
        {
            return id;
        }

        /** 管理ID？のゲッター。
         * @return 管理ID？
         */
        public String getPlayerId()
        {
            return playerId;
        }

        /** UUIDのゲッター。
         * @return UUID。
         */
        public String getUuId()
        {
            return uuId;
        }

        /** 処罰方法のゲッター。
         * @return 処罰方法。
         */
        public Type getType()
        {
            return type;
        }
    }

}

