package ml.peya.plugins.Bukkit.Commands;

import ml.peya.plugins.Bukkit.Moderate.*;
import ml.peya.plugins.Bukkit.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

import static ml.peya.plugins.Bukkit.Utils.MessageEngine.get;

/**
 * Mod参照コマンド系クラス。
 */
public class CommandMods implements CommandExecutor
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
        if (ErrorMessageSender.unPermMessage(sender, "psac.mods") || ErrorMessageSender.invalidLengthMessage(sender, args, 1, 1))
            return true;

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage(get("error.playerNotFound"));
            return true;
        }

        HashMap<String, String> mods = Mods.getMods(player);

        if (mods == null)
        {
            sender.sendMessage(get("error.mods.noDataFound"));
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
