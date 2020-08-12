package ml.peya.plugins.Gui.Items.Target;

import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Utils.*;
import ml.peya.plugins.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

/**
 * 戻るボタン
 */
public class BackButton implements IItems
{
    @Override
    public void run(Player player, String target)
    {
        Variables.tracker.remove(player.getName());
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageEngine.get("item.tracking.noTarget")));
        player.sendMessage(MessageEngine.get("item.stopTarget"));
        GuiItem.giveAllItems(player, Type.MAIN, target);
    }

    @Override
    public ItemStack getItem(String target)
    {
        ItemStack stack = new ItemStack(Material.WATCH);

        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(MessageEngine.get("book.words.back"));

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
        return "BACK";
    }

    @Override
    public Type getType()
    {
        return Type.TARGET;
    }
}
