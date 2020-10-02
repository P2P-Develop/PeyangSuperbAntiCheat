package ml.peya.plugins.Moderate;

import ml.peya.plugins.DetectClasses.WatchEyeManagement;
import ml.peya.plugins.Objects.Decorations;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import ml.peya.plugins.Utils.MessageEngine;
import ml.peya.plugins.Utils.SQL;
import ml.peya.plugins.Utils.Utils;
import ml.peya.plugins.Variables;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;

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
        map.put("ggid", Abuse.genRandomId(7));
        map.put("id", id);

        Decorations.decoration(player);
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
                            1
                    );

                    WatchEyeManagement.deleteReportWithPlayerID(player.getUniqueId().toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Utils.errorNotification(Utils.getStackTrace(e));
                }

                String message = date == null ? MessageEngine.get("ban.permReason", map): MessageEngine.get("ban.tempReason", map);
                Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), message, date, null);
                player.kickPlayer(message);

                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), Math.multiplyExact(Variables.config.getInt("kick.delay"), 20));
    }
}
