package ml.peya.plugins.Module.Load;

import me.rerere.matrix.api.*;
import ml.peya.plugins.Module.Events;
import ml.peya.plugins.Module.*;
import ml.peya.plugins.*;
import org.bukkit.*;

public class Matrix
{
    public static void matrix()
    {
        if (PeyangSuperbAntiCheat.isEnablePlugin("Matrix"))
        {
            InitModule.addModule("Matrix");
            ModuleContainer.matrix = MatrixAPIProvider.getAPI();
            Bukkit.getServer().getPluginManager().registerEvents(new Events(), PeyangSuperbAntiCheat.getPlugin());
        }
    }
}
