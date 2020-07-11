package ml.peya.plugins.Gui;

import ml.peya.plugins.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class GuiItem
{
    public static void giveAllItems(Player player, IItems.Type type, String target)
    {
        Item item = PeyangSuperbAntiCheat.item;

        int i = 0;

        for (ItemStack stack: player.getInventory().getContents())
        {
            if (stack == null || stack.getType() == Material.AIR)
                continue;

            if (!Item.canGuiItem(stack))
                player.getWorld().dropItem(player.getLocation().add(0, 1, 0), stack);

            player.getInventory().remove(stack);
        }


        for (IItems items: item.getItems())
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
