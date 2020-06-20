package ml.peya.plugins.Commands.CmdTst;

import ml.peya.plugins.Detect.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

public class AuraBot implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psr.aurabot") || ErrorMessageSender.invalidLengthMessage(sender, args, 1, 1))
            return true;
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage(MessageEngihe.get("error.playerNotFound"));

            return true;
        }

        String name = player.getDisplayName() + (player.getDisplayName().equals(player.getName()) ? "": (" (" + player.getName() + ") "));

        if(PeyangSuperbAntiCheat.cheatMeta.exists(player.getUniqueId()))
        {
            sender.sendMessage(MessageEngihe.get("error.aura.testingNow"));

            return true;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("type", "AuraBot");
        map.put("seconds", PeyangSuperbAntiCheat.config.getString("npc.seconds"));

        sender.sendMessage(MessageEngihe.get("message.aura.summon", map));

        NPCConnection.scan(player, DetectType.AURA_BOT, sender);
        return true;
    }
}
