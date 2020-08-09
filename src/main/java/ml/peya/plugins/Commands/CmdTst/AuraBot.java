package ml.peya.plugins.Commands.CmdTst;

import ml.peya.plugins.Detect.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

/**
 * Aurabot NPCコマンド系クラス。
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
     * @return 処理を終わらせるだけ。Always true。
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psac.aurabot") || ErrorMessageSender.invalidLengthMessage(sender, args, 1, 1))
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
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));

            return true;
        }

        if (TrustModifier.isTrusted(player) && !player.hasPermission("psac.trust"))
        {
            sender.sendMessage(MessageEngine.get("error.trusted"));

            return true;
        }

        if (PeyangSuperbAntiCheat.cheatMeta.exists(player.getUniqueId()))
        {
            sender.sendMessage(MessageEngine.get("error.aura.testingNow"));

            return true;
        }


        if (!PeyangSuperbAntiCheat.config.getBoolean("message.lynx"))
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", player.getDisplayName() + (player.getDisplayName().equals(player.getName()) ? "": (" (" + player.getName() + ") ")));
            map.put("type", "AuraBot");
            map.put("seconds", PeyangSuperbAntiCheat.config.getString("npc.seconds"));

            sender.sendMessage(MessageEngine.get("message.aura.summon", map));
        }
        else
        {
            sender.sendMessage(MessageEngine.get("message.aura.lynx"));
        }

        DetectConnection.scan(player, DetectType.AURA_BOT, sender, reachModeEnabled);
        return true;
    }
}
