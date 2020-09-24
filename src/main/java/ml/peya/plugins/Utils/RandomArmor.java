package ml.peya.plugins.Utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 防具をランダムにNPCに着せるクラス。
 * 革装備はランダムな色を付与
 */
public class RandomArmor
{
    /**
     * ランダムなヘルメットを入手する。
     *
     * @return アイテムどもから選んだやつ。
     */
    public static ItemStack getHelmet()
    {
        return getRandomItems(new ArrayList<>(Arrays
                .asList(Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET, Material.GOLD_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.LEATHER_HELMET, Material.AIR)));
    }

    /**
     * ランダムなチェストプレートを入手する。
     *
     * @return アイテムどもから選んだやつ。
     */
    public static ItemStack getChestPlate()
    {
        return getRandomItems(new ArrayList<>(Arrays
                .asList(Material.CHAINMAIL_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.LEATHER_CHESTPLATE, Material.AIR)));
    }

    /**
     * ランダムなレギンスを入手する。
     *
     * @return アイテムどもから選んだやつ。
     */
    public static ItemStack getLeggings()
    {
        return getRandomItems(new ArrayList<>(Arrays
                .asList(Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.AIR)));
    }

    /**
     * ランダムなブーツを入手する。
     *
     * @return アイテムどもから選んだやつ。
     */
    public static ItemStack getBoots()
    {
        return getRandomItems(new ArrayList<>(Arrays
                .asList(Material.CHAINMAIL_BOOTS, Material.DIAMOND_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.LEATHER_BOOTS, Material.AIR)));
    }

    /**
     * ランダムな剣を入手する。
     *
     * @return アイテムどもから選んだやつ。
     */
    public static ItemStack getSwords()
    {
        return getRandomItems(new ArrayList<>(Arrays
                .asList(Material.DIAMOND_SWORD, Material.STONE_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.WOOD_SWORD, Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.WOOD_AXE, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR)));
    }

    /**
     * 金アイテムかどうか
     *
     * @param item あいてむ！
     * @return ゴールド全面でござったらtrue。
     */
    private static boolean isGold(Material item)
    {
        return new ArrayList<>(Arrays
                .asList(Material.GOLD_BOOTS, Material.GOLD_CHESTPLATE, Material.GOLD_HELMET, Material.GOLD_LEGGINGS))
                .contains(item);
    }

    /**
     * ランダムにえりすぐる。
     *
     * @param itemsArg あいてむ！なArrayList。
     * @return えりすぐったアイテム。
     */
    public static ItemStack getRandomItems(ArrayList<Material> itemsArg)
    {
        Random random = new Random();

        ArrayList<Material> items = new ArrayList<>();

        itemsArg.parallelStream().forEachOrdered(item ->
        {
            if (isGold(item))
                items.add(item);
            items.add(item);
        });

        ItemStack stack = new ItemStack(items.get(random.nextInt(items.size() - 1)), 1);

        if (stack.getType() == Material.LEATHER_BOOTS || stack.getType() == Material.LEATHER_CHESTPLATE || stack
                .getType() == Material.LEATHER_HELMET || stack.getType() == Material.LEATHER_LEGGINGS)
        {
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            meta.setColor(Color.fromRGB( //色を調整
                    random.nextInt(254),
                    random.nextInt(254),
                    random.nextInt(254)
            ));
            stack.setItemMeta(meta);
        }

        return stack;
    }
}
