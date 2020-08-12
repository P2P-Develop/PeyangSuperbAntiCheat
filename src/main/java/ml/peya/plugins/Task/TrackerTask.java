package ml.peya.plugins.Task;

import ml.peya.plugins.*;
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
