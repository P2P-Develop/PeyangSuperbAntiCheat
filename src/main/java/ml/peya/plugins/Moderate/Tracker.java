package ml.peya.plugins.Moderate;

import ml.peya.plugins.Enum.DetectType;
import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.cheatMeta;

/**
 * トラッキングに使うクラス。いろいろコントロールしてくれる。
 */
public class Tracker
{
    /**
     * トラッカーの中枢...?
     */
    private final HashMap<String, String> tracker = new HashMap<>();
    /**
     * お前とお前の位置。
     */
    private final HashMap<String, Location> target = new HashMap<>();

    /**
     * 指定したプレイヤーをトラッキングするやつ。
     *
     * @param player プレイヤー。
     * @param target なんこれ
     */
    public void add(String player, String target)
    {
        this.tracker.put(player, target);
        Player p = Bukkit.getPlayer(player);
        if (p == null)
            return;
        this.target.put(player, p.getCompassTarget());
    }

    /**
     * 指定したプレイヤーをトラッキング解除するやつ。
     *
     * @param player プレイヤー。
     */
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

    /**
     * トラッキングしてるかどうか調べるやつ。
     *
     * @param player プレイヤー。
     * @return トラッキングしてたらtrue。
     */
    public boolean isTracking(String player)
    {
        return tracker.containsKey(player);
    }

    /**
     * なんか別の方法でトラッキングしてるかどうか調べるやつ。
     *
     * @param player プレイヤー。
     * @return トラッキングしてたらtrue。
     */
    public boolean isTrackingByPlayer(String player)
    {
        return tracker.containsValue(player);
    }

    /**
     * トラックの中枢。
     * デフォルトでは10チック(0.5秒)に1回実行される。
     */
    public void tick()
    {
        tracker.keySet().parallelStream().forEachOrdered(playerName ->
        {
            Player player = Bukkit.getPlayer(playerName);
            Player target = Bukkit.getPlayer(tracker.get(playerName));
            if (player == null || target == null)
            {
                tracker.remove(playerName);
                return;
            }
            final Location location = target.getLocation();
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", target.getName());
            map.put("world", location.getWorld().getName());
            map.put("x", scaleSet(location.getX(), 2));
            map.put("y", scaleSet(location.getY(), 2));
            map.put("z", scaleSet(location.getZ(), 2));
            map.put("distance", scaleSet(location.distance(player.getLocation()), 1));
            if (cheatMeta.exists(target.getUniqueId()))
            {
                HashMap<String, Object> repKey = new HashMap<>();
                repKey.put("type", String
                    .valueOf(cheatMeta.getMetaByPlayerUUID(target.getUniqueId()).getType().getName()));
                repKey.put("vl", cheatMeta.getMetaByPlayerUUID(target.getUniqueId())
                    .getType() == DetectType.ANTI_KB ? "N/A": Integer
                    .valueOf(cheatMeta.getMetaByPlayerUUID(target.getUniqueId()).getVL()));
                map.put("tests", get("item.tracking.testing", repKey));
            }
            else
                map.put("tests", "");
            if (target.hasMetadata("speed"))
                target.getMetadata("speed").parallelStream().filter(value -> value.getOwningPlugin().getName()
                    .equals(PeyangSuperbAntiCheat
                        .getPlugin().getName()))
                    .forEachOrdered(value -> map.put("velocity", scaleSet((Double) value.value(), 2)));
            else
                map.put("velocity", 0.0);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(get("item.tracking.text", map)));
            Arrays.stream(player.getInventory().getContents()).parallel()
                .filter(itemStack -> !Item.canGuiItem(itemStack))
                .filter(itemStack -> Objects.equals(Item.getType(itemStack), "TRACKER")).map(itemStack -> location)
                .forEachOrdered(player::setCompassTarget);
            target.removeMetadata("speed", PeyangSuperbAntiCheat.getPlugin());
        });
    }

    /**
     * スケールちょうせい......?
     *
     * @param d     だぶる！
     * @param scale すけーる！
     * @return なんか！
     */
    private String scaleSet(double d, int scale)
    {
        return BigDecimal.valueOf(d).setScale(scale, RoundingMode.HALF_UP).toPlainString();
    }
}
