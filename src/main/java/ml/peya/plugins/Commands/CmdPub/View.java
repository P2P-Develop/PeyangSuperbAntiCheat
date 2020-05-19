package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.*;

import java.sql.*;
import java.util.*;

public class View
{
    public static void run(CommandSender sender,  String[] args)
    {
        sender.sendMessage(ChatColor.AQUA + "-----" +
                ChatColor.GREEN + "[" +
                ChatColor.BLUE + "PeyangSuperbAntiCheat" +
                ChatColor.GREEN + "]" +
                ChatColor.AQUA + "-----");
        int start = 0;
        int next;
        int previous;
        boolean nameFlag = false;
        String offName = args[0];
        try
        {
            if (args.length == 2)
                start = Integer.parseInt(args[1]);
        }
        catch (Exception e)
        {
            nameFlag = true;
        }

        next = start + 5;
        previous = start - 5;

        TextComponent nextBtn = TextBuilder.getNextButton(next);
        TextComponent prevBtn = TextBuilder.getPrevButton(previous);

        int count = 0;

        int allCount = 0;

        try (Connection connection = PeyangSuperbAntiCheat.hManager.getConnection();
             Statement statement = connection.createStatement())
        {
            String idReq = nameFlag ? String.format("WhErE id = '%s'", offName): "";
            String querey = "SeLeCt * FrOm WaTcHeYe " + idReq + " OrDer By LeVel DeSc LiMiT 5 OfFsEt " + start;
            ResultSet result = statement.executeQuery(querey);
            while (result.next())
            {
                String id = result.getString("ID");
                String issuebyid = result.getString("ISSUEBYID");
                String mngid = result.getString("MNGID");

                ResultSet reason = statement.executeQuery("SeLeCt * FrOm WaTcHrEaSoN WhErE MnGiD='" + mngid + "'");

                ArrayList<EnumCheatType> types = new ArrayList<>();
                while (reason.next())
                    types.add(CheatTypeUtils.getCheatTypeFromString(reason.getString("REASON")));
                ComponentBuilder line = TextBuilder.getLine(id, issuebyid, types, mngid);

                sender.spigot().sendMessage(line.create());
                count++;
            }

            ResultSet allCounts = statement.executeQuery("SeLeCt CoUnT(*) FrOm WaTcHeYe");
            allCounts.next();
            allCount = allCounts.getInt("CoUnT");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }

        if (count == 0)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー: レポートが1件も見つかりませんでした。");
            return;
        }

        sender.sendMessage(ChatColor.GOLD + String.format("                         (%s/%s)",
                (start + count), allCount));
        sender.spigot().sendMessage(TextBuilder.getNextPrevButtonText(prevBtn, nextBtn, !(previous < 0), !(count < 5)).create());
    }
}
