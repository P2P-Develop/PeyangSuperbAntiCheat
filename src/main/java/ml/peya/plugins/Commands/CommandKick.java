package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Moderate.KickManager;
import ml.peya.plugins.Moderate.TrustModifier;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;

/**
 * Kick!!!
 * /kick で動く。
 */
public class CommandKick implements CommandExecutor
{
    /**
     * コマンド動作のオーバーライド。
     *
     * @param sender イベントsender。
     * @param cmd    コマンド。
     * @param label  ラベル。
     * @param args   引数。
     * @return 正常に終わったかどうか。
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psac.kick"))
            return true;
        boolean test = false;

        if (args.length == 2 && args[1].equals("test"))
        {
            sender.sendMessage(get("message.kick.test"));

            final Player player = Bukkit.getPlayer(args[0]);
            if (player == null)
            {
                sender.sendMessage(get("error.playerNotFound"));

                return true;
            }

            if (TrustModifier.isTrusted(player) && !player.hasPermission("psac.trust"))
            {
                sender.sendMessage(get("error.trusted"));

                return true;
            }

            test = true;
        }

        if (args.length < 1)
        {
            sender.sendMessage(get("error.minArgs", pair("label", "psac")));

            return true;
        }

        final Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage(get("error.playerNotFound"));

            return true;
        }

        if (player.hasMetadata("psac-kick"))
        {

            sender.sendMessage(get("error.processing"));
            return true;
        }

        ArrayList<String> argSet = new ArrayList<>(Arrays.asList(args));

        argSet.remove(0);

        if (argSet.size() == 0)
            argSet.add("Kicked by Operator.");

        KickManager.kickPlayer(player, String.join(", ", argSet), test, test);
        return true;
    }
}
