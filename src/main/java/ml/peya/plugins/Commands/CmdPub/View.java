package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.command.*;

import java.sql.*;
import java.util.*;

/**
 * /psac viewで動くクラス。
 */
public class View
{
    /**
     * 関数を稼働させる。
     *
     * @param sender イベントsender。
     * @param args   引数。
     */
    public static void run(CommandSender sender, String[] args)
    {
        sender.sendMessage(MessageEngine.get("base.prefix"));

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

        int count = 0;

        try (Connection connection = PeyangSuperbAntiCheat.eye.getConnection();
             Statement statement = connection.createStatement();
             Statement statement2 = connection.createStatement())
        {
            String idReq = nameFlag ? String.format("WhErE id = '%s'", offName): "";

            if (WatchEyeManagement.isInjection(idReq))
                return;
            String query = "SeLeCt * FrOm WaTcHeYe " + idReq + " OrDer By LeVel DeSc LiMiT 5 OfFsEt " + start;
            ResultSet result = statement.executeQuery(query);
            while (result.next())
            {
                String mngid = result.getString("MNGID");
                if (WatchEyeManagement.isInjection(mngid))
                    return;

                ResultSet reason = statement2.executeQuery("SeLeCt * FrOm WaTcHrEaSoN WhErE MnGiD='" + mngid + "'");
                ArrayList<EnumCheatType> types = new ArrayList<>();

                while (reason.next())
                    types.add(CheatTypeUtils.getCheatTypeFromString(reason.getString("REASON")));

                sender.spigot().sendMessage(TextBuilder.getLine(result.getString("ID"), result.getString("ISSUEBYID"), types, mngid, sender).create());
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
        sender.spigot().sendMessage(TextBuilder.getNextPrevButtonText(TextBuilder.getPrevButton(start - 5), TextBuilder.getNextButton(start + 5), !(start - 5 < 0), !(count < 5)).create());
    }
}
