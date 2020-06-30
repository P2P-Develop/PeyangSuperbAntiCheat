package ml.peya.plugins.Gui;

import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public interface IItems
{
    void run(Player player, String target);

    ItemStack getItem(String target);

    boolean canSpace();

    String getExecName();

}
