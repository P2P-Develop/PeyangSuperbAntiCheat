package ml.peya.plugins.Moderate;

import ml.peya.plugins.*;
import org.bukkit.entity.*;

import java.util.*;

public class Mods
{
    public static HashMap<String, String> getMods(Player player)
    {
        UUID uuid = player.getUniqueId();

        if (!PeyangSuperbAntiCheat.mods.containsKey(uuid))
            return null;

        return PeyangSuperbAntiCheat.mods.get(uuid);
    }
}
