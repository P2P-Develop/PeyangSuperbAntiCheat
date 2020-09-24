package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Moderate.Mods;
import ml.peya.plugins.Utils.BookUtil;
import ml.peya.plugins.Utils.Books;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static ml.peya.plugins.Utils.MessageEngine.get;

/**
 * Mod参照コマンド系クラス。
 * /mod コマンドで動く
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
     *
     * @return 正常に終わったかどうか。
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psac.mods") || ErrorMessageSender
                .invalidLengthMessage(sender, args, 1, 1))
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
            mods.keySet()
                .parallelStream()
                .forEachOrdered(id -> sender
                        .sendMessage(ChatColor.RED + id + ChatColor.GREEN + ": " + ChatColor.BLUE + mods.get(id)));
            return true;
        }

        BookUtil.openBook(Books.getModsBook(player, mods), (Player) sender);
        return true;
    }
}
