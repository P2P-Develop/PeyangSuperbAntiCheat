package ml.peya.plugins.Bukkit.Commands.CmdPub;

import ml.peya.plugins.Bukkit.Moderate.*;
import ml.peya.plugins.Bukkit.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

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
            sender.sendMessage(MessageEngine.get("message.kick.test"));

            Player player = Bukkit.getPlayer(args[1]);
            if (player == null)
            {
                sender.sendMessage(MessageEngine.get("error.playerNotFound"));

                return;
            }

            if (TrustModifier.isTrusted(player) && !player.hasPermission("psac.trust"))
            {
                sender.sendMessage(MessageEngine.get("error.trusted"));

                return;
            }

            KickManager.kickPlayer(player, args[2], true, true);
            return;
        }

        if (args.length < 3)
        {
            sender.sendMessage(MessageEngine.get("error.minArgs", MessageEngine.pair("label", "psr")));

            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null)
        {
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));

            return;
        }

        ArrayList<String> argSet = new ArrayList<>(Arrays.asList(args));

        argSet.remove(1);
        argSet.remove(0);

        KickManager.kickPlayer(player, String.join(", ", argSet), false, false);
    }
}
