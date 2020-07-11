package ml.peya.plugins.Gui.Items.Target;

import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class BanBook implements IItems
{
    @Override
    public void run(Player player, String target)
    {
        player.performCommand("bans -a " + target);
    }

    @Override
    public ItemStack getItem(String target)
    {
        ItemStack stack = new ItemStack(Material.PAPER);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(MessageEngine.get("item.banBook.title", MessageEngine.hsh("name", target)));
        meta.setLore(Item.getLore(this, target));
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public boolean canSpace()
    {
        return true;
    }

    @Override
    public String getExecName()
    {
        return "BANS";
    }

    @Override
    public Type getType()
    {
        return Type.TARGET;
    }
}
