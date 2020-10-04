package ml.peya.plugins.Moderate;

import ml.peya.plugins.DetectClasses.WatchEyeManagement;
import ml.peya.plugins.Objects.Decorations;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import ml.peya.plugins.Utils.MessageEngine;
import ml.peya.plugins.Utils.PlayerUtils;
import ml.peya.plugins.Utils.SQL;
import ml.peya.plugins.Utils.TimeParser;
import ml.peya.plugins.Utils.Utils;
import ml.peya.plugins.Variables;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * BAN発行等
 */
public class BanManager
{
    /**
     * BAN!!!!!
     *
     * @param player 対象プレイヤー
     * @param reason 理由
     * @param date   解除日時(Nullable)
     */
    public static void ban(Player player, String reason, @Nullable Date date)
    {
        player.setMetadata("psac-kick", new FixedMetadataValue(PeyangSuperbAntiCheat.getPlugin(), true));

        HashMap<String, Object> map = new HashMap<>();

        String id = Abuse.genRandomId(8);

        map.put("reason", reason);
        map.put("ggid", PlayerUtils.getGGID(id.hashCode()));
        map.put("id", id);

        Decorations.decoration(player);
        BroadcastMessenger.broadCast(false, player);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {

                if (Variables.config.getBoolean("decoration.lightning"))
                    Decorations.lighting(player);

                try (Connection connection = Variables.banKick.getConnection())
                {
                    SQL.insert(connection, "ban",
                            player.getName(),
                            player.getUniqueId().toString().replace("-", ""),
                            id,
                            new Date().getTime(),
                            reason,
                            date == null ? "_PERM": date.getTime(),
                            1,
                            0,
                            ""
                    );

                    WatchEyeManagement.deleteReportWithPlayerID(player.getUniqueId().toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Utils.errorNotification(Utils.getStackTrace(e));
                }

                String message = MessageEngine.get("ban.permReason", map);

                if (date != null)
                {
                    map.put("date", TimeParser.convertFromDate(date));
                    message = MessageEngine.get("ban.tempReason", map);
                }


                Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), reason, date, id).save();
                player.kickPlayer(message);

                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), Math.multiplyExact(Variables.config.getInt("kick.delay"), 20));
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

                BanEntry entry = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(set.getString("PLAYER"));
                if (entry != null)
                    Bukkit.getBanList(BanList.Type.NAME).pardon(set.getString("PLAYER"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
