package ml.peya.plugins.Moderate;

import ml.peya.plugins.DetectClasses.WatchEyeManagement;
import ml.peya.plugins.Objects.Decorations;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import ml.peya.plugins.Utils.MessageEngine;
import ml.peya.plugins.Utils.PlayerUtils;
import ml.peya.plugins.Utils.TimeParser;
import ml.peya.plugins.Variables;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.HashMap;

/**
 * BAN発行等
 */
public class BanWithEffect extends BanModifier
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

                BanModifier.ban(player.getUniqueId(), reason, date);
                WatchEyeManagement.deleteReportWithPlayerID(player.getUniqueId().toString());

                String message = MessageEngine.get("ban.permReason", map);

                if (date != null)
                {
                    map.put("date", TimeParser.convertFromDate(date));
                    message = MessageEngine.get("ban.tempReason", map);
                }

                player.kickPlayer(message);

                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), Math.multiplyExact(Variables.config.getInt("kick.delay"), 20));
    }

}
