package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.ErrorMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;

/**
 * STPコマンド系プラグイン。
 * /tpto コマンドで動く
 */
public class CommandSilentTeleport implements CommandExecutor
{
    /**
     * コマンド動作のオーバーライド。
     *
     * @param sender  イベントsender。
     * @param command コマンド。
     * @param label   ラベル。
     * @param args    引数。
     *
     * @return 正常に終わったかどうか。
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 0, 2) || ErrorMessageSender.unPermMessage(sender, "psac.silentteleport"))
            return true;

        if (!(sender instanceof Player))
        {
            sender.sendMessage(get("error.requirePlayer"));
            return true;
        }

        final Player target = args.length == 2 ? Bukkit.getPlayer(args[1]) : Bukkit.getPlayer(args[0]);
        final Player player = args.length == 2 ? Bukkit.getPlayer(args[0]) : (Player) sender;

        if (target == null || player == null)
        {
            sender.sendMessage(get("error.playerNotFound"));
            return true;
        }

        player.teleport(target.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
        player.sendMessage(get("message.teleport.teleport", pair("player", target.getName())));
        return true;
    }
}
