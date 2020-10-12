package ml.peya.plugins.Gui.Events;

import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.tracker;

/**
 * ドロップのやつ
 */
public class Drop implements Listener
{
    /**
     * PSAC系アイテムのGUI管理系
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
                Arrays.stream(e.getPlayer()
                    .getInventory()
                    .getContents())
                    .parallel()
                    .filter(stack -> !Item.canGuiItem(stack))
                    .forEachOrdered(stack -> stack.setAmount(0));
                this.cancel();
            }
        }.runTask(PeyangSuperbAntiCheat.getPlugin());
        tracker.remove(e.getPlayer().getName());
        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(get("item.tracking.noTarget")));
        e.getPlayer().sendMessage(get("item.stopTarget"));
    }
}
