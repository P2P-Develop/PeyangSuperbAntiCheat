package ml.peya.plugins.Moderate;

import ml.peya.plugins.DetectClasses.WatchEyeManagement;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import ml.peya.plugins.Utils.Decorations;
import ml.peya.plugins.Utils.MessageEngine;
import ml.peya.plugins.Utils.TextBuilder;
import ml.peya.plugins.Utils.Utils;
import ml.peya.plugins.Variables;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ml.peya.plugins.Utils.MessageEngine.get;

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
        player.setMetadata("psac-kick", new FixedMetadataValue(PeyangSuperbAntiCheat.getPlugin(), true));

        broadCast(wdFlag, player);
        decoration(player);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                kick(player, reason, isTest, !wdFlag);
                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), Math
                .multiplyExact(Variables.config.getInt("kick.delay"), 20));
    }

    /**
     * 全員にメッセージ送りつけるやつ。
     *
     * @param wdFlag NPC(等)による、オートキック...かどうか?
     * @param target 対象プレイヤー。
     */
    private static void broadCast(boolean wdFlag, Player target)
    {
        if (wdFlag)
            Bukkit.getOnlinePlayers().parallelStream().forEachOrdered(player ->
            {
                if (player.hasPermission("psac.ntfadmin"))
                    player.spigot().sendMessage(TextBuilder.getBroadCastWdDetectionText(target).create());
                else if (player.hasPermission("psac.notification"))
                    player.spigot().sendMessage(TextBuilder.getBroadCastWdDetectionText().create());
            });

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
        if (Variables.config.getBoolean("decoration.laser"))
            Decorations.laser(player, Math.multiplyExact(Variables.config.getInt("kick.delay"), 20));
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
        IntStream.range(0, 8).parallel().forEachOrdered(i ->
        {
            id.append(random.nextBoolean() ? random.nextInt(9): (char) (random.nextInt(5) + 'A'));
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
        map.put("ggid", IntStream.range(0, 7).parallel().mapToObj(i -> String.valueOf(random.nextInt(9)))
                .collect(Collectors.joining()));
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

            ResultSet eyeList = eyeS
                    .executeQuery("SeLeCt * FrOm WaTcHeYe WhErE UuId = '" + player.getUniqueId().toString()
                            .replace("-", "")
                            .replace("'", "\\'") + "'");

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
