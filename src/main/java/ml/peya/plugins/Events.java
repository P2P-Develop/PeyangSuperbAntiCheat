package ml.peya.plugins;

import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.metadata.*;


public class Events implements Listener
{
    @EventHandler
    public void onKill(PlayerDeathEvent e)
    {
        if (e.getEntity().getKiller() == null)
            return;
        PeyangSuperbAntiCheat.counting.kill(e.getEntity().getKiller().getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent e)
    {
        if (!(e.getEntity() instanceof CraftPlayer))
            return;
        if (!(e.getDamager() instanceof  CraftArrow))
            return;

        if (!PeyangSuperbAntiCheat.cheatMeta.exists(e.getEntity().getUniqueId()))
            return;

        if (!e.getDamager().hasMetadata("testArrow-" + e.getDamager().getUniqueId()))
            return;

        e.setDamage(0);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();

        PeyangSuperbAntiCheat.tracker.remove(player.getName());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        if (!PeyangSuperbAntiCheat.tracker.isTrackingByPlayer(e.getPlayer().getName()))
            return;
        Location from = e.getFrom();
        Location to = e.getTo();
        double distance = from.distance(to);
        Player player = e.getPlayer();
        player.setMetadata("speed", new FixedMetadataValue(PeyangSuperbAntiCheat.getPlugin(), distance));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e)
    {
        e.setCancelled(true);
        String format = e.getFormat().replace("%1$s", e.getPlayer().getDisplayName()).replace("%2$s", e.getMessage());

        ComponentBuilder builder = new ComponentBuilder("");

        builder.append(ChatColor.RED +
                "[" + ChatColor.YELLOW + ChatColor.BOLD +
                "➤" + ChatColor.RESET + ChatColor.RED +
                "] ");
        builder.append(format);
        builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/target " + e.getPlayer().getName()));
        builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ChatColor.RED + "Target " + e.getPlayer().getName()).create()));

        for (Player receiver: e.getRecipients())
        {
            if (!receiver.hasPermission("psac.chattarget"))
                receiver.sendMessage(format);
            else
                receiver.spigot().sendMessage(builder.create());
        }
    }
}
