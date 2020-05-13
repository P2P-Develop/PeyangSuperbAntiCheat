package ml.peya.plugins.Commands;

import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

import java.util.*;

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

            ItemStack book = ReportBook.getBook(target, (EnumCheatType[]) CheatTypeUtils.getFullType().toArray());
            Book.openBook(book, (Player) sender);
        }

        Player target= Bukkit.getPlayer(args[0]);
        ArrayList<String> reasons = (ArrayList<String>) Arrays.asList(args);
        ArrayList<EnumCheatType> types = CheatTypeUtils.getFullType();
        reasons.remove(0);

        for (String reason: reasons)
        {
            for (EnumCheatType type: types)
            {
                if (reason.contains(type.getSysName()))
                    type.setSelected(true);
            }
        }

        if(reasons.get(reasons.size() - 1).equals("\\"))
        {
            ItemStack book = ReportBook.getBook(target, (EnumCheatType[]) reasons.toArray());
            Book.openBook(book, target);
            return true;
        }

        for (EnumCheatType type: types)
        {

        }
        return false;
    }
}
