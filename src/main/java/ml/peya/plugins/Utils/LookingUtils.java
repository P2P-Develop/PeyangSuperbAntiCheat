package ml.peya.plugins.Utils;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.util.*;

import java.util.*;
import java.util.stream.*;

/**
 * メンチ切ってるのわかっちゃうクラス。
 */
public class LookingUtils
{
    /** 誰が見てるのかわかるやつ。
     * @param player 見られてるプレイヤー。
     *
     * @return 見てるプレイヤー。
     */
    public static Player getLookingEntity(Player player)
    {
        for (Location location : player.getLineOfSight(null, 4).parallelStream().map(Block::getLocation).collect(Collectors.toCollection(ArrayList::new)))
            for (Entity entity : player.getNearbyEntities(3.5, 3.5, 3.5))
                if (isLooking((Player) entity, location) && entity.getType() == EntityType.PLAYER)
                    return (Player) entity;

        return null;
    }

    /** 今見てるかわかるやつ。
     * @param player 見られてるプレイヤー。
     * @param location あと場所。
     *
     * @return 見られてたらtrue。
     */
    public static boolean isLooking(Player player, Location location)
    {
        BlockIterator it = new BlockIterator(player, 4);

        while (it.hasNext())
        {
            Block block = it.next();
            if (block.getX() == location.getBlockX() && block.getY() == location.getBlockY() && block.getZ() == location.getBlockZ())
                return true;
        }
        return false;
    }
}
