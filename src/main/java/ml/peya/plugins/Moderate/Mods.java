package ml.peya.plugins.Moderate;

import org.bukkit.entity.Player;

import java.util.HashMap;

import static ml.peya.plugins.Variables.mods;

/**
 * 何のMod入れてるか取得するやつ
 */
public class Mods
{
    /**
     * 本番。
     *
     * @param player 調べる対象。
     *
     * @return 調べ終わったやつ。
     */
    public static HashMap<String, String> getMods(Player player)
    {
        return mods.getOrDefault(player.getUniqueId(), null);
    }
}
