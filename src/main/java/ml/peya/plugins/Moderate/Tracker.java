package ml.peya.plugins.Moderate;

import ml.peya.plugins.*;
import ml.peya.plugins.Gui.Item;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.*;

import java.math.*;
import java.util.*;

public class Tracker
{
    private final HashMap<String, String> tracker = new HashMap<>();
    private final HashMap<String, Location> target = new HashMap<>();

    public void add(String player, String target)
    {
        this.tracker.put(player, target);
        Player p = Bukkit.getPlayer(player);
        if (p == null)
            return;
        this.target.put(player, p.getCompassTarget());
    }

    public void remove(String player)
    {
        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                if (target.get(player) != null)
                    if (Bukkit.getPlayer(player) == null)
                        Bukkit.getPlayer(player).setCompassTarget(target.get(player));
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 11L);


        this.target.remove(player);
        this.tracker.remove(player);
    }

    public boolean isTracking(String player)
    {
        return tracker.containsKey(player);
    }

    public void tick()
    {
        for (String playerName: tracker.keySet())
        {
            Player player = Bukkit.getPlayer(playerName);
            Player target = Bukkit.getPlayer(tracker.get(playerName));

            if (player == null)
            {
                tracker.remove(playerName);
                continue;
            }

            if (target == null)
            {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageEngihe.get("item.tracking.noTarget")));
                continue;
            }

            Location location = target.getLocation();

            HashMap<String, Object> map = new HashMap<>();
            map.put("name", target.getName());
            map.put("world", location.getWorld().getName());

            map.put("x", scaleSet(location.getX(), 2));
            map.put("y", scaleSet(location.getY(), 2));
            map.put("z", scaleSet(location.getZ(), 2));

            map.put("distance", scaleSet(location.distance(player.getLocation()), 1));

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageEngihe.get("item.tracking.text", map)));

            for (ItemStack itemStack: player.getInventory().getContents())
            {
                if (!Item.canGuiItem(itemStack))
                    continue;
                if (!Objects.equals(Item.getType(itemStack), "TRACKER"))
                    continue;
                player.setCompassTarget(location);
            }
        }
    }

    private String scaleSet(double d, int scale)
    {
        BigDecimal bigDecimal = BigDecimal.valueOf(d);

        bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);
        return bigDecimal.toPlainString();
    }
}
