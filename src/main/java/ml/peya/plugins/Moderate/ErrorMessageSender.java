package ml.peya.plugins.Moderate;

import ml.peya.plugins.*;
import org.bukkit.command.*;

public class ErrorMessageSender
{
    public static boolean unPermMessage(CommandSender sender, String perm)
    {
        if (sender.hasPermission(perm))
            return false;
        sender.sendMessage(MessageEngine.get("error.notHavePermission"));
        return true;
    }

    public static boolean invalidLengthMessage(CommandSender sender,  String[] args, int min, int max)
    {
        if (args.length < min || args.length > max)
        {
            sender.sendMessage(MessageEngine.get("error.invalidArgument"));
            return true;
        }

        return false;
    }
}
