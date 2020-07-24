package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.command.*;

import java.sql.*;

public class Drop
{
    public static void run(CommandSender sender, String[] args)
    {
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 2, 2))
            return;

        if (WatchEyeManagement.isInjection(args[1]) || !WatchEyeManagement.isExistsRecord(args[1]))
        {
            sender.sendMessage(MessageEngine.get("error.showDrop.notFoundReport"));

            return;
        }

        try (Connection connection = PeyangSuperbAntiCheat.eye.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.execute("DeLeTe FrOm WaTcHeYe WhErE mNGiD = '" + args[1] + "'");
            statement.execute("DeLeTe FrOm WaTcHrEaSon WHeRe MnGiD = '" + args[1] + "'");
            sender.sendMessage(MessageEngine.get("message.drop.success"));


        }
        catch (Exception e)
        {
            e.printStackTrace();
            sender.sendMessage(MessageEngine.get("error.unknownSQLError"));

            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }
    }
}
