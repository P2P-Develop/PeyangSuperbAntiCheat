package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.BanWithEffect;
import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Utils.PlayerUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;

/**
 * BAN解除系。
 * /unbanでうごく。
 */
public class CommandUnBan implements CommandExecutor
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
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 1, 1) || ErrorMessageSender.unPermMessage(sender, "psac.unban"))
            return true;

        String playerName = args[0];

        OfflinePlayer player = PlayerUtils.getOfflinePlayer(playerName);

        if (player == null)
        {
            sender.sendMessage(get("error.playerNotFound"));
            return true;
        }

        if (!Boolean.parseBoolean(BanWithEffect.getBanInfo(player.getUniqueId()).get("banned")))
        {
            sender.sendMessage(get("error.playerNotBanned"));
            return true;
        }

        BanWithEffect.pardon(player.getUniqueId());

        sender.sendMessage(get("message.unban.playerUnBanned", pair("player", player.getName())));
        return true;
    }
}
