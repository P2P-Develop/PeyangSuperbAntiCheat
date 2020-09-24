package ml.peya.plugins.Gui.Events;

import ml.peya.plugins.Gui.Item;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static ml.peya.plugins.Variables.item;

/**
 * イベントの根本的なやつ。
 */
public class Run implements Listener
{
    /**
     * GUIのアイテムをクリックした際のやつ
     *
     * @param e なんか使ったときに発令するイベント。
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e)
    {
        ItemStack itemStack = e.getItem();

        if (e.getItem() == null || e.getItem().getType() == Material.AIR || Item.canGuiItem(itemStack)) return;

        e.setCancelled(true);

        item.getItems()
            .parallelStream()
            .filter(items -> Objects.equals(Item.getType(itemStack), items.getExecName()))
            .forEachOrdered(items -> items.run(e.getPlayer(), Item.getTarget(itemStack)));
    }
}
