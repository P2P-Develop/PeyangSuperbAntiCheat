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

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.config;

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
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), Math.multiplyExact(config.getInt("kick.delay"), 20));
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
                Bukkit.broadcast(get("kick.broadcast"), "psac.notification");
                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 15);
    }

    /**
     * デコ要素すべて展開するやつ
     *
     * @param player 被験者
     */
    public static void decoration(Player player)
    {
        if (Variables.config.getBoolean("decoration.flame"))
            Decorations.flame(player, Math.multiplyExact(Variables.config.getInt("kick.delay"), 20));
        if (Variables.config.getBoolean("decoration.circle"))
            Decorations.magic(player, Math.multiplyExact(Variables.config.getInt("kick.delay"), 20));
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
        if (Variables.config.getBoolean("decoration.lightning"))
            Decorations.lighting(player);
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
                    "'" + WatchEyeManagement.parseInjection(player.getName()) + "'," +
                    "'" + WatchEyeManagement.parseInjection(player.getUniqueId().toString()) + "'," +
                    "'" + WatchEyeManagement.parseInjection(id.toString()) + "'," +
                    "" + new Date().getTime() + "," +
                    "'" + WatchEyeManagement.parseInjection(reason) + "', " +
                    (opFlag ? 1: 0) +
                    ");");

            ResultSet eyeList = eyeS.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE UuId = '" + player.getUniqueId().toString().replace("-", "").replace("'", "\\'") + "'");

            while (eyeList.next())
            {
                String MNGID = eyeList.getString("MNGID");
                MNGID = WatchEyeManagement.parseInjection(MNGID);
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
