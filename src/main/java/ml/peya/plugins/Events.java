package ml.peya.plugins;

import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class Events implements Listener
{
    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageByEntityEvent e)
    {
        if (!e.getEntity().hasMetadata("NPC"))
            return;

    }
}
