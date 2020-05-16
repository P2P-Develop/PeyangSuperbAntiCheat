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
        CheatDetectNowMeta meta = PeyangSuperbAntiCheat.cheatMeta;
        if (meta.getUuids() != e.getNPC().getUniqueId())
            return;
        System.out.println(meta.addVL());
    }
}
