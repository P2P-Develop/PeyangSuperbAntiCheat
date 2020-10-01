package ml.peya.plugins.Task;

import ml.peya.plugins.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.banKick;

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

        try (Connection connection = banKick.getConnection();
             PreparedStatement wdStatement =
                     connection.prepareStatement("SELECT STAFF FROM kick WHERE DATE BETWEEN ? AND ? AND STAFF=1 "+
                                                "UNION SELECT STAFF FROM ban WHERE DATE BETWEEN ? AND ? AND STAFF=0"))
        {
            wdStatement.setLong(1, date.getTime());
            wdStatement.setLong(2, new Date().getTime());
            wdStatement.setInt(3, 0);
            ResultSet wd = wdStatement.executeQuery();
            while (wd.next())
                if (wd.getInt("STAFF") == 0)
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
