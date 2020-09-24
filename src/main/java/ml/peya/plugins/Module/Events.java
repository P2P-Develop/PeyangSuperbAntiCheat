package ml.peya.plugins.Module;

import me.rerere.matrix.api.*;
import me.rerere.matrix.api.events.*;
import ml.peya.plugins.Detect.*;
import ml.peya.plugins.Enum.*;
import org.bukkit.event.*;

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
