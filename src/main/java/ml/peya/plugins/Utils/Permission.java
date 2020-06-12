package ml.peya.plugins.Utils;

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
}
