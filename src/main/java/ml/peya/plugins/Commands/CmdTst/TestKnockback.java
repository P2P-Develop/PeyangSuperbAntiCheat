package ml.peya.plugins.Commands.CmdTst;

import ml.peya.plugins.Detect.DetectConnection;
import ml.peya.plugins.Enum.DetectType;
import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Moderate.TrustModifier;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.cheatMeta;

/**
 * Test Knockbackコマンド系クラス。
 * /testkb コマンドで動く。
 */
public class TestKnockback implements CommandExecutor
{
    /**
     * コマンド動作のオーバーライド。
     *
     * @param sender イベントsender。
     * @param cmd    コマンド。
     * @param label  ラベル。
     * @param args   引数。
     *
     * @return 正常に終わったかどうか。
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psac.testkb"))
            return true;

        if (ErrorMessageSender.invalidLengthMessage(sender, args, 1, 1))
            return true;

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage(get("error.playerNotFound"));

            return true;
        }

        if (TrustModifier.isTrusted(player) && !player.hasPermission("psac.trust"))
        {
            sender.sendMessage(get("error.trusted"));

            return true;
        }

        if (cheatMeta.exists(player.getUniqueId()))
        {
            sender.sendMessage(get("error.aura.testingNow"));

            return true;
        }

        DetectConnection.scan(player, DetectType.ANTI_KB, sender, false);
        return true;
    }
}
