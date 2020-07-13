package ml.peya.plugins.Gui.Items.Target.Page2;

import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class ModList implements IItems
{
    @Override
    public void run(Player player, String target)
    {
        player.performCommand("mods " + target);
    }

    @Override
    public ItemStack getItem(String target)
    {
        ItemStack stack = new ItemStack(Material.TNT);

        ItemMeta meta = stack.getItemMeta();
        meta.setLore(Item.getLore(this, target));
        meta.setDisplayName(MessageEngine.get("item.mods"));
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
        return "MOD_LIST";
    }

    @Override
    public Type getType()
    {
        return Type.TARGET_2;
    }
}
