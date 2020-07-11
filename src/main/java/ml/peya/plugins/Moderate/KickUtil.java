package ml.peya.plugins.Moderate;

import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class KickUtil
{
    public static void kickPlayer(Player player, String reason, boolean wdFlag, boolean isTest)
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
        if (!wdFlag)
            continue;

        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (player.hasPermission("psac.ntfadmin"))
                player.spigot().sendMessage(TextBuilder.getBroadCastWdDetectionText(target).create());
            else if (player.hasPermission("psac.notification"))
                player.spigot().sendMessage(TextBuilder.getBroadCastWdDetectionText().create());
        }

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Bukkit.broadcast(MessageEngine.get("kick.broadcast"), "psac.notification");
                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 15);
    }

    private static void kick(Player player, String reason, boolean isTest, boolean opFlag)
    {
        if (PeyangSuperbAntiCheat.config.getBoolean("kick.lightning"))
            player.getWorld().strikeLightningEffect(player.getLocation());

        StringBuilder id = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++)
            if (random.nextBoolean())
                id.append(random.nextInt(9));
            else
                id.append((char) (random.nextInt(5) + 'A'));

        StringBuilder ggId = new StringBuilder();
        for (int i = 0; i < 7; i++)
            ggId.append(random.nextInt(9));


        String reasonP;

        if (isTest)
            reasonP = "PEYANG CHEAT TEST";
        else if (opFlag)
            reasonP = "KICKED BY STAFF";
        else
            reasonP = "PEYANG CHEAT DETECTION ";

        HashMap<String, Object> map = new HashMap<>();

        map.put("reason", reasonP);
        map.put("ggid", ggId.toString());
        map.put("id", id.toString());

        String message = MessageEngine.get("kick.reason", map);

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
                    "'" + player.getName().replace("'", "\\'") + "'," +
                    "'" + player.getUniqueId().toString().replace("'","\\'") + "'," +
                    "'" + id.toString() + "'," +
                    "" + new Date().getTime() + "," +
                    "'" + reason.replace("'","\\'") + "', " +
                    (opFlag ? 1: 0) +
                    ");");

            ResultSet eyeList = eyeS.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE UuId = '" + player.getUniqueId().toString().replace("-", "").replace("'","\\'") + "'");

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
