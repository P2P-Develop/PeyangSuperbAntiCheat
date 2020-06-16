package ml.peya.plugins.Commands;

import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.*;
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
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：変数が不足しています。/" + label + " help でヘルプを見てください。");
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
                        ChatColor.AQUA + "-----");
                sender.sendMessage(ChatColor.AQUA + "/" + label + " <PlayerName> [reason...]");
                sender.sendMessage(ChatColor.GREEN + "プレイヤーを報告します。");
                sender.sendMessage(ChatColor.AQUA + "/" + label + " help");
                sender.sendMessage(ChatColor.GREEN + "このコマンドです。");
                return true;
            }
            if (Bukkit.getPlayer(args[0]) == null)
            {
                sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：報告対象プレイヤーが見つかりませんでした。");
                return true;
            }
            else if (sender instanceof ConsoleCommandSender)
            {
                sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：理由短縮モードは、プレイヤーからのみ実行することができます。");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            ItemStack book = Books.getReportBook(target, CheatTypeUtils.getFullType());
            BookUtil.openBook(book, (Player) sender);
            return true;
        }


        ArrayList<String> reasons = new ArrayList<>(Arrays.asList(args));

        reasons.remove(0);

        ArrayList<EnumCheatType> types = CheatTypeUtils.getCheatTypeArrayFromString(reasons.toArray(new String[0]));

        if (Bukkit.getPlayer(args[0]) == null)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：報告対象プレイヤーが見つかりませんでした。");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (reasons.size() != 0 && reasons.get(reasons.size() - 1).equals("\\"))
        {
            ItemStack book = Books.getReportBook(target, types.toArray(new EnumCheatType[0]));
            BookUtil.openBook(book, (Player) sender);
            return true;
        }


        types.removeIf(type -> !type.isSelected());

        if (types.size() == 0)
        {
            if (!reasons.contains("bybooks"))
                sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：理由が正しくありません！");
            else if (args.length == 2 && reasons.contains("bybooks"))
                sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：理由が選択されていません！");

            return true;
        }


        String senderName = sender instanceof ConsoleCommandSender ? "[CONSOLE]" : sender.getName();
        String senderUUID = sender instanceof ConsoleCommandSender ? "[CONSOLE]" : ((Player) sender).getUniqueId().toString().replace("-", "");

        if (WatchEyeManagement.isExistsRecord(target.getUniqueId().toString().replace("-", ""), senderUUID))
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：既に報告済みです！");
            return true;
        }

        String id = WatchEyeManagement.add(target, senderName, senderUUID, SeverityLevelUtils.getSeverity(types).getLevel());
        boolean successFlag = false;
        for (EnumCheatType type : types)
            successFlag = WatchEyeManagement.setReason(id, type, 0);


        if (successFlag)
        {
            sender.sendMessage(ChatColor.GREEN + "不正行為の報告、ありがとうございます。私たちはあなたの懸念を理解しており、できるだけ早急に審査いたします。");

            if (!PeyangSuperbAntiCheat.config.getBoolean("message.lynx"))
            {
                ReportUtils.adminNotification(id);
                return true;
            }

            ArrayList<String> resStr = new ArrayList<>();

            for (EnumCheatType type : types)
                resStr.add(type.getText());

            ReportUtils.adminNotification(target.getName(), id, resStr.toArray(new String[0]));
        }
        else
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：不明なSQLエラーが発生しました。運営に報告しています。");
            ReportUtils.errorNotification("Error：Unknown");
        }
        return true;
    }
}
