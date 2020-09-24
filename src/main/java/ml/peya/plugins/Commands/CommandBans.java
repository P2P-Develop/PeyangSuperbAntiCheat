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
 * /ban コマンドで動く
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

        final String type = args.length == 2 ? args[0]: "-a";
        final String name = args.length == 2 ? args[1]: args[0];

        if (!type.equals("-a") && !type.toLowerCase()
                .equals("ban") && !type.toLowerCase()
                .equals("kick"))
        {
            sender.sendMessage(get("error.bans.unknownSearchType"));

            return true;
        }

        final UUID[] player = {null};

        Arrays.stream(Bukkit.getOfflinePlayers())
                .parallel()
                .forEachOrdered(ofPly ->
                        player[0] = ofPly.getName()
                                .toLowerCase()
                                .equals(name.toLowerCase())
                                ? ofPly.getUniqueId()
                                : player[0]);

        if (player[0] == null)
        {
            Arrays.stream(((Player[]) Bukkit.getOnlinePlayers()
                    .toArray()))
                    .parallel()
                    .forEachOrdered(onPly ->
                            player[0] = onPly.getName()
                                    .toLowerCase()
                                    .equals(name.toLowerCase())
                                    ? onPly.getUniqueId()
                                    : player[0]);

            if (player[0] == null)
            {
                sender.sendMessage(get("error.playerNotFound"));
                return true;
            }
        }

        ArrayList<BanAnalyzer.Bans> bans = BanAnalyzer.getAbuse(player[0], BanAnalyzer.Type.toType(type));

        sender.sendMessage(config.getBoolean("message.lynx") ? get("message.bans.lynx", pair("name", name)): get("message.bans.message", pair("name", name)));

        if (bans.size() == 0)
            sender.sendMessage(get("error.bans.databaseInfoNotFound"));

        int i = 0;
        do
        {
            if (i >= bans.size())
                break;
            sender.spigot()
                    .sendMessage(TextBuilder.getTextBan(bans.get(i), bans.get(i++)
                            .getType())
                            .create());
        }
        while (i < 5);

        if (bans.size() <= 5)
            return true;

        final int count = bans.size() - 5;
        sender.sendMessage(config.getBoolean("message.lynx") ? get("message.bans.more.lynx", pair("count", count)): get("message.bans.more.normal", pair("count", count)));

        return true;
    }
}
