package ml.peya.plugins.Commands.CmdTst;

import ml.peya.plugins.Detect.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Moderate.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

public class AuraPanic implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psac.aurapanic") || ErrorMessageSender.invalidLengthMessage(sender, args, 1, 2))
            return true;

        int sec = 5;

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

        if (PeyangSuperbAntiCheat.config.getBoolean("message.lynx"))
            sender.sendMessage(MessageEngihe.get("message.aura.lynx"));
        else
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("type", "AuraPanicBot");
            map.put("seconds", String.valueOf(sec));

            sender.sendMessage(MessageEngihe.get("message.aura.summon", map));

        }


        DetectType type = DetectType.AURA_PANIC;

        DetectConnection.scan(player, type, sender);
        return true;
    }
}
