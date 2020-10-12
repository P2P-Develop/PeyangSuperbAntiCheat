package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.BanAnalyzer;
import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Utils.TextBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

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

        if (!type.equals("-a") && !type.equalsIgnoreCase("ban") && !type.equalsIgnoreCase("kick"))
        {
            sender.sendMessage(get("error.bans.unknownSearchType"));

            return true;
        }

        final UUID[] player = {null};

        Arrays.stream(Bukkit.getOfflinePlayers())
            .parallel()
            .forEachOrdered(ofPly ->
                player[0] = ofPly.getName().equalsIgnoreCase(name)
                    ? ofPly.getUniqueId()
                    : player[0]);

        if (player[0] == null)
        {
            Arrays.stream(((Player[]) Bukkit.getOnlinePlayers().toArray()))
                .parallel()
                .forEachOrdered(onPly ->
                    player[0] = onPly.getName().equalsIgnoreCase(name)
                        ? onPly.getUniqueId()
                        : player[0]);

            if (player[0] == null)
            {
                sender.sendMessage(get("error.playerNotFound"));
                return true;
            }
        }

        ArrayList<BanAnalyzer.Bans> bans = BanAnalyzer.getAbuse(player[0], BanAnalyzer.Type.toType(type));

        sender.sendMessage(
            config.getBoolean("message.lynx")
                ? get("message.bans.lynx", pair("name", name))
                : get("message.bans.message", pair("name", name))
        );

        if (bans.size() == 0)
            sender.sendMessage(get("error.bans.databaseInfoNotFound"));

        int i = 0;
        do
        {
            if (i >= bans.size())
                break;
            sender.spigot().sendMessage(TextBuilder.getTextBan(bans.get(i), bans.get(i++).getType()).create());
        }
        while (i < 5);

        if (bans.size() <= 5)
            return true;

        final int count = bans.size() - 5;
        sender.sendMessage(config.getBoolean("message.lynx")
            ? get("message.bans.more.lynx", pair("count", count))
            : get("message.bans.more.normal", pair("count", count)));

        return true;
    }
}
