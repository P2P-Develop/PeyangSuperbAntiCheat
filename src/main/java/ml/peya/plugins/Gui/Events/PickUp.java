package ml.peya.plugins.Gui.Events;

import ml.peya.plugins.Gui.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

import java.util.*;

public class PickUp implements Listener
{
    @EventHandler
    public void onPickUp(InventoryPickupItemEvent e)
    {
        ItemStack itemStack = e.getItem().getItemStack();

        if (!Item.canGuiItem(itemStack))
            return;

        if (Objects.equals(Item.getType(itemStack), "RELOADING"))
            e.setCancelled(true);
    }
}
