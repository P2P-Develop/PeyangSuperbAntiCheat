package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.DetectClasses.WatchEyeManagement;
import ml.peya.plugins.Enum.EnumCheatType;
import ml.peya.plugins.Moderate.CheatTypeUtils;
import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Objects.Books;
import ml.peya.plugins.Utils.BookUtil;
import ml.peya.plugins.Utils.TextBuilder;
import ml.peya.plugins.Utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

        if (!WatchEyeManagement.isExistsRecord(args[1]))
        {
            sender.sendMessage(get("error.showDrop.notFoundReport"));

            return;
        }

        String mngid = args[1];

        try (Connection connection = eye.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement("SELECT UUID, ID, ISSUEBYID, ISSUEBYUUID, ISSUEDATE, UUID FROM watcheye WHERE MNGID=?");
             PreparedStatement reasons =
                 connection.prepareStatement("SELECT REASON FROM watchreason WHERE MNGID=?"))
        {
            statement.setString(1, mngid);
            ResultSet result = statement.executeQuery();

            if (!result.next())
            {
                sender.sendMessage(get("error.unknownSQLError"));

                return;
            }

            final String uuid = result.getString("UUID");
            final String id = result.getString("ID");
            final BigDecimal issuedate = result.getBigDecimal("ISSUEDATE");
            final String issuebyid = result.getString("ISSUEBYID");
            final String issuebyuuid = result.getString("ISSUEBYUUID");

            reasons.setString(1, mngid);

            ResultSet reason = reasons.executeQuery();

            ArrayList<EnumCheatType> types = new ArrayList<>();

            while (reason.next())
                types.add(CheatTypeUtils.getCheatTypeFromString(reason.getString("REASON")));

            if (sender instanceof Player)
            {
                BookUtil.openBook(Books.getShowBook(id, uuid, issuebyid, issuebyuuid, issuedate, types), (Player) sender);
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
