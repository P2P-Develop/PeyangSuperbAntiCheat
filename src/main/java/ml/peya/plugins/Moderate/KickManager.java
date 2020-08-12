package ml.peya.plugins.Moderate;

import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.stream.*;

/**
 * プレイヤーのキックと共にいろいろやってくれるやつ。
 */
public class KickManager
{
    /**
     * Bukkit的キックをかます。
     *
     * @param player 対象プレイヤー。
     * @param reason 判定タイプ。
     * @param wdFlag 報告してるか...どうか？
     * @param isTest テストで捕まったか...どうか？
     */
    public static void kickPlayer(Player player, String reason, boolean wdFlag, boolean isTest) throws ArithmeticException
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
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), Math.multiplyExact(Variables.config.getInt("kick.delay"), 20));
    }

    /**
     * 全員にメッセージ送りつけるやつ。
     *
     * @param wdFlag 報告してるか...どうか？
     * @param target 対象プレイヤー。
     */
    private static void broadCast(boolean wdFlag, Player target)
    {
        if (wdFlag)
        {
            Bukkit.getOnlinePlayers().parallelStream().forEachOrdered(player -> {
                if (player.hasPermission("psac.ntfadmin"))
                    player.spigot().sendMessage(TextBuilder.getBroadCastWdDetectionText(target).create());
                else if (player.hasPermission("psac.notification"))
                    player.spigot().sendMessage(TextBuilder.getBroadCastWdDetectionText().create());
            });
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

    /**
     * 色々やってから結局蹴るやつ。
     *
     * @param player 対象プレイヤー。
     * @param reason 判定タイプ。
     * @param isTest テストで捕まったか...どうか？
     * @param opFlag OPが入ってたか......どうか？
     */
    private static void kick(Player player, String reason, boolean isTest, boolean opFlag)
    {
        if (Variables.config.getBoolean("kick.lightning"))
            player.getWorld().strikeLightningEffect(player.getLocation());

        StringBuilder id = new StringBuilder();
        Random random = new Random();
        IntStream.range(0, 8).parallel().forEachOrdered(i -> {
            if (random.nextBoolean())
                id.append(random.nextInt(9));
            else
                id.append((char) (random.nextInt(5) + 'A'));
        });

        String reasonP;

        if (isTest)
            reasonP = "PEYANG CHEAT TEST";
        else if (opFlag)
            reasonP = "KICKED BY STAFF";
        else
            reasonP = "PEYANG CHEAT DETECTION ";

        HashMap<String, Object> map = new HashMap<>();

        map.put("reason", reasonP);
        map.put("ggid", IntStream.range(0, 7).parallel().mapToObj(i -> String.valueOf(random.nextInt(9))).collect(Collectors.joining()));
        map.put("id", id.toString());

        String message = MessageEngine.get("kick.reason", map);

        if (isTest)
        {
            player.kickPlayer(message);
            return;
        }
        try (Connection kickC = Variables.banKick.getConnection();
             Connection eyeC = Variables.eye.getConnection();
             Statement kickS = kickC.createStatement();
             Statement eyeS = eyeC.createStatement();
             Statement eyeS2 = eyeC.createStatement();
             Statement eyeS3 = eyeC.createStatement())
        {
            kickS.execute("InSeRt InTo KiCk VaLuEs(" +
                    "'" + player.getName().replace("'", "\\'") + "'," +
                    "'" + player.getUniqueId().toString().replace("'", "\\'") + "'," +
                    "'" + id.toString().replace("'", "\\'") + "'," +
                    "" + new Date().getTime() + "," +
                    "'" + reason.replace("'", "\\'") + "', " +
                    (opFlag ? 1: 0) +
                    ");");

            ResultSet eyeList = eyeS.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE UuId = '" + player.getUniqueId().toString().replace("-", "").replace("'", "\\'") + "'");

            while (eyeList.next())
            {
                String MNGID = eyeList.getString("MNGID");
                if (WatchEyeManagement.isInjection(MNGID))
                    return;
                eyeS2.execute("DeLeTe FrOm WaTcHrEaSoN WhErE MnGiD = '" + MNGID + "'");
                eyeS3.execute("DeLeTe FrOm WaTchEyE WhErE MnGiD = '" + MNGID + "'");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
        }
        player.kickPlayer(message);
    }
}
