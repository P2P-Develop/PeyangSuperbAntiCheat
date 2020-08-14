package ml.peya.plugins.Bukkit.Utils;

import org.bukkit.entity.*;
import org.bukkit.potion.*;

/**
 * Q. これ何のために使うん？
 * A. しらんがな
 */
public class Criticals
{
    /**
     * めっちゃクリティカルされたよぉふえええええぇぇぇっていうの確認するやつ
     *
     * @param player クリティカルゥ！プレイヤー。
     * @return クリティカル警察が反応したらtrueを返してくれます。
     */
    public static boolean hasCritical(Player player)
    {
        return !(player.getFallDistance() <= 0.0F) && !player.getLocation().getBlock().isLiquid() && !player.isOnGround() && !player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.getVehicle() == null;
    }

}
