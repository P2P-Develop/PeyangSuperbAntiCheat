package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Moderate.TrustModifier;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static ml.peya.plugins.Utils.MessageEngine.get;

/**
 * 信用コマンドのクラス。
 * /trust
 */
public class CommandTrust implements CommandExecutor
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
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 0, 1) || ErrorMessageSender.unPermMessage(sender, "psac.trust"))
            return true;

        if (!(sender instanceof Player))
        {
            sender.sendMessage(get("error.requirePlayer"));
            return true;
        }

        if (args.length == 0)
        {
            sender.sendMessage(get("error.invalidArgument"));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(get("error.playerNotFound"));
            return true;
        }

        if (!player.hasPermission("psac.trust"))
            return ErrorMessageSender.unPermMessage(sender, "psac.trust");

        TrustModifier.addTrustPlayer(player, sender);

        return true;
    }
}
