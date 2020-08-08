package ml.peya.plugins.Utils;

import org.bukkit.scheduler.*;

/**
 * マルチスレッドをちょびっと拡張。
 */
public class RunnableUtil
{
    /** もうタスク始まってるかどうか。
     * @param runnable こいつしか出来ないんかい
     *
     * @return はじまってたらtrueを返します。
     */
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
