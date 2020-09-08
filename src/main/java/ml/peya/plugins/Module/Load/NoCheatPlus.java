package ml.peya.plugins.Module.Load;

import fr.neatmonster.nocheatplus.*;
import fr.neatmonster.nocheatplus.checks.*;
import fr.neatmonster.nocheatplus.hooks.*;
import ml.peya.plugins.Module.*;
import ml.peya.plugins.Module.NoCheatPlus.*;

public class NoCheatPlus
{
    public static void ncp()
    {
        InitModule.addModule("NoCheatPlus");
        ModuleContainer.ncp = NCPAPIProvider.getNoCheatPlusAPI();
        NCPHookManager.addHook(CheckType.FIGHT, new Hook());

    }
}
