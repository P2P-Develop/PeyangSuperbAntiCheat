package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.Utils.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.*;

import java.sql.*;
import java.util.*;

public class View
{
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

        TextComponent nextBtn = TextBuilder.getNextButton(next);
        TextComponent prevBtn = TextBuilder.getPrevButton(previous);

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
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }

        if (count == 0)
        {
            sender.sendMessage(MessageEngine.get("error.view.notFoundReport"));

            return;
        }
        sender.spigot().sendMessage(TextBuilder.getNextPrevButtonText(prevBtn, nextBtn, !(previous < 0), !(count < 5)).create());
    }
}
