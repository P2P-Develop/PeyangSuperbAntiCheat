package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;
import static ml.peya.plugins.Variables.config;

/**
 * Banリスト表示コマンド系クラス。
 */
public class CommandBans implements CommandExecutor
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
        if (ErrorMessageSender.unPermMessage(sender, "psac.bans") || ErrorMessageSender.invalidLengthMessage(sender, args, 1, 2))
            return true;

        String type = args.length == 2 ? args[0]: "-a";
        String name = args.length == 2 ? args[1]: args[0];

        if (!type.equals("-a") && !type.toLowerCase().equals("ban") && !type.toLowerCase().equals("kick"))
        {
            sender.sendMessage(get("error.bans.unknownSearchType"));

            return true;
        }


        BanAnalyzer.Type typeP = BanAnalyzer.Type.toType(type);

        UUID player = null;

        for (OfflinePlayer ofPly : Bukkit.getOfflinePlayers())
            if (ofPly.getName().toLowerCase().equals(name.toLowerCase()))
                player = ofPly.getUniqueId();

        if (player == null)
            for (Player onPly : Bukkit.getOnlinePlayers())
                if (onPly.getName().toLowerCase().equals(name.toLowerCase()))
                    player = onPly.getUniqueId();

        if (player == null)
        {
            sender.sendMessage(get("error.playerNotFound"));
            return true;
        }

        ArrayList<BanAnalyzer.Bans> bans = BanAnalyzer.getAbuse(player, typeP);

        sender.sendMessage(config.getBoolean("message.lynx") ? get("message.bans.lynx", pair("name", name)): get("message.bans.message", pair("name", name)));

        if (bans.size() == 0)
        {
            sender.sendMessage(get("error.bans.databaseInfoNotFound"));
            return true;
        }

        for (int ii = 0; ii < 5; ii++)
        {
            if (ii >= bans.size())
                break;
            sender.spigot().sendMessage(TextBuilder.getTextBan(bans.get(ii), bans.get(ii).getType()).create());
        }
        if (bans.size() <= 5)
            return true;

        int count = bans.size() - 5;
        sender.sendMessage(config.getBoolean("message.lynx") ? get("message.bans.more.lynx", pair("count", count)): get("message.bans.more.normal", pair("count", count)));

        return true;
    }
}
