package ml.peya.plugins;

import org.bukkit.entity.*;
import org.bukkit.potion.*;

/**
 * ...これ何のために使うん？
 */
public class Criticals
{
    /** めっちゃクリティカルされたよぉふえええええぇぇぇっていうの確認するやつ
     * @param player クリティカルゥ！プレイヤー。
     *
     * @return クリティカル警察が反応したらtrueを返してくれます。
     */
    public static boolean hasCritical(Player player)
    {
        return !(player.getFallDistance() <= 0.0F) && !player.getLocation().getBlock().isLiquid() && !player.isOnGround() && !player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.getVehicle() == null;
    }

}
