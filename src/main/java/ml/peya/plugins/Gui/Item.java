package ml.peya.plugins.Gui;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

public class Item
{
    private final ArrayList<IItems> items;

    public Item()
    {
        this.items = new ArrayList<>();
    }

    public void register(IItems item)
    {
        for (IItems items: this.items)
        {
            if (items.getExecName().equals(item.getExecName()))
                return;
        }

        items.add(item);
    }

    public void unRegister(IItems item)
    {
        this.items.removeIf(iItems -> iItems.getExecName().equals(item.getExecName()));
    }

    public ArrayList<IItems> getItems()
    {
        return items;
    }


    public static boolean canGuiItem(ItemStack item)
    {
        if (item == null || item.getType() == Material.AIR || !item.hasItemMeta())
            return false;

        ItemMeta meta = item.getItemMeta();

        if (!meta.hasLore())
            return false;

        if (meta.getLore().size() <= 1)
            return false;

        return meta.getLore().get(0).equals(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Lynx item.");
    }

    public static String getType(ItemStack item)
    {
        if (!canGuiItem(item))
            return null;
        return item.getItemMeta().getLore().get(1).replace(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Execution type: ", "");
    }

    public static String getTarget(ItemStack item)
    {
        if (!canGuiItem(item))
            return null;
        return item.getItemMeta().getLore().get(2).replace(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Target: ", "");

    }


    public static ArrayList<String> getLore(IItems item, String target)
    {
        ArrayList<String> list = new ArrayList<>();

        list.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Lynx item.");
        list.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Execution type: " + item.getExecName());

        if (item.getType() == IItems.Type.TARGET || item.getType() == IItems.Type.TARGET_2)
            list.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Target: " + target);

        list.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Type: " + item.getType());

        return list;
    }

}
