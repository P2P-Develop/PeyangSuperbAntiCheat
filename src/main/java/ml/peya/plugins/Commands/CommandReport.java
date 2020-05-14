package ml.peya.plugins.Commands;


import ml.peya.plugins.*;
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
            sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "エラー：変数が不足しています。/" + label + " help でヘルプを見てください。");
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
                sender.sendMessage(ChatColor.AQUA + "/" + label + " <PlayerName> [reason...]");
                sender.sendMessage(ChatColor.GREEN + "プレイヤーを報告します。");
                sender.sendMessage(ChatColor.AQUA + "/" + label + " help");
                sender.sendMessage(ChatColor.GREEN + "このコマンドです。");
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

            ItemStack book = ReportBook.getBook(target,  CheatTypeUtils.getFullType());
            BookUtil.openBook(book, (Player) sender);
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        ArrayList<String> reasons = new ArrayList<>(Arrays.asList(args));
        ArrayList<EnumCheatType> types = CheatTypeUtils.getFullTypeArrayList();

        reasons.remove(0);

        for (String reason: reasons)
        {
            for (EnumCheatType type: types)
            {
                if (reason.contains(type.getSysName()))
                    type.setSelected(true);
            }
        }

        if(reasons.size() != 0 &&  reasons.get(reasons.size() - 1).equals("\\"))
        {
            ItemStack book = ReportBook.getBook(target, types.toArray(new EnumCheatType[0]));
            BookUtil.openBook(book, target);
            return true;
        }
        types.removeIf(type -> !type.isSelected());

        String senderName  = sender instanceof ConsoleCommandSender ? "[CONSOLE]": sender.getName();
        String senderUUID  = sender instanceof ConsoleCommandSender ? "[CONSOLE]": ((Player) sender).getUniqueId().toString().replace("-", "");

        String id = WatchEyeManagement.add(target, senderName, senderUUID);

        if (WatchEyeManagement.setReason(id, types))
            sender.sendMessage(ChatColor.GREEN + "チート報告ありがとうございます。お客様の懸念を理解し、可能ならば早急に検討させていただきます。");
        return true;
    }
}
