package ml.peya.plugins.Module;

import ml.peya.plugins.Module.Load.*;
import ml.peya.plugins.*;

import static ml.peya.plugins.Variables.logger;
import static ml.peya.plugins.Variables.module;

/**
 * Init時のモジュール
 */
public class InitModule
{
    /**
     * 初期化
     */
    public static void init()
    {
        module = new Modules();

        if (PeyangSuperbAntiCheat.isEnablePlugin("Matrix"))
            Matrix.matrix();
        if (PeyangSuperbAntiCheat.isEnablePlugin("NoCheatPlus"))
            NoCheatPlus.ncp();
        if (PeyangSuperbAntiCheat.isEnablePlugin("PeyangGreatBanManager"))
            Banmanager.ban();
    }

    /**
     * モジュール追加
     *
     * @param m モジュール
     */
    public static void addModule(String m)
    {
        module.enable(m);
        logger.info("<-> Module [" + m + "] has connected!");
    }
}
