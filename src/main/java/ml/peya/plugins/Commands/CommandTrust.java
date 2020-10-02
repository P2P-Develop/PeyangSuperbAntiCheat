package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Moderate.TrustModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.PlayerUtils.getPlayer;

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
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 1, 1) || ErrorMessageSender.unPermMessage(sender, "psac.trust"))
            return true;

        if (!(sender instanceof Player))
        {
            sender.sendMessage(get("error.requirePlayer"));
            return true;
        }

        Player player = getPlayer(sender, args[0]);

        if (player == null)
            return true;

        if (!player.hasPermission("psac.trust"))
            return ErrorMessageSender.unPermMessage(sender, "psac.trust");

        TrustModifier.addTrustPlayer(player, sender);

        return true;
    }
}
