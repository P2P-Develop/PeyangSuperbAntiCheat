package ml.peya.plugins.Gui.Items.Target;

import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class AuraPanic implements IItems
{
    @Override
    public void run(Player player, String target)
    {
        player.performCommand("acpanic " + target);
    }

    @Override
    public ItemStack getItem(String target)
    {
        AuraBot auraBot = new AuraBot();
        ItemStack stack = auraBot.getItem(target);

        ItemMeta meta = stack.getItemMeta();
        meta.setLore(Item.getLore(this, target));
        meta.setDisplayName(MessageEngine.get("item.execute", MessageEngine.pair("command", "AuraPanic")));
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
        return "AURA_PANIC";
    }

    @Override
    public Type getType()
    {
        return Type.TARGET;
    }
}
