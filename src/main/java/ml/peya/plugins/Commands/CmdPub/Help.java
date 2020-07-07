package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Help
{
    public static void run(CommandSender sender, String label)
    {
        boolean flag = false;
        sender.sendMessage(MessageEngine.get("base.prefix"));

        if (sender.hasPermission("psac.report"))
        {
            sender.sendMessage(MessageEngine.get("command.help.report", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psac.view"))
        {
            sender.sendMessage(MessageEngine.get("command.help.view", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psac.aurapanic"))
        {
            sender.sendMessage(MessageEngine.get("command.help.aurapanic"));
            flag = true;
        }

        if (sender.hasPermission("psac.aurabot"))
        {
            sender.sendMessage(MessageEngine.get("command.help.aurabot"));
            flag = true;
        }

        if (sender.hasPermission("psac.show"))
        {
            sender.sendMessage(MessageEngine.get("command.help.show", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psac.drop"))
        {
            sender.sendMessage(MessageEngine.get("command.help.drop", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psac.kick"))
        {
            sender.sendMessage(MessageEngine.get("command.help.kick", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psac.bans"))
        {
            sender.sendMessage(MessageEngine.get("command.help.bans"));
            flag = true;
        }

        if (!(sender instanceof Player))
            return;

        if (sender.hasPermission("psac.drop") || sender.hasPermission("psac.show"))
            sender.sendMessage(MessageEngine.get("command.help.mngIdWarning"));


        if (!flag)
            sender.sendMessage(MessageEngine.get("error.psac.notPage"));
    }
}
