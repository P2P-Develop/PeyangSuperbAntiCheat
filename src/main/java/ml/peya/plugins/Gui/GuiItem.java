package ml.peya.plugins.Gui;

import ml.peya.plugins.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.*;

public class GuiItem
{
    public static void giveAllItems(Player player, IItems.Type type, String target)
    {
        int i = 0;

        Arrays.stream(player.getInventory().getContents()).parallel().filter(stack -> stack != null && stack.getType() != Material.AIR).forEachOrdered(stack -> {
            if (Item.canGuiItem(stack))
                player.getWorld().dropItem(player.getEyeLocation(), stack);
            player.getInventory().remove(stack);
        });

        for (IItems items : PeyangSuperbAntiCheat.item.getItems())
        {
            if (items.getType() != type && type != IItems.Type.ALL)
                continue;

            player.getInventory().setItem(i, items.getItem(target));
            i++;
            if (items.canSpace())
                i++;
        }
    }
}
