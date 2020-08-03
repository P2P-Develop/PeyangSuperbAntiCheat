package ml.peya.plugins.Detect;

import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.*;

class TestKnockback
{
    public static void scan(Player player, DetectType type, CommandSender sender)
    {
        if (type == DetectType.AURA_BOT || type == DetectType.AURA_PANIC)
            return;

        Location location = player.getLocation();
        location.add(0, 1, 0);
        location.setPitch(0);
        Vector loc = location.getDirection();
        loc.multiply(-0.1f);
        location.add(loc);


        Arrow arrow = (Arrow) player.getWorld().spawnEntity(location, EntityType.ARROW);
        PeyangSuperbAntiCheat.cheatMeta.add(player, arrow.getUniqueId(), arrow.getEntityId(), DetectType.ANTI_KB);
        arrow.setMetadata("testArrow-" + arrow.getUniqueId(), new FixedMetadataValue(PeyangSuperbAntiCheat.getPlugin(), player.getUniqueId()));
        Bukkit.getOnlinePlayers().parallelStream().map(hide -> ((CraftPlayer) hide).getHandle().playerConnection).forEachOrdered(connection -> connection.sendPacket(new PacketPlayOutEntityDestroy(arrow.getEntityId())));

        Vector speed = location.getDirection();
        speed.multiply(32767f);
        arrow.setVelocity(speed);

        CheatDetectNowMeta meta = PeyangSuperbAntiCheat.cheatMeta.add(player, arrow.getUniqueId(), arrow.getEntityId(), type);
        meta.setCanTesting(true);


        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                String path = "message.testkb.normal";
                if (PeyangSuperbAntiCheat.config.getBoolean("message.lynx"))
                    path = "message.testkb.lynx";
                sender.sendMessage(MessageEngine.get(path, MessageEngine.hsh("name", player.getName())));
                arrow.remove();
                PeyangSuperbAntiCheat.cheatMeta.remove(arrow.getUniqueId());
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20);

    }
}
