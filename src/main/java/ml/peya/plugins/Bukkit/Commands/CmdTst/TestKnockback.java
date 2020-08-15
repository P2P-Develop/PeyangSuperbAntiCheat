package ml.peya.plugins.Bukkit.Commands.CmdTst;

import ml.peya.plugins.Bukkit.Detect.*;
import ml.peya.plugins.Bukkit.Enum.*;
import ml.peya.plugins.Bukkit.Moderate.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import static ml.peya.plugins.Bukkit.Utils.MessageEngine.get;
import static ml.peya.plugins.Bukkit.Variables.cheatMeta;

/**
 * Test Knockbackコマンド系クラス。
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
