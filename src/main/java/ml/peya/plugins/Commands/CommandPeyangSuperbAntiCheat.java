package ml.peya.plugins.Commands;

import ml.peya.plugins.Commands.CmdPub.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;

public class CommandPeyangSuperbAntiCheat implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：引数の数が不正です。/psr help でヘルプを見てください。");
            return true;
        }

        switch (args[0])
        {
            case "help":
                if (!Permission.unPermMessage(sender, "psr.help"))
                    return true;
                Help.run(sender, label);
                break;
            case "view":
                if (!Permission.unPermMessage(sender, "psr.viewreport"))
                    return true;
                View.run(sender, args);
                break;
            case "show":
                if (!Permission.unPermMessage(sender, "psr.showreport"))
                    return true;
                Show.run(sender, args);
                break;
            case "drop":
                if (!Permission.unPermMessage(sender, "psr.drop"))
                    return true;
                Drop.run(sender, args);
                break;
            case "learn":
                if (!Permission.unPermMessage(sender, "psr.learn"))
                    return true;
                Learn.run(sender);
                break;
            case "kick":
                if (!Permission.unPermMessage(sender, "psr.kick"))
                    return true;
                Kick.run(sender, args);
                break;
            default:
                sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：対応するコマンドが見つかりませんでした。/" + label + " help でヘルプを見てください。");
        }

        return true;

    }
}
