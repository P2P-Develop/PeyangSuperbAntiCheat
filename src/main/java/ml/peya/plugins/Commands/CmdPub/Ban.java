package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.Moderate.BanManager;
import ml.peya.plugins.Utils.MessageEngine;
import ml.peya.plugins.Utils.PlayerUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static ml.peya.plugins.Utils.MessageEngine.get;

/**
 * /psac ban で動くクラス。
 */
public class Ban
{
    /**
     * コマンド
     *
     * @param sender イベントsender。
     * @param args   引数。
     */
    public static void run(CommandSender sender, String[] args)
    {

        if (args.length < 2)
        {
            sender.sendMessage(get("error.invalidArgument"));
            return;
        }

        Player player = PlayerUtils.getPlayerAllowOffline(args[1]);
        if (player == null)
        {
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));
            return;
        }

        String reason = "Banned by Operator.";

        if (args.length < 3)
        {
            ArrayList<String> reasons = new ArrayList<>(Arrays.asList(args));
            reasons.remove(0);
            reasons.remove(0);

            reason = String.join(" ", reasons);
        }

        BanManager.ban(player, reason, null);

    }
}
