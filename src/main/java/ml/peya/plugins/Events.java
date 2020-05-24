package ml.peya.plugins;

import ml.peya.plugins.Utils.*;
import net.citizensnpcs.api.event.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class Events implements Listener
{
    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(NPCDamageByEntityEvent e)
    {
        DetectingList metas = PeyangSuperbAntiCheat.cheatMeta;
        for (CheatDetectNowMeta meta: metas.getMetas())
        {
            if (meta.getUuids() != e.getNPC().getUniqueId())
                return;
            if (meta.getTarget().getUniqueId() == e.getDamager().getUniqueId())
                System.out.println(meta.addVL());
        }

    }

    @EventHandler
    public void onKill(PlayerDeathEvent e)
    {
        if (e.getEntity().getKiller() == null)
            return;
        PeyangSuperbAntiCheat.counting.kill(e.getEntity().getKiller().getUniqueId());

    }
}
