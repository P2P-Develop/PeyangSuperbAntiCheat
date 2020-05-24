package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.math.*;
import java.sql.*;
import java.util.*;

public class Show
{
    public static void run(CommandSender sender,  String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー: 引数の数が不正です。/psr help でヘルプを見てください。");
            return;
        }

        String mngid = args[1];

        try (Connection connection = PeyangSuperbAntiCheat.eye.getConnection();
             Statement statement = connection.createStatement())
        {
            ResultSet result = statement.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE MnGiD='" + mngid + "'");
            if (!result.next())
            {
                sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー: IDと合致するデータが見つかりませんでした！");
                return;
            }

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
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー: 不明なSQLエラーが発生しました。運営に報告しています。");
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }
    }
}
