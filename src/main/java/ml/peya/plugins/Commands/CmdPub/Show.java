package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.*;
import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.math.*;
import java.sql.*;
import java.util.*;

public class Show
{
    public static void run(CommandSender sender, String[] args)
    {
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 2, 2))
            return;

        if (WatchEyeManagement.isInjection(args[1]) || WatchEyeManagement.isExistsRecord(args[1]))
        {
            sender.sendMessage(MessageEngine.get("error.showDrop.notFoundReport"));

            return;
        }

        String mngid = args[1];

        try (Connection connection = PeyangSuperbAntiCheat.eye.getConnection();
             Statement statement = connection.createStatement())
        {
            ResultSet result = statement.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE MnGiD='" + mngid + "'");
            result.next();

            String uuid = result.getString("UUID");
            String id = result.getString("ID");
            BigDecimal issuedate = result.getBigDecimal("ISSUEDATE");
            String issuebyid = result.getString("ISSUEBYID");
            String issuebyuuid = result.getString("ISSUEBYUUID");

            ResultSet reason = statement.executeQuery("SeLeCt * FrOm WaTcHrEaSoN WhErE MnGiD='" + mngid + "'");

            ArrayList<EnumCheatType> types = new ArrayList<>();

            while (reason.next())
                types.add(CheatTypeUtils.getCheatTypeFromString(reason.getString("REASON")));

            if (sender instanceof Player)
                BookUtil.openBook(Books.getShowBook(id, uuid, issuebyid, issuebyuuid, issuedate, types), (Player) sender);
            else
                TextBuilder.showText(id, uuid, issuebyid, issuebyuuid, issuedate, types, sender);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            sender.sendMessage(MessageEngine.get("error.unknownSQLError"));

            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }
    }
}
