package ml.peya.plugins.Detect;

import ml.peya.plugins.DetectClasses.CheatDetectNowMeta;
import ml.peya.plugins.DetectClasses.WatchEyeManagement;
import ml.peya.plugins.Enum.DetectType;
import ml.peya.plugins.Moderate.CheatTypeUtils;
import ml.peya.plugins.Moderate.KickManager;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import ml.peya.plugins.Utils.TextBuilder;
import ml.peya.plugins.Utils.Utils;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import static ml.peya.plugins.Variables.banLeft;
import static ml.peya.plugins.Variables.cheatMeta;
import static ml.peya.plugins.Variables.config;
import static ml.peya.plugins.Variables.eye;
import static ml.peya.plugins.Variables.learnCount;
import static ml.peya.plugins.Variables.learnCountLimit;
import static ml.peya.plugins.Variables.network;

/**
 * NPCの出陣命令を、NPCスポーン命令に変換
 * 装備を指定や、コマンド等から受け取った物の管理
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
        CheatDetectNowMeta meta = cheatMeta.add(player, uuid.getUniqueID(), uuid.getId(), type);
        meta.setTesting(true);
        return meta;
    }

    /**
     * スキャン
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
                final double seconds = cheatMeta.getMetaByPlayerUUID(player.getUniqueId()).getSeconds();

                if (learnCount > learnCountLimit)
                {
                    if (network.commit(Pair.of(vl, seconds)) > 0.01)
                    {
                        learn(vl, seconds);

                        if (kick(player)) return;
                    }
                }
                else if (banLeft <= vl)
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
                                    Bukkit.getOnlinePlayers().parallelStream()
                                            .filter(np -> np.hasPermission("psac.aurabot"))
                                            .forEachOrdered(np ->
                                                    np.spigot().sendMessage(TextBuilder.textTestRep(name, meta.getVL(), banLeft).create()));
                                else
                                    sender.spigot().sendMessage(TextBuilder.textTestRep(name, meta.getVL(), banLeft).create());
                                break;

                            case AURA_PANIC:
                                if (sender == null)
                                    Bukkit.getOnlinePlayers().parallelStream()
                                            .filter(np -> np.hasPermission("psac.aurapanic"))
                                            .forEachOrdered(np -> np.spigot().sendMessage(TextBuilder
                                                    .textPanicRep(name, meta.getVL()).create()));
                                else
                                    sender.spigot().sendMessage(TextBuilder.textPanicRep(name, meta.getVL()).create());
                                break;
                        }

                        cheatMeta.remove(meta.getUUIDs());
                        this.cancel();
                    }
                }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 10);
                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), Math.multiplyExact(config.getInt("npc.seconds"), 20));
    }

    /**
     * 学習回数を増やしAIを学習させる。
     *
     * @param vl      評価したVL。
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
                learnCount++;
                network.learn(arr, 1000);

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
    private static boolean kick(final Player player)
    {
        ArrayList<String> reason = new ArrayList<>();
        try (Connection connection = eye.getConnection();
             Statement statement = connection.createStatement();
             Statement statement1 = connection.createStatement())
        {

            final String name = WatchEyeManagement.parseInjection(player.getName());

            ResultSet rs = statement.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE ID='" + name + "'");
            while (rs.next())
            {

                final String MNGID = WatchEyeManagement.parseInjection(rs.getString("MNGID"));
                ResultSet set = statement1.executeQuery("SeLeCt * FrOm WaTcHrEaSon WhErE MNGID='" + MNGID + "'");
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

        KickManager.kickPlayer(player, (String.join(", ", realReason).equals("") ? "KillAura": "Report: " + String
                .join(", ", realReason)), true, false);
        return true;
    }
}
