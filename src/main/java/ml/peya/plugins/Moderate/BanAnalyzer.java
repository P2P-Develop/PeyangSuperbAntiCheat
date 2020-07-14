package ml.peya.plugins.Moderate;

import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;

import java.sql.*;
import java.util.*;

public class BanAnalyzer
{
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
                    while(set.next())
                        abuses.add(new Bans(set.getLong("DATE"),
                                set.getString("REASON"),
                                set.getString("PLAYER"),
                                set.getString("UUID"),
                                set.getString("KICKID").replace("#", ""),
                                Type.KICK));
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
        
        // fuki
        return abuses;
    }
    public enum Type
    {
        ALL,
        KICK,
        BAN;

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

    public static class Bans
    {
        private final long date;
        private final String reason;
        private final String playerId;
        private final String uuId;
        private final String id;
        private final Type type;

        public Bans(long date, String reason, String playerId, String uuId, String id, Type type)
        {
            this.date = date;
            this.reason = reason;
            this.id = id;
            this.playerId = playerId;
            this.uuId = uuId;
            this.type = type;
        }

        public long getDate()
        {
            return date;
        }

        public String getReason()
        {
            return reason;
        }

        public String getId()
        {
            return id;
        }

        public String getPlayerId()
        {
            return playerId;
        }

        public String getUuId()
        {
            return uuId;
        }

        public Type getType()
        {
            return type;
        }
    }

}

