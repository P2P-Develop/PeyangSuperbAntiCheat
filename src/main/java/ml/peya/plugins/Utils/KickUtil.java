package ml.peya.plugins.Utils;

import ml.peya.plugins.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class KickUtil
{
    public static void kickPlayer (Player player, boolean wdFlag, boolean isTest)
    {
        broadCast(wdFlag, player);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                kick(player, isTest, wdFlag);
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * PeyangSuperbAntiCheat.config.getInt("kick.delay"));


    }

    private static void broadCast(boolean wdFlag, Player target)
    {
        if (wdFlag)
        {
            for (Player player: Bukkit.getOnlinePlayers())
            {
                if(player.hasPermission("psr.admin"))
                    player.spigot().sendMessage(TextBuilder.getBroadCastWdDetectionTexdt(target).create());
                else
                    player.spigot().sendMessage(TextBuilder.getBroadCastWdDetectionTexdt().create());
            }
        }

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Bukkit.broadcast(ChatColor.RED + ChatColor.BOLD.toString() +
                        "プレイヤーはハッキングなどの理由でゲームから削除されました。" +
                        ChatColor.AQUA + "ご報告ありがとうございました！", "psr.notification");
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 15);
    }

    private static void kick(Player player, boolean isTest, boolean opFlag)
    {
        UUID uuid = UUID.randomUUID();
        String reason;

        if (isTest)
            reason = "PYGANTICHEAT TEST";
        else if (opFlag)
            reason = "KICKED BY STAFF";
        else
            reason = "PYGANTICHEAT DETECTION ";

        String message = ChatColor.RED + "あなたは、このサーバーからKickされました！" +
                "\n" +
                ChatColor.GRAY + "理由: " +
                ChatColor.WHITE +
                reason +
                "\n" +
                "\n" +
                ChatColor.GRAY + "Kick ID: " +
                ChatColor.WHITE + uuid.toString();
        if (isTest)
        {

            player.kickPlayer(message);
            return;
        }
        try (Connection kickC = PeyangSuperbAntiCheat.banKick.getConnection();
             Connection eyeC = PeyangSuperbAntiCheat.eye.getConnection();
             Statement kickS = kickC.createStatement();
             Statement eyeS = eyeC.createStatement();
             Statement eyeS2 = eyeC.createStatement())
        {
            kickS.execute("InSeRt InTo KiCk VaLuEs(" +
                    "'" + player.getName() + "'," +
                    "'" + player.getUniqueId().toString() + "'," +
                    "'" + uuid.toString() + "'," +
                    "'" + new Date().getTime() + "');");

            ResultSet eyeList = eyeS.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE UuId = '" + player.getUniqueId().toString().replace("-", "") + "'");

            while (eyeList.next())
            {
                String MNGID = eyeList.getString("MNGID");
                eyeS2.execute("DeLeTe FrOm WaTcHrEaSoN WhErE MnGiD = '" + MNGID + "'");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }
        player.kickPlayer(message);
    }
}
