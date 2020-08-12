package ml.peya.plugins.Moderate;

import ml.peya.plugins.*;
import org.bukkit.entity.*;

import java.util.*;

/**
 * 何のMod入れてるか取得するやつ
 */
public class Mods
{
    /**
     * 本番。
     *
     * @param player 調べる対象。
     * @return 調べ終わったやつ。
     */
    public static HashMap<String, String> getMods(Player player)
    {
        UUID uuid = player.getUniqueId();

        if (!Variables.mods.containsKey(uuid))
            return null;

        return Variables.mods.get(uuid);
    }
}
