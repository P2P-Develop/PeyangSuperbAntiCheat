package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.DetectClasses.WatchEyeManagement;
import ml.peya.plugins.Enum.EnumCheatType;
import ml.peya.plugins.Moderate.CheatTypeUtils;
import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Utils.BookUtil;
import ml.peya.plugins.Utils.Books;
import ml.peya.plugins.Utils.TextBuilder;
import ml.peya.plugins.Utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
                BookUtil.openBook(Books
                        .getShowBook(id, uuid, issuebyid, issuebyuuid, issuedate, types), (Player) sender);
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
