package ml.peya.plugins.Commands;

import ml.peya.plugins.*;
import ml.peya.plugins.Moderate.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class CommandTracking implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 0, 1))
            return true;

        if (!(sender instanceof Player))
        {
            sender.sendMessage(MessageEngine.get("error.requirePlayer"));
            return true;
        }

        if (ErrorMessageSender.unPermMessage(sender, "psac.tracking"))
            return true;


        if (PeyangSuperbAntiCheat.tracker.isTracking(sender.getName()) && args.length == 0)
        {
            PeyangSuperbAntiCheat.tracker.remove(sender.getName());
            sender.sendMessage(MessageEngine.get("item.stopTarget"));
            ((Player) sender).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageEngine.get("item.tracking.noTarget")));
            return true;
        }
        else if (args.length == 0)
        {
            sender.sendMessage(MessageEngine.get("error.invalidArgument"));
            return true;
        }


        if (Bukkit.getPlayer(args[0]) == null)
        {
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));
            return true;
        }

        PeyangSuperbAntiCheat.tracker.add(sender.getName(), args[0]);
        sender.sendMessage(MessageEngine.get("message.tracking.track", MessageEngine.hsh("player", args[0])));

        return true;
    }
}