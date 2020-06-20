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

        if (sender.hasPermission("psr.report"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.report", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psr.view"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.view", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psr.automessage"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.automessage"));
            flag = true;
        }

        if (sender.hasPermission("psr.aurapanic"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.aurapanic"));
            flag = true;
        }

        if (sender.hasPermission("psr.aurabot"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.aurabot"));
            flag = true;
        }

        if (sender.hasPermission("psr.show"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.show", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psr.drop"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.drop", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psr.kick"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.kick", MessageEngihe.hsh("label", label)));
            flag = true;
        }

        if (sender.hasPermission("psr.bans"))
        {
            sender.sendMessage(MessageEngihe.get("command.help.bans"));
            flag = true;
        }

        if (!(sender instanceof Player))
            return;

        if (sender.hasPermission("psr.drop") || sender.hasPermission("psr.show"))
            sender.sendMessage(MessageEngihe.get("command.help.mngIdWarning"));


        if (!flag)
            sender.sendMessage(MessageEngihe.get("error.psr.notPage"));
    }
}
