package ml.peya.plugins.Utils;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Permission
{
    public static boolean hasStaff(Player player)
    {
        return player.hasPermission("psr.admin") || player.hasPermission("psr.mod");
    }

    public static boolean hasStaff(CommandSender sender)
    {
        return sender.hasPermission("psr.admin") || sender.hasPermission("psr.mod");
    }

    public static boolean unPermMessage(CommandSender sender, String perm)
    {
        if (sender.hasPermission(perm))
            return true;
        sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：権限がありません！");
        return false;
    }

    public static boolean unPermMessage(Player player, String perm)
    {
        if (player.hasPermission(perm))
            return true;
        player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：権限がありません！");
        return false;
    }
}
