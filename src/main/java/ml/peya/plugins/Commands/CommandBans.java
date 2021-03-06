package ml.peya.plugins.Commands;

import ml.peya.plugins.*;
import ml.peya.plugins.Moderate.*;
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
        if (ErrorMessageSender.unPermMessage(sender, "psac.bans") || ErrorMessageSender.invalidLengthMessage(sender, args, 1, 2))
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
            sender.sendMessage(MessageEngine.get("error.bans.unknownSearchType"));

            return true;
        }


        BanAnalyzer.Type typeP = BanAnalyzer.Type.toType(type);

        UUID player = null;

        for (OfflinePlayer ofPly : Bukkit.getOfflinePlayers())
        {
            if (ofPly.getName().toLowerCase().equals(name.toLowerCase()))
                player = ofPly.getUniqueId();
        }

        if (player == null)
            for (Player onPly : Bukkit.getOnlinePlayers())
            {
                if (onPly.getName().toLowerCase().equals(name.toLowerCase()))
                    player = onPly.getUniqueId();
            }

        if (player == null)
        {
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));
            return true;
        }

        ArrayList<BanAnalyzer.Bans> bans = BanAnalyzer.getAbuse(player, typeP);

        if (PeyangSuperbAntiCheat.config.getBoolean("message.lynx"))
            sender.sendMessage(MessageEngine.get("message.bans.lynx", MessageEngine.hsh("name", name)));
        else
            sender.sendMessage(MessageEngine.get("message.bans.message", MessageEngine.hsh("name", name)));

        if (bans.size() == 0)
            sender.sendMessage(MessageEngine.get("error.bans.databaseInfoNotFound"));

        for (int ii = 0; ii < 5; ii++)
        {
            sender.spigot().sendMessage(TextBuilder.getTextBan(bans.get(ii), bans.get(ii).getType()).create());
        }

        if (bans.size() <= 5)
            return true;

        int count = bans.size() - 5;
        if (PeyangSuperbAntiCheat.config.getBoolean("message.lynx"))
            sender.sendMessage(MessageEngine.get("message.bans.more.lynx", MessageEngine.hsh("count", count)));
        else
            sender.sendMessage(MessageEngine.get("message.bans.more.normal", MessageEngine.hsh("count", count)));

        return true;
    }
}
