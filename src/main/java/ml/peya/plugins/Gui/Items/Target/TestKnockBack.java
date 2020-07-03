package ml.peya.plugins.Gui.Items.Target;

import ml.peya.plugins.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Gui.Item;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

public class TestKnockBack implements IItems
{
    @Override
    public void run(Player player, String target)
    {
        player.performCommand("testkb " + target);
    }

    @Override
    public ItemStack getItem(String target)
    {
        ItemStack stack = new ItemStack(Material.ARROW);

        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(MessageEngihe.get("item.execute", MessageEngihe.hsh("command", "TestKnockBack")));

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
        return "TEST_KB";
    }

    @Override
    public Type getType()
    {
        return Type.TARGET;
    }
}
