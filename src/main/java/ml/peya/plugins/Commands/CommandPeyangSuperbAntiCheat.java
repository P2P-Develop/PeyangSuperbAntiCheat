package ml.peya.plugins.Commands;

import ml.peya.plugins.Commands.CmdPub.*;
import org.bukkit.*;
import org.bukkit.command.*;

public class CommandPeyangSuperbAntiCheat implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!sender.hasPermission("psr.admin"))
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー: あなたには権限がありません！");
            return true;
        }

        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "エラー：変数が不足しています。/" + label + " help でヘルプを見てください。");
            return true;
        }

        switch (args[0])
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
            case "test":
                Test.run(sender, args);
        }

        return true;

    }
}
