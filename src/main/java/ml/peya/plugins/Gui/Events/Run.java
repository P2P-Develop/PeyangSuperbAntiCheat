package ml.peya.plugins.Gui.Events;

import ml.peya.plugins.Gui.*;
import ml.peya.plugins.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.scheduler.*;

import java.util.*;

public class Run implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e)
    {
        ItemStack itemStack = e.getItem();

        if (e.getItem() == null || e.getItem().getType() == Material.AIR)
            return;

        Item item = PeyangSuperbAntiCheat.item;


        if (!Item.canGuiItem(itemStack))
            return;


        e.setCancelled(true);

        for (IItems items: item.getItems())
        {
            String type = items.getExecName();
            if (Objects.equals(Item.getType(itemStack), type))
                items.run(e.getPlayer(), Item.getTarget(itemStack));
        }
    }
}
