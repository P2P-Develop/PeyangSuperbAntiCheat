package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.DetectClasses.WatchEyeManagement;
import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Utils.SQL;
import ml.peya.plugins.Utils.Utils;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.eye;

/**
 * /psac dropで動くクラス。
 */
public class Drop
{
    /**
     * コマンド
     *
     * @param sender イベントsender。
     * @param args   引数。
     */
    public static void run(CommandSender sender, String[] args)
    {
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 2, 2))
            return;

        if (WatchEyeManagement.isExistsRecord(args[1]))
        {
            sender.sendMessage(get("error.showDrop.notFoundReport"));
            return;
        }

        try (Connection connection = eye.getConnection())
        {
            SQL.delete(connection, "watcheye", new HashMap<String, String>(){{put("MNGID", args[1]);}});
            SQL.delete(connection, "watchreason", new HashMap<String, String>(){{put("MNGID", args[1]);}});
            sender.sendMessage(get("message.drop.success"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            sender.sendMessage(get("error.unknownSQLError"));

            Utils.errorNotification(Utils.getStackTrace(e));
        }
    }
}
