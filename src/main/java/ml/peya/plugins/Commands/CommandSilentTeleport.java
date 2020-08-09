package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;

/**
 * STPコマンド系プラグイン。
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
     * @return 処理を終わらせるだけ。Always true。
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 0, 2) || ErrorMessageSender.unPermMessage(sender, "psac.silentteleport"))
            return true;

        if (!(sender instanceof Player))
        {
            sender.sendMessage(MessageEngine.get("error.requirePlayer"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        Player player = (Player) sender;

        if (args.length == 2)
        {
            target = Bukkit.getPlayer(args[1]);
            player = Bukkit.getPlayer(args[0]);
        }

        if (target == null || player == null)
        {
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));
            return true;
        }

        player.teleport(target.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
        player.sendMessage(MessageEngine.get("message.teleport.teleport", MessageEngine.hsh("player", target.getName())));
        return true;
    }
}
