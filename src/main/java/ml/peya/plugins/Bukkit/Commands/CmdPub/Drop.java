package ml.peya.plugins.Bukkit.Commands.CmdPub;

import ml.peya.plugins.Bukkit.DetectClasses.*;
import ml.peya.plugins.Bukkit.Moderate.*;
import ml.peya.plugins.Bukkit.Utils.*;
import org.bukkit.command.*;

import java.sql.*;

import static ml.peya.plugins.Bukkit.Utils.MessageEngine.get;
import static ml.peya.plugins.Bukkit.Variables.eye;

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

        if (WatchEyeManagement.isInjection(args[1]) || WatchEyeManagement.isExistsRecord(args[1]))
        {
            sender.sendMessage(get("error.showDrop.notFoundReport"));

            return;
        }

        try (Connection connection = eye.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.execute("DeLeTe FrOm WaTcHeYe WhErE mNGiD = '" + args[1] + "'");
            statement.execute("DeLeTe FrOm WaTcHrEaSon WHeRe MnGiD = '" + args[1] + "'");
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
