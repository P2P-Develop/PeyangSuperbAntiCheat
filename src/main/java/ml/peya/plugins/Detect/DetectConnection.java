package ml.peya.plugins.Detect;

import ml.peya.plugins.DetectClasses.CheatDetectNowMeta;
import ml.peya.plugins.Enum.DetectType;
import ml.peya.plugins.Moderate.KickManager;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import ml.peya.plugins.Utils.TextBuilder;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

import static ml.peya.plugins.Variables.banLeft;
import static ml.peya.plugins.Variables.cheatMeta;
import static ml.peya.plugins.Variables.config;
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
                final Double[] angles = cheatMeta.getMetaByPlayerUUID(player.getUniqueId()).getAngles().toArray(new Double[0]);
                //ごにょごにょする ←　一時的になんとなく実装

                if (learnCount > learnCountLimit)
                {
                    if (Arrays.stream(angles).mapToDouble(angle -> network.commit(Pair.of(vl, angle))).sum() / angles.length > 0.01)
                    {
                        Arrays.stream(angles).forEachOrdered(angle -> learn(vl, angle));

                        learnCount++;

                        if (kick(player)) return;
                    }
                }
                else if (banLeft <= vl)
                {
                    Arrays.stream(angles).forEachOrdered(angle -> learn(vl, angle));

                    learnCount++;

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
                                    Bukkit.getOnlinePlayers()
                                        .parallelStream()
                                        .filter(np -> np.hasPermission("psac.aurapanic"))
                                        .forEachOrdered(np -> np.spigot().sendMessage(TextBuilder.textPanicRep(name, meta.getVL()).create()));
                                else
                                    sender.spigot().sendMessage(TextBuilder.textPanicRep(name, meta.getVL()).create());
                                break;
                            default:
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
     * @param vl    評価したVL。
     * @param angle あんぐるず
     */
    private static void learn(double vl, double angle)
    {
        ArrayList<Triple<Double, Double, Double>> arr = new ArrayList<>();
        arr.add(Triple.of(vl, angle, angle / vl));
        network.learn(arr, 1000);
    }

    /**
     * キック動作の開始DA!
     *
     * @param player プレイヤー。
     * @return 処理が正常に終了したかどうか。
     */
    private static boolean kick(final Player player)
    {
        KickManager.kickPlayer(
            player,
            "PEYANG CHEAT DETECTION",
            true,
            false
        );
        return true;
    }
}
