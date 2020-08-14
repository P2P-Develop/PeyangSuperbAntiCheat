package ml.peya.plugins.Bukkit.Task;

import ml.peya.plugins.Bukkit.*;
import org.bukkit.scheduler.*;

/**
 * トラッキングの情報を更新する定期タスク。
 */
public class TrackerTask extends BukkitRunnable
{
    /**
     * 更新する処理を書く。
     */
    @Override
    public void run()
    {
        Variables.tracker.tick();
    }
}
