package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.Moderate.KickManager;
import ml.peya.plugins.Moderate.TrustModifier;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;

/**
 * /psac kickで動くクラス。
 */
public class Kick
{
    /**
     * コマンド
     *
     * @param sender イベントsender。
     * @param args   引数。
     */
    public static void run(CommandSender sender, final String[] args)
    {
        boolean test = false;

        if (args.length == 3 && args[2].equals("test"))
        {
            sender.sendMessage(get("message.kick.test"));

            final Player player = Bukkit.getPlayer(args[1]);
            if (player == null)
            {
                sender.sendMessage(get("error.playerNotFound"));

                return;
            }

            if (TrustModifier.isTrusted(player) && !player.hasPermission("psac.trust"))
            {
                sender.sendMessage(get("error.trusted"));

                return;
            }

            test = true;
        }

        if (args.length < 2)
        {
            sender.sendMessage(get("error.minArgs", pair("label", "psr")));

            return;
        }

        final Player player = Bukkit.getPlayer(args[1]);
        if (player == null)
        {
            sender.sendMessage(get("error.playerNotFound"));

            return;
        }

        if (player.hasMetadata("psac-kick"))
        {

            sender.sendMessage(get("error.processing"));
            return;
        }

        ArrayList<String> argSet = new ArrayList<>(Arrays.asList(args));

        argSet.subList(0, 2)
                .clear();

        if (argSet.size() == 0)
            argSet.add("Kicked by Operator.");

        KickManager.kickPlayer(player, String.join(", ", argSet), test, test);
    }
}
