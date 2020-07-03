package ml.peya.plugins.Gui.Events;

import com.sun.corba.se.spi.ior.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Moderate.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.*;
import sun.nio.cs.*;

import java.util.*;

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
        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageEngihe.get("item.tracking.noTarget")));
        e.getPlayer().sendMessage(MessageEngihe.get("item.stopTarget"));
    }
}
