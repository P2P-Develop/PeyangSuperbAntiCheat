package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.command.*;

import java.sql.*;
import java.util.*;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.eye;

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
        sender.sendMessage(get("base.prefix"));

        int start = 0;
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

        int next = start + 5;
        int previous = start - 5;

        int count = 0;

        try (Connection connection = eye.getConnection();
             Statement statement = connection.createStatement();
             Statement statement2 = connection.createStatement())
        {
            String idReq = nameFlag ? String.format("WhErE id = '%s'", offName): "";

            offName = WatchEyeManagement.parseInjection(offName);

            String query = "SeLeCt * FrOm WaTcHeYe " + idReq + " OrDer By LeVel DeSc LiMiT 5 OfFsEt " + start;
            ResultSet result = statement.executeQuery(query);
            while (result.next())
            {

                String id = result.getString("ID");
                String issuebyid = result.getString("ISSUEBYID");
                String mngid = result.getString("MNGID");

                id = WatchEyeManagement.parseInjection(id);
                issuebyid = WatchEyeManagement.parseInjection(issuebyid);
                mngid = WatchEyeManagement.parseInjection(mngid);

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
            sender.sendMessage(get("error.view.notFoundReport"));

            return;
        }
        sender.spigot().sendMessage(TextBuilder.getNextPrevButtonText(TextBuilder.getPrevButton(previous), TextBuilder.getNextButton(next), !(previous < 0), !(count < 5)).create());
    }

}
