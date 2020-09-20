package ml.peya.plugins.Commands;

import ml.peya.plugins.Commands.CmdPub.*;
import ml.peya.plugins.Moderate.ErrorMessageSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;

/**
 * PSACコマンド系クラス。
 * /psac コマンドで動く
 */
public class CommandPeyangSuperbAntiCheat implements CommandExecutor
{
    /**
     * コマンド動作のオーバーライド。
     *
     * @param sender  イベントsender。
     * @param command コマンド。
     * @param label   ラベル。
     * @param args    引数。
     * @return 正常に終わったかどうか。
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 1, 5))
            return true;

        if (Arrays.asList("help", "view", "show", "drop", "kick")
                .contains(args[0]) && ErrorMessageSender.unPermMessage(sender, "psac." + args[0])) return true;

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(args));
        arrayList.remove(0);

        switch (args[0].toLowerCase())
        {
            case "help":
                Help.run(sender, label, arrayList.toArray(new String[0]));
                break;
            case "view":
                View.run(sender, args);
                break;
            case "show":
                Show.run(sender, args);
                break;
            case "drop":
                Drop.run(sender, args);
                break;
            case "kick":
                Kick.run(sender, args);
                break;
            default:
                sender.sendMessage(get("error.main.notFoundCommand", pair("label", label)));
        }

        return true;
    }
}
