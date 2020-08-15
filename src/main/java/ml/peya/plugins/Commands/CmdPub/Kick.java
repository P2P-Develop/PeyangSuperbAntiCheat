package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.Moderate.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

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
    public static void run(CommandSender sender, String[] args)
    {
        if (args.length == 3 && args[2].equals("test"))
        {
            sender.sendMessage(get("message.kick.test"));

            Player player = Bukkit.getPlayer(args[1]);
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

            KickManager.kickPlayer(player, args[2], true, true);
            return;
        }

        if (args.length < 3)
        {
            sender.sendMessage(get("error.minArgs", pair("label", "psr")));

            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null)
        {
            sender.sendMessage(get("error.playerNotFound"));

            return;
        }

        ArrayList<String> argSet = new ArrayList<>(Arrays.asList(args));

        argSet.remove(1);
        argSet.remove(0);

        KickManager.kickPlayer(player, String.join(", ", argSet), false, false);
    }
}
