package ml.peya.plugins.Gui.Items.Main;

import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import static ml.peya.plugins.Utils.LookingUtils.getLookingEntity;

/**
 * ターゲット棒
 */
public class TargetStick implements IItems
{
    @Override
    public void run(Player player, String target)
    {
        Player lookingPlayer = getLookingEntity(player);
        if (lookingPlayer == null)
        {
            player.sendMessage(MessageEngine.get("error.notPlayerFoundInRange"));
            return;
        }
        player.performCommand("target " + lookingPlayer.getName());
    }

    @Override
    public ItemStack getItem(String target)
    {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);

        ItemMeta meta = stack.getItemMeta();

        meta.setLore(Item.getLore(this, target));

        meta.setDisplayName(MessageEngine.get("item.targetStick"));

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
        return Type.MAIN;
    }

}
