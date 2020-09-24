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

import java.util.HashMap;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.cheatMeta;
import static ml.peya.plugins.Variables.config;

/**
 * Aurabot NPCコマンド系クラス。
 * /aurabot コマンドで動く。
 */
public class AuraBot implements CommandExecutor
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
        if (ErrorMessageSender.unPermMessage(sender, "psac.aurabot") || ErrorMessageSender
                .invalidLengthMessage(sender, args, 1, 2))
            return true;
        Player player = Bukkit.getPlayer(args[0]);
        boolean reachModeEnabled = false;
        if (args.length == 2 && args[0].equals("-r"))
        {
            player = Bukkit.getPlayer(args[1]);
            reachModeEnabled = true;
        }
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


        if (!config.getBoolean("message.lynx"))
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", player.getDisplayName() + (player.getDisplayName().equals(player.getName()) ? "": " (" + player.getName() + ") "));
            map.put("type", "AuraBot");
            map.put("seconds", config.getString("npc.seconds"));

            sender.sendMessage(get("message.aura.summon", map));
        }
        else
            sender.sendMessage(get("message.aura.lynx"));

        DetectConnection.scan(player, DetectType.AURA_BOT, sender, reachModeEnabled);
        return true;
    }
}
