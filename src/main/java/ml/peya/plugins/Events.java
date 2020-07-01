package ml.peya.plugins;

import com.comphenix.protocol.injector.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class Events implements Listener
{
    @EventHandler
    public void onKill(PlayerDeathEvent e)
    {
        if (e.getEntity().getKiller() == null)
            return;
        PeyangSuperbAntiCheat.counting.kill(e.getEntity().getKiller().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageByEntityEvent e)
    {
        if (!(e.getEntity() instanceof CraftPlayer))
            return;
        if (!(e.getDamager() instanceof  CraftArrow))
            return;

        if (!PeyangSuperbAntiCheat.cheatMeta.exists(e.getEntity().getUniqueId()))
            return;

        if (!e.getDamager().hasMetadata("testArrow-" + e.getDamager().getUniqueId()))
            return;

        e.setDamage(0);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();

        PeyangSuperbAntiCheat.tracker.remove(player.getName());
    }
}
