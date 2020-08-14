package ml.peya.plugins.Bukkit.Gui;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

/**
 * アイテム自体をなんとなく管理してくれるクラス。
 */
public class Item
{
    private final ArrayList<IItems> items;

    public Item()
    {
        this.items = new ArrayList<>();
    }

    /**
     * GUIで表示できるアイテムであるか。
     *
     * @param item チェックするアイテム。
     * @return GUI表示可能であればちゃんとしてくれる。
     */
    public static boolean canGuiItem(ItemStack item)
    {
        if (item == null || item.getType() == Material.AIR || !item.hasItemMeta())
            return true;

        ItemMeta meta = item.getItemMeta();

        if (!meta.hasLore() || meta.getLore().size() <= 1) return true;

        return !meta.getLore().get(0).equals(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Lynx item.");
    }

    /**
     * どのようなアイテムであるかStringで取得できる奴。
     *
     * @param item チェックするアイテム。
     * @return 説明付きのString結果。
     */
    public static String getType(ItemStack item)
    {
        if (canGuiItem(item))
            return null;
        return item.getItemMeta().getLore().get(1).replace(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Execution type: ", "");
    }

    /**
     * トラッキングに関わる関数。トラッキングしているプレイヤーを表示する。
     *
     * @param item チェックするアイテム。
     * @return トラッキングしているプレイヤー付きのString。
     */
    public static String getTarget(ItemStack item)
    {
        if (canGuiItem(item))
            return null;
        return item.getItemMeta().getLore().get(2).replace(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Target: ", "");
    }

    /**
     * アイテムの説明を取得する関数。
     *
     * @param item   チェックするアイテム。
     * @param target ターゲット(?)
     * @return チェックした後のArrayList。
     */
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

    /**
     * アイテムを登録する...らしい。
     *
     * @param item 登録するアイテム。
     */
    public void register(IItems item)
    {
        for (IItems items : this.items)
        {
            if (items.getExecName().equals(item.getExecName()))
                return;
        }

        items.add(item);
    }

    /**
     * アイテムの登録を解除する...らしい。
     *
     * @param item 解除するアイテム。
     */
    public void unRegister(IItems item)
    {
        this.items.removeIf(iItems -> iItems.getExecName().equals(item.getExecName()));
    }

    /**
     * アイテムを取得するゲッター。
     *
     * @return アイテムたち。
     */
    public ArrayList<IItems> getItems()
    {
        return items;
    }
}
