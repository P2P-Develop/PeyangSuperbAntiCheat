package ml.peya.plugins.Task;

import org.bukkit.scheduler.*;

import static ml.peya.plugins.Variables.tracker;

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
        tracker.tick();
    }
}
