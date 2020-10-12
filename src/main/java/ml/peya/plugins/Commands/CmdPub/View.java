package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.Enum.EnumCheatType;
import ml.peya.plugins.Moderate.CheatTypeUtils;
import ml.peya.plugins.Utils.MessageEngine;
import ml.peya.plugins.Utils.TextBuilder;
import ml.peya.plugins.Utils.Utils;
import ml.peya.plugins.Variables;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
             PreparedStatement statement =
                 connection.prepareStatement("SELECT ID, ISSUEBYID, MNGID FROM watcheye " + (nameFlag ? "WHERE ID=?": "") +
                     "ORDER BY LEVEL DESC LIMIT 5 OFFSET ?");
             PreparedStatement statement2 =
                 connection.prepareStatement("SELECT REASON from watchreason WHERE MNGID=?"))
        {
            if (nameFlag)
            {
                statement.setString(1, offName);
                statement.setInt(2, start);
            }
            else
                statement.setInt(1, start);

            ResultSet result = statement.executeQuery();
            while (result.next())
            {
                final String id = result.getString("ID");
                final String issuebyid = result.getString("ISSUEBYID");
                final String mngid = result.getString("MNGID");

                statement2.setString(1, mngid);

                ResultSet reason = statement2.executeQuery();
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
