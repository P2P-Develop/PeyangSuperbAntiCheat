package ml.peya.plugins.Gui.Items.Target;

import ml.peya.plugins.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Gui.Item;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class Lead implements IItems
{
    @Override
    public void run(Player player, String target)
    {
        player.performCommand("pull " + target);
    }

    @Override
    public ItemStack getItem(String target)
    {
        ItemStack stack = new ItemStack(Material.LEASH);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(MessageEngine.get("item.lead"));
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
        return "PULL";
    }

    @Override
    public Type getType()
    {
        return Type.TARGET_2;
    }
}
