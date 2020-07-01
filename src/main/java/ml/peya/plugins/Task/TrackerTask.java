package ml.peya.plugins.Task;

import ml.peya.plugins.*;
import org.bukkit.scheduler.*;

public class TrackerTask extends BukkitRunnable
{
    @Override
    public void run()
    {
        PeyangSuperbAntiCheat.tracker.tick();
    }
}
