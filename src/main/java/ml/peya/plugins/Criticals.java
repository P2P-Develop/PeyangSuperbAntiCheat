package ml.peya.plugins;

import org.bukkit.entity.*;
import org.bukkit.potion.*;

public class Criticals
{
    public static boolean hasCritical(Player player)
    {
        return !(player.getFallDistance() <= 0.0F) && !player.getLocation().getBlock().isLiquid() && !player.isOnGround() && !player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.getVehicle() == null;
    }

}
