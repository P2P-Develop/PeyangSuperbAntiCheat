package ml.peya.plugins.Commands;

import ml.peya.plugins.*;
import ml.peya.plugins.Moderate.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

/**
 * 信用コマンドのクラス。
 */
public class CommandTrust implements CommandExecutor
{
    /** コマンド動作のオーバーライド。
     * @param sender イベントsender。
     * @param command コマンド。
     * @param label ラベル。
     * @param args 引数。
     *
     * @return 処理を終わらせるだけ。Always true。
     */
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

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));
            return true;
        }

        if (!player.hasPermission("psac.trust"))
        {
            return ErrorMessageSender.unPermMessage(sender, "psac.trust");
        }

        TrustModifier.addTrustPlayer(player, sender);

        return true;
    }
}
