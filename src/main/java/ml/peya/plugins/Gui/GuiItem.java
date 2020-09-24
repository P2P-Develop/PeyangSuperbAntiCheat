package ml.peya.plugins.Gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static ml.peya.plugins.Variables.item;

/**
 * GUI用のアイテム
 */
public class GuiItem
{
    /**
     * trackした時のいろいろアイテムをくれるやつ。
     *
     * @param player 対象プレイヤー。
     * @param type   Type!
     * @param target ターゲット。
     */
    public static void giveAllItems(Player player, IItems.Type type, String target)
    {
        int i = 0;

        Arrays.stream(player.getInventory().getContents()).parallel().filter(stack -> stack != null && stack.getType() != Material.AIR).forEachOrdered(stack -> {
            if (Item.canGuiItem(stack))
                player.getWorld().dropItem(player.getEyeLocation(), stack);
            player.getInventory().remove(stack);
        });

        for (IItems items : item.getItems())
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
