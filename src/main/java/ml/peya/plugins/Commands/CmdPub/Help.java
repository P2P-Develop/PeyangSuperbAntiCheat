package ml.peya.plugins.Commands.CmdPub;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

public class Help
{
    public static void run(CommandSender sender, String label)
    {
        sender.sendMessage(ChatColor.AQUA + "-----" +
                ChatColor.GREEN + "[" +
                ChatColor.BLUE + "PeyangSuperbAntiCheat" +
                ChatColor.GREEN + "]" +
                ChatColor.AQUA + "-----");
        if (sender.hasPermission("psr.mod") || sender.hasPermission("psr.admin"))
        {
            sender.sendMessage(ChatColor.AQUA + "/" + label + " view " + ChatColor.DARK_PURPLE + "[ページ数 | プレイヤ名] [ページ数]");
            sender.sendMessage(ChatColor.GREEN + "レポートされた対処されてない問題を、");
            sender.sendMessage(ChatColor.GREEN + "重大度順に表示します");

            sender.sendMessage(ChatColor.AQUA + "/acpanic " + ChatColor.DARK_AQUA + "<PlayerName>" + ChatColor.DARK_PURPLE + " [Seconds]");
            sender.sendMessage(ChatColor.GREEN + "指定したプレイヤーの背後に指定した秒数、");
            sender.sendMessage(ChatColor.GREEN + "NPCを召喚します。");

            sender.sendMessage(ChatColor.AQUA + "/aurabot " + ChatColor.DARK_AQUA + "<PlayerName>");
            sender.sendMessage(ChatColor.GREEN + "指定したプレイヤーを中心に");
            sender.sendMessage(ChatColor.GREEN + "回るNPCを召喚します。");
        }

        if (sender.hasPermission("psr.admin"))
        {
            sender.sendMessage(ChatColor.AQUA + "/" + label + " show " + ChatColor.DARK_AQUA + "<管理iD>");
            sender.sendMessage(ChatColor.GREEN + "レポートを表示します。");
            sender.sendMessage(ChatColor.AQUA + "/" + label + " drop " + ChatColor.DARK_AQUA + "<管理iD>");
            sender.sendMessage(ChatColor.GREEN + "レポートを削除します。");
            if (!(sender instanceof Player))
                return;
            sender.sendMessage(ChatColor.YELLOW + "注意: 管理IDはランダムでつけられるものです。\n");
            sender.sendMessage(ChatColor.YELLOW + "プレイヤーからも操作できますが、コンソールから操作してください。");
            return;
        }

        sender.sendMessage(ChatColor.AQUA + "/report(wdr) " + ChatColor.DARK_AQUA + "<PlayerName>");
        sender.sendMessage(ChatColor.GREEN + "プレイヤーをレポートします。");
    }
}
