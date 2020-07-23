package ml.peya.plugins.Utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LookingUtils
{
    public static Player getLookingEntity(Player player)
    {
        ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(3.5, 3.5, 3.5);
        ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight(null, 4);

        ArrayList<Location> sight = sightBlock.stream().map(Block::getLocation).collect(Collectors.toCollection(ArrayList::new));

        for (Location location : sight)
            for (Entity entity : entities)
                if (isLooking(entity, location) && entity.getType() == EntityType.PLAYER)
                    return (Player) entity;

        return null;
    }

    public static boolean isLooking(Entity entity, Location location)
    {
        if (Math.abs(entity.getLocation().getX() - location.getX()) < 1.3 && Math.abs(entity.getLocation().getY() - location.getY()) < 1.5)
            return Math.abs(entity.getLocation().getZ() - location.getZ()) < 1.3;

        return false;
    }
}
