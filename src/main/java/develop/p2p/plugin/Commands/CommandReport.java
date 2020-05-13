package develop.p2p.plugin.Commands;

import develop.p2p.plugin.Enum.*;
import develop.p2p.plugin.Gui.*;
import develop.p2p.plugin.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class CommandReport implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "エラー：変数が不足しています。/" + label + "help でヘルプを見てください。");
            return true;
        }
        else if (args.length == 1)
        {
            if (args[0].equals("help"))
            {
                sender.sendMessage(ChatColor.AQUA + "-----" +
                        ChatColor.GREEN + "[" +
                        ChatColor.BLUE + "PeyangSuperbAntiCheat" +
                        ChatColor.GREEN + "]" +
                        ChatColor.AQUA);
                sender.sendMessage(ChatColor.AQUA + "    /" + label + " <PlayerName> [reason...]");
                sender.sendMessage(ChatColor.GREEN + "    プレイヤーを報告します。");
                sender.sendMessage(ChatColor.AQUA + "    /" + label + " help");
                sender.sendMessage(ChatColor.GREEN + "    このコマンドです。");
                return true;
            }
            Player target= Bukkit.getPlayer(args[0]);
            if (target == null)
            {
                sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "エラー：報告対象プレイヤーが見つかりませんでした。");
                return true;
            }
            else if(sender instanceof ConsoleCommandSender)
            {
                sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "エラー：理由短縮モードは、プレイヤーからのみ実行することができます。");
                return true;
            }

            ItemStack book = ReportBook.getBook(target, CheatTypeUtils.getFullType().toArray(EnumCheatType.values()));
            Book.openBook(book, target);
        }

        Player target= Bukkit.getPlayer(args[0]);


        return false;
    }
}
