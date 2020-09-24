package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.math.*;
import java.sql.*;
import java.util.*;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.eye;

/**
 * /psac showで動くクラス。
 */
public class Show
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

        args[1] = WatchEyeManagement.parseInjection(args[1]);

        if (WatchEyeManagement.isExistsRecord(args[1]))
        {
            sender.sendMessage(get("error.showDrop.notFoundReport"));

            return;
        }

        String mngid = args[1];

        try (Connection connection = eye.getConnection();
             Statement statement = connection.createStatement())
        {
            ResultSet result = statement.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE MnGiD='" + mngid + "'");
            result.next();

            final String uuid = result.getString("UUID");
            final String id = result.getString("ID");
            final BigDecimal issuedate = result.getBigDecimal("ISSUEDATE");
            final String issuebyid = result.getString("ISSUEBYID");
            final String issuebyuuid = result.getString("ISSUEBYUUID");

            ResultSet reason = statement.executeQuery("SeLeCt * FrOm WaTcHrEaSoN WhErE MnGiD='" + mngid + "'");

            ArrayList<EnumCheatType> types = new ArrayList<>();

            while (reason.next())
                types.add(CheatTypeUtils.getCheatTypeFromString(reason.getString("REASON")));

            if (sender instanceof Player)
            {
                BookUtil.openBook(Books.getShowBook(id, uuid, issuebyid, issuebyuuid, issuedate, types), (Player) sender);
                return;
            }

            TextBuilder.showText(id, uuid, issuebyid, issuebyuuid, issuedate, types, sender);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            sender.sendMessage(get("error.unknownSQLError"));

            Utils.errorNotification(Utils.getStackTrace(e));
        }
    }
}
