package ml.peya.plugins.Commands;

import ml.peya.plugins.Commands.CmdPub.*;
import ml.peya.plugins.Moderate.*;
import org.bukkit.command.*;

import java.util.*;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;

/**
 * PSACコマンド系クラス。
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

        ArrayList<String> commandList = new ArrayList<>();

        commandList.add("help");
        commandList.add("view");
        commandList.add("show");
        commandList.add("drop");
        commandList.add("kick");

        if (commandList.contains(args[0]) && ErrorMessageSender.unPermMessage(sender, "psac." + args[0])) return true;

        switch (args[0].toLowerCase())
        {
            case "help":
                Help.run(sender, label);
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
