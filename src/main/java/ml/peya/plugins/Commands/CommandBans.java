package ml.peya.plugins.Commands;

import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

public class CommandBans implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psr.bans") || ErrorMessageSender.invalidLengthMessage(sender, args, 1, 2))
            return true;


        String type;
        String name;
        if (args.length == 2)
        {
            type = args[0];
            name = args[1];
        }
        else
        {
            type = "-a";
            name = args[0];
        }

        if (!type.equals("-a") && !type.toLowerCase().equals("ban") && !type.toLowerCase().equals("kick"))
        {
            sender.sendMessage(MessageEngihe.get("error.bans.unknownSearchType"));

            return true;
        }


        BanAnalyzer.Type typeP = BanAnalyzer.Type.toType(type);

        UUID player = null;

        for (OfflinePlayer ofPly: Bukkit.getOfflinePlayers())
            if (ofPly.getName().toLowerCase().equals(name.toLowerCase()))
                player = ofPly.getUniqueId();

        if (player == null)
            for (Player onPly: Bukkit.getOnlinePlayers())
                if (onPly.getName().toLowerCase().equals(name.toLowerCase()))
                    player = onPly.getUniqueId();

        if (player == null)
        {
            sender.sendMessage(MessageEngihe.get("error.playerNotFound"));
            return true;
        }

        ArrayList<BanAnalyzer.Bans> bans = BanAnalyzer.getAbuse(player, typeP);

        for (BanAnalyzer.Bans ban: bans)
            sender.spigot().sendMessage(TextBuilder.getTextBan(ban, ban.getType()).create());
        if (bans.size() == 0)
            sender.sendMessage(MessageEngihe.get("error.bans.databaseInfoNotFound"));
        return true;
    }
}
