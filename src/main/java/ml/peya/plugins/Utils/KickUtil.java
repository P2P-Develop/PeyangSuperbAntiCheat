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
    public static void kickPlayer (Player player, String reason, boolean wdFlag, boolean isTest)
    {
        broadCast(wdFlag, player);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                kick(player, reason, isTest, !wdFlag);
                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * PeyangSuperbAntiCheat.config.getInt("kick.delay"));


    }

    private static void broadCast(boolean wdFlag, Player target)
    {
        if (wdFlag)
        {
            for (Player player: Bukkit.getOnlinePlayers())
            {
                if(Permission.hasStaff(player))
                    player.spigot().sendMessage(TextBuilder.getBroadCastWdDetectionText(target).create());
                else if (player.hasPermission("psr.notification"))
                    player.spigot().sendMessage(TextBuilder.getBroadCastWdDetectionText().create());
            }
        }

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Bukkit.broadcast(ChatColor.RED + ChatColor.BOLD.toString() +
                        "違反行為をしたプレイヤーをゲームから対処しました。\n" +
                        ChatColor.AQUA + "ご報告ありがとうございました！", "psr.notification");
                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 15);
    }

    private static void kick(Player player, String reason, boolean isTest, boolean opFlag)
    {
        if (PeyangSuperbAntiCheat.config.getBoolean("kick.lightning"))
            player.getWorld().strikeLightningEffect(player.getLocation());

        StringBuilder id = new StringBuilder("#");
        Random random = new Random();
        for (int i = 0; i < 8; i++)
        {
            if (random.nextBoolean())
                id.append(random.nextInt(9));
            else
                id.append((char)(random.nextInt(5) + 'A'));
        }

        String reasonP;

        if (isTest)
            reasonP = "PEYANG CHEAT TEST";
        else if (opFlag)
            reasonP = "KICKED BY STAFF";
        else
            reasonP = "PEYANG CHEAT DETECTION ";

        String message = ChatColor.RED + "あなたは、このサーバーからキックされました！" +
                "\n" +
                ChatColor.GRAY + "理由：" +
                ChatColor.WHITE +
                reasonP +
                "\n" +
                "\n" +
                ChatColor.GRAY + "Kick ID：" +
                ChatColor.WHITE + id.toString();
        if (isTest)
        {
            player.kickPlayer(message);
            return;
        }
        try (Connection kickC = PeyangSuperbAntiCheat.banKick.getConnection();
             Connection eyeC = PeyangSuperbAntiCheat.eye.getConnection();
             Statement kickS = kickC.createStatement();
             Statement eyeS = eyeC.createStatement();
             Statement eyeS2 = eyeC.createStatement();
             Statement eyeS3 = eyeC.createStatement())
        {
            kickS.execute("InSeRt InTo KiCk VaLuEs(" +
                    "'" + player.getName() + "'," +
                    "'" + player.getUniqueId().toString() + "'," +
                    "'" + id.toString() + "'," +
                    "'" + new Date().getTime() + "'," +
                    "'" + reason + "'"+
                    ");");

            ResultSet eyeList = eyeS.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE UuId = '" + player.getUniqueId().toString().replace("-", "") + "'");

            while (eyeList.next())
            {
                String MNGID = eyeList.getString("MNGID");
                eyeS2.execute("DeLeTe FrOm WaTcHrEaSoN WhErE MnGiD = '" + MNGID + "'");
                eyeS3.execute("DeLeTe FrOm WaTchEyE WhErE MnGiD = '" + MNGID + "'");
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
