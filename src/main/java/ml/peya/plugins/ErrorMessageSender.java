package ml.peya.plugins;

import org.bukkit.*;
import org.bukkit.command.*;

public class ErrorMessageSender
{
    public static boolean unPermMessage(CommandSender sender, String perm)
    {
        if (sender.hasPermission(perm))
            return false;
        sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：権限がありません！");
        return true;
    }

    public static boolean invalidLengthMessage(CommandSender sender,  String[] args, int min, int max)
    {
        if (args.length < min || args.length > max)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：引数の数が不正です。/psr help でヘルプを見てください。");
            return true;
        }

        return false;
    }
}
