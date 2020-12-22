package ml.peya.plugins.Module.Load;

import ml.peya.plugins.Module.*;
import ml.peya.plugins.*;

/**
 * BanManagerの接続
 */
public class Banmanager
{
    /**
     * BanManager
     */
    public static void ban()
    {
        InitModule.addModule("BanManager");
        ModuleContainer.ban = PeyangGreatBanManager.getAPI();
    }
}
