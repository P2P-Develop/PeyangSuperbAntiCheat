package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.DetectClasses.WatchEyeManagement;
import ml.peya.plugins.Enum.EnumCheatType;
import ml.peya.plugins.Moderate.CheatTypeUtils;
import ml.peya.plugins.Utils.MessageEngine;
import ml.peya.plugins.Utils.TextBuilder;
import ml.peya.plugins.Utils.Utils;
import ml.peya.plugins.Variables;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * /psac viewで動くクラス。
 */
public class View
{
    /**
     * コマンド
     *
     * @param sender イベントsender。
     * @param args   引数。
     */
    public static void run(CommandSender sender, String[] args)
    {
        sender.sendMessage(MessageEngine.get("base.prefix"));

        int start = 0;
        int next;
        int previous;
        boolean nameFlag = false;
        String offName = "";
        try
        {
            if (args.length == 2)
                start = Integer.parseInt(args[1]);
        }
        catch (Exception e)
        {
            nameFlag = true;
            offName = args[1];
        }

        next = start + 5;
        previous = start - 5;

        int count = 0;

        try (Connection connection = Variables.eye.getConnection();
             Statement statement = connection.createStatement();
             Statement statement2 = connection.createStatement())
        {
            String idReq = nameFlag ? String.format("WhErE id = '%s'", offName): "";

            String query = "SeLeCt * FrOm WaTcHeYe " + idReq + " OrDer By LeVel DeSc LiMiT 5 OfFsEt " + start;
            ResultSet result = statement.executeQuery(query);
            while (result.next())
            {
                final String id = WatchEyeManagement.parseInjection(result.getString("ID"));
                final String issuebyid = WatchEyeManagement.parseInjection(result.getString("ISSUEBYID"));
                final String mngid = WatchEyeManagement.parseInjection(result.getString("MNGID"));

                ResultSet reason = statement2.executeQuery("SeLeCt * FrOm WaTcHrEaSoN WhErE MnGiD='" + mngid + "'");
                ArrayList<EnumCheatType> types = new ArrayList<>();
                while (reason.next())
                    types.add(CheatTypeUtils.getCheatTypeFromString(reason.getString("REASON")));

                sender.spigot().sendMessage(TextBuilder.getLine(id, issuebyid, types, mngid, sender).create());
                count++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
        }

        if (count == 0)
        {
            sender.sendMessage(MessageEngine.get("error.view.notFoundReport"));

            return;
        }
        sender.spigot().sendMessage(TextBuilder.getNextPrevButtonText(TextBuilder.getPrevButton(previous), TextBuilder.getNextButton(next), previous >= 0, count >= 5).create());
    }
}
