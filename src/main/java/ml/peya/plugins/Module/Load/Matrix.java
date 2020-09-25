package ml.peya.plugins.Module.Load;

import me.rerere.matrix.api.MatrixAPIProvider;
import ml.peya.plugins.Module.Events;
import ml.peya.plugins.Module.InitModule;
import ml.peya.plugins.Module.ModuleContainer;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import org.bukkit.Bukkit;

/**
 * Matrixの接続
 */
public class Matrix
{
    /**
     * Matrix
     */
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
