package ml.peya.plugins.Commands.CmdPub;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Help
{
    public static void run(CommandSender sender, String label)
    {
        sender.sendMessage(ChatColor.AQUA + "-----" +
                ChatColor.GREEN + "[" +
                ChatColor.BLUE + "PeyangSuperbAntiCheat" +
                ChatColor.GREEN + "]" +
                ChatColor.AQUA + "-----");
        if (sender.hasPermission("psr.admin"))
        {
            sender.sendMessage(ChatColor.AQUA + "/" + label + " view " + ChatColor.DARK_PURPLE + "[ページ数 | プレイヤ名] [ページ数]");
            sender.sendMessage(ChatColor.GREEN + "レポートされた対処されてない問題を、");
            sender.sendMessage(ChatColor.GREEN + "重大度順に表示します");

            sender.sendMessage(ChatColor.AQUA + "/" + label + " test " + ChatColor.DARK_AQUA + "<PlayerName>");
            sender.sendMessage(ChatColor.GREEN + "プレイヤーが Killauraを使用しているかチェックします。");

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
