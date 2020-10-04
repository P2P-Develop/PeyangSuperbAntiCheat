package ml.peya.plugins.Moderate;

import ml.peya.plugins.PeyangSuperbAntiCheat;
import ml.peya.plugins.Utils.TextBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static ml.peya.plugins.Utils.MessageEngine.get;

public class BroadcastMessenger
{
    /**
     * 全員にメッセージ送りつけるやつ。
     *
     * @param wdFlag NPC(等)による、オートキック...かどうか?
     * @param target 対象プレイヤー。
     */
    static void broadCast(boolean wdFlag, Player target)
    {
        if (wdFlag)
        {
            Bukkit.getOnlinePlayers().parallelStream().forEach(player ->
            {
                if (player.hasPermission("psac.ntfadmin"))
                    player.spigot().sendMessage(TextBuilder.getBroadCastWdDetectionText(target).create());
                else if (player.hasPermission("psac.notification"))
                    player.spigot().sendMessage(TextBuilder.getBroadCastWdDetectionText().create());
            });

            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    Bukkit.broadcast(get("kick.broadcast"), "psac.notification");
                    this.cancel();
                }
            }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 15);
        }
        else
            Bukkit.broadcast(get("kick.broadcast"), "psac.notification");

    }
}
