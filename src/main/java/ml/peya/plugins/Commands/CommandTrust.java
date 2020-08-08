package ml.peya.plugins.Commands;

import ml.peya.plugins.*;
import ml.peya.plugins.Moderate.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class CommandTrust implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 0, 1) || ErrorMessageSender.unPermMessage(sender, "psac.trust"))
            return true;

        if (!(sender instanceof Player))
        {
            sender.sendMessage(MessageEngine.get("error.requirePlayer"));
            return true;
        }

        if (args.length == 0)
        {
            sender.sendMessage(MessageEngine.get("error.invalidArgument"));
            return true;
        }

        if (Bukkit.getPlayer(args[0]) == null)
        {
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));
            return true;
        }

        TrustModifier.addTrustPlayer(Bukkit.getPlayer(args[0]), sender);

        return true;
    }
}
