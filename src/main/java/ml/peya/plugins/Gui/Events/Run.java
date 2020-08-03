package ml.peya.plugins.Gui.Events;

import ml.peya.plugins.Gui.*;
import ml.peya.plugins.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

import java.util.*;

public class Run implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e)
    {
        ItemStack itemStack = e.getItem();

        if (e.getItem() == null || e.getItem().getType() == Material.AIR || Item.canGuiItem(itemStack)) return;

        e.setCancelled(true);

        PeyangSuperbAntiCheat.item.getItems().stream().filter(items -> Objects.equals(Item.getType(itemStack), items.getExecName())).forEachOrdered(items -> items.run(e.getPlayer(), Item.getTarget(itemStack)));
    }
}
