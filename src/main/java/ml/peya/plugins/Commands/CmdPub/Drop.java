package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import net.citizensnpcs.npc.ai.speech.*;
import org.bukkit.*;
import org.bukkit.command.*;

import java.sql.*;

public class Drop
{
    public static void run(CommandSender sender,  String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー: 引数の数が不正です。/psr help でヘルプを見てください。");
            return;
        }

        if (!WatchEyeManagement.isExistsRecord(args[1]))
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー: IDに合致するレポートが見つかりませんでした！");
            return;
        }

        try (Connection connection = PeyangSuperbAntiCheat.hManager.getConnection();
            Statement statement = connection.createStatement())
        {
            statement.execute("DeLeTe FrOm WaTcHeYe WhErE MnGiD = '" + args[1] + "'");
            statement.execute("DeLeTe FrOm WaTcHrEaSon WhErE MnGiD = '" + args[1] + "'");
            sender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "成功: レポートの削除に成功しました！");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー: 不明なSQLエラーが発生しました。運営に報告しています。");
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }
    }
}
