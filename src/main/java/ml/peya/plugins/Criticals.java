package ml.peya.plugins;

import org.bukkit.entity.*;
import org.bukkit.potion.*;

public class Criticals
{
    public static boolean hasCritical(Player player)
    {
        if (player.getFallDistance() <= 0.0F)
            return false;
        if (player.getLocation().getBlock().isLiquid())
            return false;
        if (player.isOnGround())
            return false;
        if (player.hasPotionEffect(PotionEffectType.BLINDNESS))
            return false;
        return player.getVehicle() == null;
    }

}
