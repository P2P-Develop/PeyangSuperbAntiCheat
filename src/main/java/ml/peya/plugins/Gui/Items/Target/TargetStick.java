package ml.peya.plugins.Gui.Items.Target;

import ml.peya.plugins.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Gui.Item;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class TargetStick implements IItems
{
    @Override
    public void run(Player player, String target)
    {
        player.performCommand("target " + target);
    }

    @Override
    public ItemStack getItem(String target)
    {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);

        ItemMeta meta = stack.getItemMeta();

        meta.setLore(Item.getLore(this, target));

        meta.setDisplayName(MessageEngihe.get("item.targetStick"));

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
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
        return null;
    }
}
