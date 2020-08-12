package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.Utils.*;
import ml.peya.plugins.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.*;
import org.bukkit.entity.*;

/**
 * トラッキングコマンド系クラス。
 */
public class CommandTracking implements CommandExecutor
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
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 0, 1) || ErrorMessageSender.unPermMessage(sender, "psac.tracking"))
            return true;

        if (!(sender instanceof Player))
        {
            sender.sendMessage(MessageEngine.get("error.requirePlayer"));

            return true;
        }

        if (Variables.tracker.isTracking(sender.getName()) && args.length == 0)
        {
            Variables.tracker.remove(sender.getName());
            sender.sendMessage(MessageEngine.get("item.stopTarget"));
            ((Player) sender).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageEngine.get("item.tracking.noTarget")));

            return true;
        }

        if (args.length == 0)
        {
            sender.sendMessage(MessageEngine.get("error.invalidArgument"));

            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));

            return true;
        }

        if (TrustModifier.isTrusted(player) && !player.hasPermission("psac.trust"))
        {
            sender.sendMessage(MessageEngine.get("error.trusted"));

            return true;
        }

        Variables.tracker.add(sender.getName(), args[0]);
        sender.sendMessage(MessageEngine.get("message.tracking.track", MessageEngine.pair("player", args[0])));

        return true;
    }
}