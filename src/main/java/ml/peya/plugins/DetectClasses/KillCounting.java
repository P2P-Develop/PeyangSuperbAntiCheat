package ml.peya.plugins.DetectClasses;

import ml.peya.plugins.Detect.DetectConnection;
import ml.peya.plugins.Enum.DetectType;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

import static ml.peya.plugins.Variables.cheatMeta;
import static ml.peya.plugins.Variables.config;

/**
 * キル回数の計測をする。
 */
public class KillCounting
{
    /**
     * プレイヤー。
     */
    private final HashMap<UUID, Integer> players;

    /**
     * コンストラクターでplayersのインスタンスを生成する。
     */
    public KillCounting()
    {
        players = new HashMap<>();
    }

    /**
     * 引数付きの関数を呼び出してもらうようにする。
     *
     * @param killer キルしたプレイヤーのUUID。
     */
    public void kill(UUID killer)
    {
        if (players.containsKey(killer))
        {
            players.put(killer, players.get(killer) + 1);
            if (players.get(killer) >= config.getInt("npc.kill"))
            {//カウント
                if (!cheatMeta.exists(killer))
                    DetectConnection.scan(Bukkit.getPlayer(killer), DetectType.AURA_BOT, null, true);
                players.remove(killer);
            }//検証用
            return;
        }
        players.put(killer, 1);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                players.remove(killer);
                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), Math.multiplyExact(config.getInt("npc.seconds"), 20));
    }

    /**
     * プレイヤーのゲッター。
     *
     * @return プレイヤー。
     */
    public HashMap<UUID, Integer> getPlayers()
    {
        return players;
    }
}
