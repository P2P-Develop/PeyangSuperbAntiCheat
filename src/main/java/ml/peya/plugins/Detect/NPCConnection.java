package ml.peya.plugins.Detect;

import ml.peya.plugins.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

public class NPCConnection
{
    public static CheatDetectNowMeta spawnWithArmor(Player player, DetectType type)
    {
        EntityPlayer uuid = NPC.spawn(player, type);
        CheatDetectNowMeta meta = PeyangSuperbAntiCheat.cheatMeta.add(player, uuid.getUniqueID(), uuid.getId(), type);
        meta.setCanNPC(true);
        return meta;
    }

    public static void scan(Player player, DetectType type, CommandSender sender)
    {
        CheatDetectNowMeta meta = spawnWithArmor(player, type);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                meta.setCanNPC(false);
                if (PeyangSuperbAntiCheat.banLeft <= meta.getVL())
                    KickUtil.kickPlayer(player, true, false);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        if (type == DetectType.AURA_BOT)
                        {
                            String name = player.getDisplayName() + (player.getDisplayName().equals(player.getName()) ? "" : (" (" + player.getName() + ") "));
                            if (sender != null)
                                sender.spigot().sendMessage(TextBuilder.textTestRep(name, meta.getVL(), PeyangSuperbAntiCheat.banLeft).create());
                            else
                            {
                                for (Player np : Bukkit.getOnlinePlayers())
                                {
                                    if (!np.hasPermission("psr.admin"))
                                        continue;
                                    np.spigot().sendMessage(TextBuilder.textTestRep(name, meta.getVL(), PeyangSuperbAntiCheat.banLeft).create());
                                }
                            }
                        }
                        else
                        {
                            String name = player.getDisplayName() + (player.getDisplayName().equals(player.getName()) ? "" : (" (" + player.getName() + ") "));
                            if (sender != null)
                                sender.spigot().sendMessage(TextBuilder.textPanicRep(name, meta.getVL()).create());
                            else
                            {
                                for (Player np : Bukkit.getOnlinePlayers())
                                {
                                    if (!np.hasPermission("psr.admin"))
                                        continue;
                                    np.spigot().sendMessage(TextBuilder.textPanicRep(name, meta.getVL()).create());
                                }
                            }
                        }

                        PeyangSuperbAntiCheat.cheatMeta.remove(meta.getUuids());
                    }
                }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 10);
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * PeyangSuperbAntiCheat.config.getInt("npc.seconds"));
    }
}
