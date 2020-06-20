package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

public class Kick
{
    public static void run(CommandSender sender, String[] args)
    {
        if (args.length == 3 && args[2].equals("test"))
        {

            sender.sendMessage(MessageEngihe.get("message.kick.test"));

            Player player = Bukkit.getPlayer(args[1]);
            if (player == null)
            {
                sender.sendMessage(MessageEngihe.get("error.playerNotFound"));

                return;
            }

            KickUtil.kickPlayer(player, args[2], true, true);
            return;
        }

        if (args.length < 3)
        {
            sender.sendMessage(MessageEngihe.get("error.minArgs", MessageEngihe.hsh("label", "psr")));

            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null)
        {
            sender.sendMessage(MessageEngihe.get("error.playerNotFound"));

            return;
        }

        ArrayList<String> argSet = new ArrayList<>(Arrays.asList(args));

        argSet.remove(1);
        argSet.remove(0);


        KickUtil.kickPlayer(player, String.join(", ", argSet), false, false);
    }
}
