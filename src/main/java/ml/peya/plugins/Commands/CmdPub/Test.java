package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Test
{
    public static void run(CommandSender sender, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー: 引数の数が不正です。/psr help でヘルプを見てください。");
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー: 対象プレイヤーが見つかりませんでした！");
            return;
        }

        CheatDetectUtil.scan(player);
    }
}
