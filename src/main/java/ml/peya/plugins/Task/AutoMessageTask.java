package ml.peya.plugins.Task;

import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;

import java.sql.*;
import java.util.Date;
import java.util.*;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.banKick;

/**
 * 定期メッセージを発行するタスク。
 */
public class AutoMessageTask extends BukkitRunnable
{
    /**
     * タスクの処理を書く。
     */
    @Override
    public void run()
    {
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());

        calender.add(Calendar.DAY_OF_MONTH, -7);

        Date date = Date.from(calender.toInstant());

        int watchdog = 0;
        int staff = 0;

        try (Connection connection = banKick.getConnection();
             Statement statement = connection.createStatement();
             Statement statement2 = connection.createStatement())
        {
            ResultSet wd = statement.executeQuery("SeLeCt * FrOm kIcK WhErE DaTe BeTwEeN " +
                    date.getTime() +
                    " AnD " +
                    new Date().getTime() +
                    " AnD StAfF=0"); //XXX: 短縮不可 => 無限ループ
            ResultSet s = statement2.executeQuery("SeLeCt * FrOm kIcK WhErE DaTe BeTwEeN " +
                    date.getTime() +
                    " AnD " +
                    new Date().getTime() +
                    " AnD StAfF=1");

            while (wd.next())
                watchdog++;
            while (s.next())
                staff++;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("count", String.valueOf(watchdog));
        map.put("staff_count", String.valueOf(staff));

        Bukkit.getOnlinePlayers().parallelStream().filter(player -> player.hasPermission("psac.regular")).forEachOrdered(player -> {
            player.sendMessage("");
            player.sendMessage(get("autoMessage", map));
            player.sendMessage("");
        });
    }
}
