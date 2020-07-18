package ml.peya.plugins.Gui.Items.Main;

import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

public class TargetStick implements IItems
{
    private static Player getLookingEntity(Player player)
    {
        ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(3.5, 3.5, 3.5);
        ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight(null, 4);

        ArrayList<Location> sight = new ArrayList<>();

        for (Block block : sightBlock)
            sight.add(block.getLocation());

        for (Location location : sight)
        {
            for (Entity entity : entities)
            {
                if (isLooking(entity, location) && entity.getType() == EntityType.PLAYER)
                    return (Player) entity;
            }
        }

        return null;
    }

    private static boolean isLooking(Entity entity, Location location)
    {
        if (Math.abs(entity.getLocation().getX() - location.getX()) < 1.3)
        {
            if (Math.abs(entity.getLocation().getY() - location.getY()) < 1.5)
                return Math.abs(entity.getLocation().getZ() - location.getZ()) < 1.3;
        }

        return false;
    }

    @Override
    public void run(Player player, String target)
    {
        Player lookingPlayer = getLookingEntity(player);
        if (lookingPlayer == null)
        {
            player.sendMessage(MessageEngine.get("error.notPlayerFoundInRange"));
            return;
        }
        player.performCommand("target " + lookingPlayer.getName());
    }

    @Override
    public ItemStack getItem(String target)
    {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);

        ItemMeta meta = stack.getItemMeta();

        meta.setLore(Item.getLore(this, target));

        meta.setDisplayName(MessageEngine.get("item.targetStick"));

        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public boolean canSpace()
    {
        return false;
    }

    @Override
    public String getExecName()
    {
        return "TARGET_STICK";
    }

    @Override
    public Type getType()
    {
        return Type.MAIN;
    }

}
