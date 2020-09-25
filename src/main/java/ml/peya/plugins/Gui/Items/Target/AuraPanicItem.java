package ml.peya.plugins.Gui.Items.Target;

import ml.peya.plugins.Gui.IItems;
import ml.peya.plugins.Gui.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;

/**
 * 背後に張り付くBOT
 */
public class AuraPanicItem implements IItems
{
    @Override
    public void run(Player player, String target)
    {
        player.performCommand("acpanic " + target);
    }

    @Override
    public ItemStack getItem(String target)
    {
        ItemStack stack = new AuraBotItem().getItem(target);

        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(get("item.execute", pair("command", "AuraPanic")));
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
        return "AURA_PANIC";
    }

    @Override
    public Type getType()
    {
        return Type.TARGET;
    }
}
