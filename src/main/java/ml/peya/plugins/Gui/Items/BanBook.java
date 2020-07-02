package ml.peya.plugins.Gui.Items;

import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.Utils.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Stack;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

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
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(MessageEngihe.get("item.banBook.title", MessageEngihe.hsh("name", target)));
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
}
