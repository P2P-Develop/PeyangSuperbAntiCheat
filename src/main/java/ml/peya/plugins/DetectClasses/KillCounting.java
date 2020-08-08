package ml.peya.plugins.DetectClasses;

import ml.peya.plugins.Detect.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;

import java.util.*;

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
     * コンストラクター。
     */
    public KillCounting()
    {
        players = new HashMap<>();
    }

    /** 引数付きの関数を呼び出してもらうようにする。
     * @param killer キルしたプレイヤーのUUID。
     */
    public void kill(UUID killer)
    {
        if (players.containsKey(killer))
        {
            players.put(killer, players.get(killer) + 1);
            if (players.get(killer) >= PeyangSuperbAntiCheat.config.getInt("npc.kill"))
            {//カウント
                if (!PeyangSuperbAntiCheat.cheatMeta.exists(killer))
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
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * PeyangSuperbAntiCheat.config.getInt("npc.seconds"));

    }

    /** プレイヤーのゲッター。
     * @return プレイヤー。
     */
    public HashMap<UUID, Integer> getPlayers()
    {
        return players;
    }
}
