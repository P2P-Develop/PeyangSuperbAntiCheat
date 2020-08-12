package ml.peya.plugins.Gui.Items.Target;

import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

/**
 * ページ2に行くやつ
 */
public class ToPage2 implements IItems
{
    @Override
    public void run(Player player, String target)
    {
        player.performCommand("target " + target + " 2");
    }

    @Override
    public ItemStack getItem(String target)
    {
        ItemStack stack = new ItemStack(Material.ARROW);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(MessageEngine.get("book.words.next"));
        meta.setLore(Item.getLore(this, target));
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
        return "TO_TARGET_2";
    }

    @Override
    public Type getType()
    {
        return Type.TARGET;
    }
}
