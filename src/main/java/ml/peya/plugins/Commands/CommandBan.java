package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.BanWithEffect;
import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Utils.MessageEngine;
import ml.peya.plugins.Utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static ml.peya.plugins.Utils.MessageEngine.get;

/**
 * /psac ban で動くクラス。
 */
public class CommandBan implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psac.ban"))
            return true;
        if (args.length < 1)
        {
            sender.sendMessage(get("error.invalidArgument"));
            return true;
        }

        Player player = PlayerUtils.getPlayerAllowOffline(args[0]);
        if (player == null)
        {
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));
            return true;
        }

        String reason = "Banned by Operator.";

        if (args.length < 3)
        {
            ArrayList<String> reasons = new ArrayList<>(Arrays.asList(args));
            reasons.remove(0);

            reason = String.join(" ", reasons);
        }

        BanWithEffect.ban(player, reason, null);

        return true;
    }
}
