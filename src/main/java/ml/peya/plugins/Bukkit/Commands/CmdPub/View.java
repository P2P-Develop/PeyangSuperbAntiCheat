package ml.peya.plugins.Bukkit.Commands.CmdPub;

import ml.peya.plugins.Bukkit.DetectClasses.*;
import ml.peya.plugins.Bukkit.Enum.*;
import ml.peya.plugins.Bukkit.Moderate.*;
import ml.peya.plugins.Bukkit.Utils.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.*;

import java.sql.*;
import java.util.*;

import static ml.peya.plugins.Bukkit.Utils.MessageEngine.get;
import static ml.peya.plugins.Bukkit.Variables.eye;

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

        TextComponent nextBtn = TextBuilder.getNextButton(next);
        TextComponent prevBtn = TextBuilder.getPrevButton(previous);

        int count = 0;


        try (Connection connection = eye.getConnection();
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

                String id = result.getString("ID");
                String issuebyid = result.getString("ISSUEBYID");
                String mngid = result.getString("MNGID");
                if (WatchEyeManagement.isInjection(mngid))
                    return;

                ResultSet reason = statement2.executeQuery("SeLeCt * FrOm WaTcHrEaSoN WhErE MnGiD='" + mngid + "'");
                ArrayList<EnumCheatType> types = new ArrayList<>();
                while (reason.next())
                {
                    types.add(CheatTypeUtils.getCheatTypeFromString(reason.getString("REASON")));
                }
                ComponentBuilder line = TextBuilder.getLine(id, issuebyid, types, mngid, sender);

                sender.spigot().sendMessage(line.create());
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
        sender.spigot().sendMessage(TextBuilder.getNextPrevButtonText(prevBtn, nextBtn, !(previous < 0), !(count < 5)).create());
    }

}
