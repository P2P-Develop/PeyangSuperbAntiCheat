package ml.peya.plugins.Gui.Events;

import ml.peya.plugins.*;
import ml.peya.plugins.Gui.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.*;

import java.util.*;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.tracker;

/**
 * おもっくそプレイヤーのドロップに関わりそうなイベント。
 */
public class Drop implements Listener
{
    /**
     * ドロップした時に処理する...らしい。
     *
     * @param e ドロップした時に発火するイベント。
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent e)
    {
        if (Item.canGuiItem(e.getItemDrop().getItemStack()))
            return;
        e.setCancelled(true);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Arrays.stream(e.getPlayer().getInventory().getContents()).parallel().filter(stack -> !Item.canGuiItem(stack)).forEachOrdered(stack -> stack.setAmount(0));
                this.cancel();
            }
        }.runTask(PeyangSuperbAntiCheat.getPlugin());
        tracker.remove(e.getPlayer().getName());
        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(get("item.tracking.noTarget")));
        e.getPlayer().sendMessage(get("item.stopTarget"));
    }
}
