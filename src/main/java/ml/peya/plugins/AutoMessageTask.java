package ml.peya.plugins;

import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class AutoMessageTask extends BukkitRunnable
{
    @Override
    public void run()
    {
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());

        calender.add(Calendar.DAY_OF_MONTH, - 7);

        Date date = Date.from(calender.toInstant());

        int watchdog = 0;
        int staff = 0;

        try (Connection connection = PeyangSuperbAntiCheat.banKick.getConnection();
        Statement statement = connection.createStatement();
        Statement statement2 = connection.createStatement())
        {
            ResultSet result = statement.executeQuery("SeLeCt * FrOm kIcK WhErE DaTe BeTwEeN " +
                    date.getTime() +
                    " AnD " +
                    new Date().getTime() +
                    " AnD StAfF=0");
            while (result.next())
                watchdog++;

            ResultSet result2 = statement2.executeQuery("SeLeCt * FrOm kIcK WhErE DaTe BeTwEeN " +
                    date.getTime() +
                    " AnD " +
                    new Date().getTime() +
                    " AnD StAfF=1");
            while (result2.next())
                staff++;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("count", String.valueOf(watchdog));
        map.put("staff_count", String.valueOf(staff));

        for (Player player: Bukkit.getOnlinePlayers())
            if (player.hasPermission("psac.regular"))
                player.sendMessage(MessageEngihe.get("autoMessage", map));
    }
}
