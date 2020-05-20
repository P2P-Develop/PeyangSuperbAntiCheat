package ml.peya.plugins.Utils;

import ml.peya.plugins.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;


public class KickUtil
{
    public static void kickPlayer (Player player)
    {

        String message = ChatColor.RED + "あなたは、このサーバーからKickされました！" +
                "\n" +
                ChatColor.GRAY + "理由: " +
                ChatColor.WHITE + "PYGANTICHEAT DETECTION " + //TODO: logシステムの実装
                "\n" +
                "\n" +
                ChatColor.GRAY + "あなたのKick IDを共有すると、申し立ての処理に影響する可能性があります！";
        player.kickPlayer(message);
    }
}
