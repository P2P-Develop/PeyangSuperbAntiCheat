package ml.peya.plugins.Commands;

import ml.peya.plugins.Bungee;
import ml.peya.plugins.DetectClasses.WatchEyeManagement;
import ml.peya.plugins.Enum.EnumCheatType;
import ml.peya.plugins.Moderate.CheatTypeUtils;
import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Utils.BookUtil;
import ml.peya.plugins.Utils.Books;
import ml.peya.plugins.Utils.SeverityLevels;
import ml.peya.plugins.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;
import static ml.peya.plugins.Variables.config;

/**
 * 報告コマンド系クラス。
 * /wdr または /report... コマンドで動く
 */
public class CommandReport implements CommandExecutor
{
    /**
     * コマンド動作。
     *
     * @param sender  イベントsender。
     * @param command コマンド。
     * @param label   ラベル。
     * @param args    引数。
     *
     * @return 正常に終わったかどうか。
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psac.report"))
            return true;

        if (args.length == 0)
        {
            sender.sendMessage(get("error.minArgs", pair("label", label)));
            return true;
        } else if (args.length == 1)
        {
            if (args[0].equals("help"))
            {
                sender.sendMessage(get("base.prefix"));
                sender.sendMessage(get("command.report.help", pair("label", label)));
                return true;
            }

            if (args[0].equals("$$cancel$$"))
            {
                sender.sendMessage(get("message.report.cancel"));
                return true;
            } else if (Bukkit.getPlayer(args[0]) == null)
            {
                sender.sendMessage(get("error.playerNotFound"));
                return true;
            } else if (sender instanceof ConsoleCommandSender)
            {
                sender.sendMessage(get("error.requirePlayer"));
                return true;
            }
            BookUtil.openBook(Books
                    .getReportBook(Bukkit.getPlayer(args[0]), CheatTypeUtils.getFullType()), (Player) sender);
            return true;
        }

        ArrayList<String> reasonsV = new ArrayList<>(Arrays.asList(args));
        reasonsV.remove(0);

        ArrayList<String> reasons = new ArrayList<>();

        reasonsV.parallelStream()
                .forEachOrdered(reason ->
                {
                    if (reasons.contains(reason))
                    {
                        reasons.remove(reason);
                        return;
                    }
                    reasons.add(reason);
                });

        ArrayList<EnumCheatType> types = CheatTypeUtils.getCheatTypeArrayFromString(reasons.toArray(new String[0]));

        if (Bukkit.getPlayer(args[0]) == null)
        {
            sender.sendMessage(get("error.playerNotFound"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (reasons.size() != 0 && reasons.get(reasons.size() - 1).equals("\\"))
        {
            BookUtil.openBook(Books.getReportBook(target, types.toArray(new EnumCheatType[0])), (Player) sender);
            return true;
        }

        types.removeIf(type -> !type.isSelected());

        if (types.size() == 0)
        {
            if (!reasons.contains("$__BOOKS__;"))
                sender.sendMessage(get("error.report.invalidReason"));
            else if (args.length == 2 && reasons.contains("$__BOOKS__;"))
                sender.sendMessage(get("error.report.reasonNotSelected"));

            return true;
        }

        report(sender, types, target);
        return true;
    }

    /**
     * 思いっきり通報する。
     *
     * @param sender イベントsender。
     * @param types  判定タイプ。
     * @param target ターゲット。
     */
    private void report(CommandSender sender, final ArrayList<EnumCheatType> types, Player target)
    {
        final String senderUUID = sender instanceof ConsoleCommandSender
                ? "[CONSOLE]"
                : ((Player) sender).getUniqueId().toString().replace("-", "");

        if (WatchEyeManagement.isExistsRecord(target.getUniqueId().toString().replace("-", ""), senderUUID))
        {
            sender.sendMessage(get("error.report.alreadyReported"));
            return;
        }

        final String id = WatchEyeManagement.add(target, sender instanceof ConsoleCommandSender ? "[CONSOLE]" : sender
                .getName(), senderUUID, SeverityLevels.getSeverity(types)
                                                      .getLevel());
        boolean successFlag = false;
        for (EnumCheatType type : types)
            successFlag = WatchEyeManagement.setReason(id, type, 0);


        if (successFlag)
        {
            sender.sendMessage(get("message.report.thanks"));

            if (!config.getBoolean("message.lynx"))
            {
                Utils.adminNotification(id);
                return;
            }

            Utils.adminNotification(target.getName(), id, types.parallelStream()
                                                               .map(EnumCheatType::getText)
                                                               .toArray(String[]::new));
            Bungee.sendMessage("report " + id + " " + target.getName());
            return;
        }

        sender.sendMessage(get("error.unknownSQLError"));
    }
}
