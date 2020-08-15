package ml.peya.plugins.Detect;

import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.lang3.tuple.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.sql.*;
import java.util.*;

/**
 * キック時の処理などを管理する。
 */
public class DetectConnection
{
    /**
     * アーマー付きでスポーンさせる。
     *
     * @param player    プレイヤー。
     * @param type      判定タイプ。
     * @param reachMode リーチモードかどうか。
     * @return 万能クラス。
     */
    public static CheatDetectNowMeta spawnWithArmor(Player player, DetectType type, boolean reachMode)
    {
        EntityPlayer uuid = NPC.spawn(player, type, reachMode);
        CheatDetectNowMeta meta = Variables.cheatMeta.add(player, uuid.getUniqueID(), uuid.getId(), type);
        meta.setTesting(true);
        return meta;
    }

    /**
     * AntiKB用。
     *
     * @param player    プレイヤー。
     * @param type      判定タイプ。
     * @param sender    イベントsender。
     * @param reachMode リーチモードかどうか。
     */
    public static void scan(Player player, DetectType type, CommandSender sender, boolean reachMode)
    {
        if (type == DetectType.ANTI_KB)
        {
            TestKnockback.scan(player, type, sender);
            return;
        }

        CheatDetectNowMeta meta = spawnWithArmor(player, type, reachMode);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                meta.setTesting(false);

                final double vl = meta.getVL();
                final double seconds = Variables.cheatMeta.getMetaByPlayerUUID(player.getUniqueId()).getSeconds();

                if (Variables.learnCount > Variables.learnCountLimit)
                {
                    if (Variables.network.commit(Pair.of(vl, seconds)) > 0.01)
                    {
                        learn(vl, seconds);

                        if (kick(player)) return;
                    }
                }
                else if (Variables.banLeft <= vl)
                {
                    learn(vl, seconds);

                    if (kick(player)) return;
                }

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        String name = player.getDisplayName() + (player.getDisplayName().equals(player.getName()) ? "": (" (" + player.getName() + ") "));

                        switch (type)
                        {
                            case AURA_BOT:
                                if (sender == null)
                                    Bukkit.getOnlinePlayers().parallelStream().filter(np -> np.hasPermission("psac.aurabot")).forEachOrdered(np -> np.spigot().sendMessage(TextBuilder.textTestRep(name, meta.getVL(), Variables.banLeft).create()));
                                else
                                    sender.spigot().sendMessage(TextBuilder.textTestRep(name, meta.getVL(), Variables.banLeft).create());
                                break;

                            case AURA_PANIC:
                                if (sender == null)
                                    Bukkit.getOnlinePlayers().parallelStream().filter(np -> np.hasPermission("psac.aurapanic")).forEachOrdered(np -> np.spigot().sendMessage(TextBuilder.textPanicRep(name, meta.getVL()).create()));
                                else
                                    sender.spigot().sendMessage(TextBuilder.textPanicRep(name, meta.getVL()).create());
                                break;
                        }

                        Variables.cheatMeta.remove(meta.getUUIDs());
                        this.cancel();
                    }
                }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 10);
                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), Math.multiplyExact(Variables.config.getInt("npc.seconds"), 20));
    }

    /**
     * 学習回数を増やしAIを学習させる。
     *
     * @param vl 評価したVL。
     * @param seconds NPCに対しメンチ切った秒数。
     */
    private static void learn(double vl, double seconds)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                ArrayList<Triple<Double, Double, Double>> arr = new ArrayList<>();
                arr.add(Triple.of(vl, seconds, seconds / vl));
                Variables.learnCount++;
                Variables.network.learn(arr, 1000);

                this.cancel();
            }
        }.runTask(PeyangSuperbAntiCheat.getPlugin());
    }

    /**
     * キック動作の開始DA!
     *
     * @param player プレイヤー０。
     * @return 処理が正常に終了したかどうか。
     */
    private static boolean kick(Player player)
    {
        ArrayList<String> reason = new ArrayList<>();
        try (Connection connection = Variables.eye.getConnection();
             Statement statement = connection.createStatement();
             Statement statement1 = connection.createStatement())
        {
            if (WatchEyeManagement.isInjection(player.getName()))
                return false;
            ResultSet rs = statement.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE ID='" + player.getName() + "'");
            while (rs.next())
            {
                ResultSet set = statement1.executeQuery("SeLeCt * FrOm WaTcHrEaSon WhErE MNGID='" +
                        rs.getString("MNGID") + "'");
                while (set.next())
                    reason.add(Objects.requireNonNull(CheatTypeUtils.getCheatTypeFromString(set.getString("REASON"))).getText());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
        }

        ArrayList<String> realReason = new ArrayList<>(new HashSet<>(reason));

        KickManager.kickPlayer(player, (String.join(", ", realReason).equals("") ? "KillAura": "Report: " + String.join(", ", realReason)), true, false);
        return true;
    }
}
