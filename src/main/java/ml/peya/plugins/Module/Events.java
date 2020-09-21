package ml.peya.plugins.Module;

import me.rerere.matrix.api.HackType;
import me.rerere.matrix.api.events.PlayerViolationEvent;
import ml.peya.plugins.Detect.DetectConnection;
import ml.peya.plugins.Enum.DetectType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * イベント(Matrix用
 */
public class Events implements Listener
{
    /**
     * プレイヤーVLイベント
     *
     * @param e イベント
     */
    @EventHandler
    public static void vl(PlayerViolationEvent e)
    {
        if (e.getHackType() == HackType.KILLAURA && e.getViolations() == 10)
        {
            DetectConnection.scan(e.getPlayer(), DetectType.AURA_BOT, null, false);
            ModuleContainer.matrix.setViolations(e.getPlayer(), HackType.KILLAURA, 0);
        }
    }
}
