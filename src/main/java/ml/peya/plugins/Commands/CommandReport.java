package ml.peya.plugins.Commands;

import ml.peya.plugins.*;
import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Moderate.*;
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
        if (ErrorMessageSender.unPermMessage(sender, "psac.report"))
            return true;

        if (args.length == 0)
        {
            sender.sendMessage(MessageEngine.get("error.minArgs", MessageEngine.hsh("label", label)));
            return true;
        }
        else if (args.length == 1)
        {
            if (args[0].equals("help"))
            {
                sender.sendMessage(MessageEngine.get("base.prefix"));
                sender.sendMessage(MessageEngine.get("command.report.help", MessageEngine.hsh("label", label)));
                return true;
            }

            if (args[0].equals("$$cancel$$"))
            {
                sender.sendMessage(MessageEngine.get("message.report.cancel"));
                return true;
            }
            else if (Bukkit.getPlayer(args[0]) == null)
            {
                sender.sendMessage(MessageEngine.get("error.playerNotFound"));
                return true;
            }
            else if (sender instanceof ConsoleCommandSender)
            {
                sender.sendMessage(MessageEngine.get("error.requirePlayer"));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            ItemStack book = Books.getReportBook(target, CheatTypeUtils.getFullType());
            BookUtil.openBook(book, (Player) sender);
            return true;
        }


        ArrayList<String> reasonsV = new ArrayList<>(Arrays.asList(args));
        reasonsV.remove(0);

        ArrayList<String> reasons = new ArrayList<>();

        for (String reason: reasonsV)
        {
            if (reasons.contains(reason))
            {
                reasons.remove(reason);
                continue;
            }

            reasons.add(reason);
        }

        ArrayList<EnumCheatType> types = CheatTypeUtils.getCheatTypeArrayFromString(reasons.toArray(new String[0]));

        if (Bukkit.getPlayer(args[0]) == null)
        {
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));
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
            if (!reasons.contains("$__BOOKS__;"))
                sender.sendMessage(MessageEngine.get("error.report.invalidReason"));
            else if (args.length == 2 && reasons.contains("$__BOOKS__;"))
                sender.sendMessage(MessageEngine.get("error.report.reasonNotSelected"));

            return true;
        }


        report(sender, types, target);
        return true;
    }

    private void report(CommandSender sender, ArrayList<EnumCheatType> types, Player target)
    {
        String senderName = sender instanceof ConsoleCommandSender ? "[CONSOLE]" : sender.getName();
        String senderUUID = sender instanceof ConsoleCommandSender ? "[CONSOLE]" : ((Player) sender).getUniqueId().toString().replace("-", "");

        if (WatchEyeManagement.isExistsRecord(target.getUniqueId().toString().replace("-", ""), senderUUID))
        {
            sender.sendMessage(MessageEngine.get("error.report.alreadyReported"));
            return;
        }

        String id = WatchEyeManagement.add(target, senderName, senderUUID, SeverityLevelUtils.getSeverity(types).getLevel());
        boolean successFlag = false;
        for (EnumCheatType type : types)
            successFlag = WatchEyeManagement.setReason(id, type, 0);


        if (successFlag)
        {
            sender.sendMessage(MessageEngine.get("message.report.thanks"));

            if (!PeyangSuperbAntiCheat.config.getBoolean("message.lynx"))
            {
                ReportUtils.adminNotification(id);
                return;
            }

            ArrayList<String> resStr = new ArrayList<>();

            for (EnumCheatType type : types)
                resStr.add(type.getText());

            ReportUtils.adminNotification(target.getName(), id, resStr.toArray(new String[0]));
        }
        else
            sender.sendMessage(MessageEngine.get("error.unknownSQLError"));
    }
}
