package ml.peya.plugins;

import ml.peya.plugins.Utils.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
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

        ComponentBuilder builder = new ComponentBuilder("");
        builder.append("\n");
        builder.append("[PEYANG ANTI CHEAT からのお知らせ]")
                .color(ChatColor.DARK_RED);;
        builder.append("\n");

        builder.append("Peyang Anti Cheat はこの７日間に ")
                .color(ChatColor.WHITE);

        builder.append(String.format("%,d", watchdog))
                .color(ChatColor.RED)
                .bold(true);

        builder.append(" 人のプレイヤーをキックしました。")
                .color(ChatColor.WHITE)
                .bold(false);

        builder.append("\n");

        builder.append("スタッフがこの７日間に追加で ")
                .color(ChatColor.WHITE);

        builder.append(String.format("%,d", staff))
                .color(ChatColor.RED)
                .bold(true);

        builder.append(" 人をキックしました。")
                .color(ChatColor.WHITE)
                .bold(false);

        builder.append("\n");

        builder.append("禁止されている MOD はアクセス禁止の対象です！")
                .color(ChatColor.RED);

        builder.append("\n");

        for (Player player: Bukkit.getOnlinePlayers())
            if (player.hasPermission("psr.regular"))
                player.spigot().sendMessage(builder.create());
    }
}
