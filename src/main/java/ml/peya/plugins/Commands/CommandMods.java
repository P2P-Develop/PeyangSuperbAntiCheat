package ml.peya.plugins.Commands;

import ml.peya.plugins.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

import java.util.*;

public class CommandMods implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psac.mods") || ErrorMessageSender.invalidLengthMessage(sender, args, 1, 1))
            return true;

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));
            return true;
        }

        HashMap<String, String> mods = Mods.getMods(player);

        if (mods == null)
        {
            sender.sendMessage(MessageEngine.get("error.mods.noDataFound"));
            return true;
        }

        if (sender instanceof ConsoleCommandSender)
        {
            mods.keySet().parallelStream().forEach(id -> sender.sendMessage(ChatColor.RED + id + ChatColor.GREEN + ": " + ChatColor.BLUE + mods.get(id)));
            return true;
        }

        BookUtil.openBook(Books.getModsBook(player, mods), (Player) sender);
        return true;
    }
}
