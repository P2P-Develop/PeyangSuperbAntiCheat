package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Help
{
    public static void run(CommandSender sender, String label)
    {
        boolean flag = false;
        sender.sendMessage(MessageEngihe.get("base.prefix"));

        if (sender.hasPermission("psac.report"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.report", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psac.view"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.view", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psac.aurapanic"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.aurapanic"));
            flag = true;
        }

        if (sender.hasPermission("psac.aurabot"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.aurabot"));
            flag = true;
        }

        if (sender.hasPermission("psac.show"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.show", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psac.drop"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.drop", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psac.kick"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.kick", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psac.bans"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.bans"));
            flag = true;
        }

        if (!(sender instanceof Player))
            return;

        if (sender.hasPermission("psac.drop") || sender.hasPermission("psac.show"))
            sender.sendMessage(MessageEngihe.get("command.help.mngIdWarning"));


        if (!flag)
            sender.sendMessage(MessageEngihe.get("error.psac.notPage"));
    }
}
