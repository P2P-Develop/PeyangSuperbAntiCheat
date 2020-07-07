package ml.peya.plugins.Gui.Events;

import ml.peya.plugins.Gui.*;
import ml.peya.plugins.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.*;

public class Drop implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent e)
    {
        ItemStack item = e.getItemDrop().getItemStack();

        if (!Item.canGuiItem(item))
            return;
        e.setCancelled(true);


        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for (ItemStack stack: e.getPlayer().getInventory().getContents())
                {
                    if (!Item.canGuiItem(stack))
                        continue;
                    stack.setAmount(0);
                }
                this.cancel();
            }
        }.runTask(PeyangSuperbAntiCheat.getPlugin());
        PeyangSuperbAntiCheat.tracker.remove(e.getPlayer().getName());
        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageEngine.get("item.tracking.noTarget")));
        e.getPlayer().sendMessage(MessageEngine.get("item.stopTarget"));
    }
}
