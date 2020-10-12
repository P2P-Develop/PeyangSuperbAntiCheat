package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Moderate.TrustModifier;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;
import static ml.peya.plugins.Utils.PlayerUtils.getPlayer;
import static ml.peya.plugins.Variables.tracker;

/**
 * トラッキングコマンド系クラス。
 * /track コマンドで動く
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
            sender.sendMessage(get("error.requirePlayer"));

            return true;
        }

        if (tracker.isTracking(sender.getName()) && args.length == 0)
        {
            tracker.remove(sender.getName());
            sender.sendMessage(get("item.stopTarget"));
            ((Player) sender).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(get("item.tracking.noTarget")));

            return true;
        }
        else if (args.length == 0)
        {
            sender.sendMessage(get("error.invalidArgument"));
            return true;
        }

        Player player = getPlayer(sender, args[0]);

        if (player == null)
            return true;

        if (TrustModifier.isTrusted(player) && !player.hasPermission("psac.trust"))
        {
            sender.sendMessage(get("error.trusted"));

            return true;
        }

        tracker.add(sender.getName(), args[0]);
        sender.sendMessage(get("message.tracking.track", pair("player", args[0])));

        return true;
    }


}
