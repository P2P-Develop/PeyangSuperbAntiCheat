package ml.peya.plugins.Module.Load;

import fr.neatmonster.nocheatplus.NCPAPIProvider;
import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.hooks.NCPHookManager;
import ml.peya.plugins.Module.InitModule;
import ml.peya.plugins.Module.ModuleContainer;
import ml.peya.plugins.Module.NoCheatPlus.Hook;

/**
 * NoCheatPlusとの接続。
 */
public class NoCheatPlus
{
    /**
     * NCP
     */
    public static void ncp()
    {
        InitModule.addModule("NoCheatPlus");
        ModuleContainer.ncp = NCPAPIProvider.getNoCheatPlusAPI();
        NCPHookManager.addHook(CheckType.FIGHT, new Hook());
    }
}
