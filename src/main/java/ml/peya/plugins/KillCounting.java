package ml.peya.plugins;

import ml.peya.plugins.Detect.*;
import ml.peya.plugins.Enum.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.scheduler.*;

import java.lang.reflect.*;
import java.util.*;

public class KillCounting
{
    private final HashMap<UUID, Integer> players;

    public KillCounting()
    {
        players = new HashMap<>();
    }

    public void kill(UUID killer)//killされたときに呼び出されるやつ(?)
    {
        FileConfiguration config = PeyangSuperbAntiCheat.config;
        if (players.containsKey(killer))
        {
            players.put(killer, players.get(killer) + 1);
            if (players.get(killer) >= PeyangSuperbAntiCheat.config.getInt("npc.kill"))
            {//カウント
                if (!PeyangSuperbAntiCheat.cheatMeta.exists(killer))
                    NPCConnection.scan(Bukkit.getPlayer(killer), DetectType.AURA_BOT, null);
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
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * config.getInt("npc.seconds"));

    }

    public HashMap<UUID, Integer> getPlayers()
    {
        return players;
    }
}
