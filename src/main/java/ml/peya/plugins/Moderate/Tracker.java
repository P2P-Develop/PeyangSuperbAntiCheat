package ml.peya.plugins.Moderate;

import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.metadata.*;
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
                if (target.get(player) != null && Bukkit.getPlayer(player) == null)
                    Bukkit.getPlayer(player).setCompassTarget(target.get(player));
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20L);


        this.target.remove(player);
        this.tracker.remove(player);
    }

    public boolean isTracking(String player)
    {
        return tracker.containsKey(player);
    }

    public boolean isTrackingByPlayer(String player)
    {
        return tracker.containsValue(player);
    }

    public void tick()
    {
        tracker.keySet().forEach(playerName -> {
            Player player = Bukkit.getPlayer(playerName);
            Player target = Bukkit.getPlayer(tracker.get(playerName));
            if (player == null)
            {
                tracker.remove(playerName);
                return;
            }
            if (target == null)
            {
                tracker.remove(playerName);
                return;
            }
            Location location = target.getLocation();
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", target.getName());
            map.put("world", location.getWorld().getName());
            map.put("x", scaleSet(location.getX(), 2));
            map.put("y", scaleSet(location.getY(), 2));
            map.put("z", scaleSet(location.getZ(), 2));
            map.put("distance", scaleSet(location.distance(player.getLocation()), 1));
            if (PeyangSuperbAntiCheat.cheatMeta.exists(target.getUniqueId()))
            {
                String test = String.valueOf(PeyangSuperbAntiCheat.cheatMeta.getMetaByPlayerUUID(target.getUniqueId()).getType().getName());
                HashMap<String, Object> repKey = new HashMap<>();
                repKey.put("type", test);
                if (PeyangSuperbAntiCheat.cheatMeta.getMetaByPlayerUUID(target.getUniqueId()).getType() == DetectType.ANTI_KB)
                    repKey.put("vl", "N/A");
                else
                    repKey.put("vl", PeyangSuperbAntiCheat.cheatMeta.getMetaByPlayerUUID(target.getUniqueId()).getVL());
                map.put("tests", MessageEngine.get("item.tracking.testing", repKey));
            }
            else
                map.put("tests", "");
            if (target.hasMetadata("speed"))
                target.getMetadata("speed").stream().filter(value -> value.getOwningPlugin().getName().equals(PeyangSuperbAntiCheat.getPlugin().getName())).forEachOrdered(value -> map.put("velocity", scaleSet((Double) value.value(), 2)));
            else
                map.put("velocity", 0.0);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageEngine.get("item.tracking.text", map)));
            Arrays.stream(player.getInventory().getContents()).filter(itemStack -> !Item.canGuiItem(itemStack)).filter(itemStack -> Objects.equals(Item.getType(itemStack), "TRACKER")).map(itemStack -> location).forEachOrdered(player::setCompassTarget);
            target.removeMetadata("speed", PeyangSuperbAntiCheat.getPlugin());
        });
    }

    private String scaleSet(double d, int scale)
    {
        BigDecimal bigDecimal = BigDecimal.valueOf(d);

        bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);
        return bigDecimal.toPlainString();
    }
}
