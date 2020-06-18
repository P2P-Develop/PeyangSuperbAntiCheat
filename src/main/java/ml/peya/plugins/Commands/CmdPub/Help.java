package ml.peya.plugins.Commands.CmdPub;


import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

public class Help
{
    public static void run(CommandSender sender, String label)
    {
        boolean flag = false;
        sender.sendMessage(ChatColor.AQUA + "-----" +
                ChatColor.GREEN + "[" +
                ChatColor.BLUE + "PeyangSuperbAntiCheat" +
                ChatColor.GREEN + "]" +
                ChatColor.AQUA + "-----");

        if (sender.hasPermission("psr.report"))
        {
            sender.sendMessage(ChatColor.AQUA + "/report(wdr) " + ChatColor.DARK_AQUA + "<PlayerName>");
            sender.sendMessage(ChatColor.GREEN + "プレイヤーをレポートします。");
            flag = true;
        }

        if (sender.hasPermission("psr.viewreport"))
        {
            sender.sendMessage(ChatColor.AQUA + "/" + label + " view " + ChatColor.DARK_PURPLE + "[ページ数 | プレイヤ名] [ページ数]");
            sender.sendMessage(ChatColor.GREEN + "レポートされた対処されてない問題を、");
            sender.sendMessage(ChatColor.GREEN + "重大度順に表示します");
            flag = true;
        }

        if (sender.hasPermission("psr.automessage"))
        {
            sender.sendMessage(ChatColor.AQUA + "/automessage" + ChatColor.DARK_PURPLE + " <項目名> [設置値]");
            sender.sendMessage(ChatColor.GREEN + "定期メッセージの設定をします。");
        }

        if (sender.hasPermission("psr.aurapanic"))
        {
            sender.sendMessage(ChatColor.AQUA + "/acpanic " + ChatColor.DARK_AQUA + "<PlayerName>" + ChatColor.DARK_PURPLE + " [Seconds]");
            sender.sendMessage(ChatColor.GREEN + "指定したプレイヤーの背後に指定した秒数、");
            sender.sendMessage(ChatColor.GREEN + "NPCを召喚します。");
            flag = true;
        }

        if (sender.hasPermission("psr.aurabot"))
        {
            sender.sendMessage(ChatColor.AQUA + "/aurabot " + ChatColor.DARK_AQUA + "<PlayerName>");
            sender.sendMessage(ChatColor.GREEN + "指定したプレイヤーを中心に");
            sender.sendMessage(ChatColor.GREEN + "回るNPCを召喚します。");
            flag = true;
        }

        if (sender.hasPermission("psr.showreport"))
        {
            sender.sendMessage(ChatColor.AQUA + "/" + label + " show " + ChatColor.DARK_AQUA + "<管理iD>");
            sender.sendMessage(ChatColor.GREEN + "レポートを表示します。");
            flag = true;
        }

        if (sender.hasPermission("psr.drop"))
        {
            sender.sendMessage(ChatColor.AQUA + "/" + label + " drop " + ChatColor.DARK_AQUA + "<管理iD>");
            sender.sendMessage(ChatColor.GREEN + "レポートを削除します。");
        }

        if (!(sender instanceof Player))
            return;

        if (sender.hasPermission("psr.drop") || sender.hasPermission("psr.showreport"))
        {
            sender.sendMessage(ChatColor.YELLOW + "注意: 管理IDはランダムでつけられるものです。\n");
            sender.sendMessage(ChatColor.YELLOW + "プレイヤーからも操作できますが、コンソールから操作してください。");
        }


        if (!flag)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：表示できるものが何もありません！");
        }
    }
}
