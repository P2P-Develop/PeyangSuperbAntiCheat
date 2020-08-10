package ml.peya.plugins.Gui.Items.Target;

import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
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
        ItemStack stack = new ItemStack(Material.SLIME_BALL);

        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(MessageEngine.get("item.execute", MessageEngine.pair("command", "TestKnockBack")));

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
