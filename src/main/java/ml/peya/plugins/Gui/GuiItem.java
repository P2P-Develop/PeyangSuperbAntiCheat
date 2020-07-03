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

        for (IItems items: item.getItems())
        {
            PlayerInventory inventory = player.getInventory();

            ItemStack stack = inventory.getItem(i);

            if (stack != null && stack.getType() != Material.AIR)
            {
                if (!Item.canGuiItem(stack))
                    player.getWorld().dropItem(player.getLocation().add(0, 1, 0), stack);
                inventory.remove(stack);
            }


            if (items.getType() == type || items.getType() == IItems.Type.ALL)
                inventory.setItem(i, items.getItem(target));

            if (items.canSpace())
                i++;

            i++;
        }
    }
}
