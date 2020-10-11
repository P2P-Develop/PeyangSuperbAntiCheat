package ml.peya.plugins.Task;

import ml.peya.plugins.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.ban;
import static ml.peya.plugins.Variables.log;

/**
 * 定期メッセージを発行するタスク。
 */
public class AutoMessageTask extends BukkitRunnable
{
    /**
     * 定期メッセージを送信
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

        try (Connection connection = log.getConnection();
             PreparedStatement wd = connection.prepareStatement("SELECT STAFF FROM kick WHERE DATE BETWEEN ? AND ?");
             PreparedStatement sr = connection.prepareStatement("SELECT STAFF FROM ban WHERE DATE BETWEEN ? AND ?"))
        {
            wd.setLong(1, date.getTime());
            wd.setLong(2, new Date().getTime());
            sr.setLong(1, date.getTime());
            sr.setLong(2, new Date().getTime());
            ResultSet wdr = wd.executeQuery();
            while (wdr.next())
                if (wdr.getInt("STAFF") == 0)
                    watchdog++;
                else
                    staff++;

            ResultSet srr = sr.executeQuery();
            while (srr.next())
                if (srr.getInt("STAFF") == 0)
                    watchdog++;
                else
                    staff++;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
        }


        try (Connection connection = ban.getConnection();
             PreparedStatement sr = connection.prepareStatement("SELECT STAFF FROM ban WHERE DATE BETWEEN ? AND ?"))
        {
            sr.setLong(1, date.getTime());
            sr.setLong(2, new Date().getTime());
            ResultSet srr = sr.executeQuery();
            while (srr.next())
                if (srr.getInt("STAFF") == 0)
                    watchdog++;
                else
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

        Bukkit.getOnlinePlayers().parallelStream().filter(player -> player.hasPermission("psac.regular"))
                .forEachOrdered(player ->
                {
                    player.sendMessage("");
                    player.sendMessage(get("autoMessage", map));
                    player.sendMessage("");
                });
    }
}
