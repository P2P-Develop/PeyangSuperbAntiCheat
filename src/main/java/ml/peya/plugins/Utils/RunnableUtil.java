package ml.peya.plugins.Utils;

import org.bukkit.scheduler.*;

import java.lang.reflect.*;

public class RunnableUtil
{
    public static boolean isStarted(BukkitRunnable runnable)
    {
        try
        {
            return !runnable.isCancelled();
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
