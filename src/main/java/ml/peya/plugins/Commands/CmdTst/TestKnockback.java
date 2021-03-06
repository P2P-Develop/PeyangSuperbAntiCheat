package ml.peya.plugins.Commands.CmdTst;

import ml.peya.plugins.Detect.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Moderate.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class TestKnockback implements CommandExecutor
{
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
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));

            return true;
        }

        if (PeyangSuperbAntiCheat.cheatMeta.exists(player.getUniqueId()))
        {
            sender.sendMessage(MessageEngine.get("error.aura.testingNow"));

            return true;
        }

        DetectConnection.scan(player, DetectType.ANTI_KB, sender);
        return true;
    }
}
