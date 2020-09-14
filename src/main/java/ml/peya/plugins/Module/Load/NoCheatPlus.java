package ml.peya.plugins.Module.Load;

import fr.neatmonster.nocheatplus.*;
import fr.neatmonster.nocheatplus.checks.*;
import fr.neatmonster.nocheatplus.hooks.*;
import ml.peya.plugins.Module.*;
import ml.peya.plugins.Module.NoCheatPlus.*;

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
