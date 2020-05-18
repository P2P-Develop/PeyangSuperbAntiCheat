package ml.peya.plugins;

import ml.peya.plugins.Utils.*;
import net.citizensnpcs.api.event.*;
import org.bukkit.event.*;

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
}
