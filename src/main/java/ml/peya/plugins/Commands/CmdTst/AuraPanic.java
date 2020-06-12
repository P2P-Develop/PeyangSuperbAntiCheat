package ml.peya.plugins.Commands.CmdTst;

import ml.peya.plugins.*;
import ml.peya.plugins.Detect.*;
import ml.peya.plugins.Enum.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class AuraPanic implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(args.length == 1 || args.length == 2))
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：引数の数が不正です。/psr help でヘルプを見てください。");
            return true;
        }

        int sec = 5;

        if (args.length == 2)
        {
            try
            {
                sec = Integer.parseInt(args[1]);
            }
            catch (Exception e)
            {
                sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：指定された秒数が数字ではありません。/psr help でヘルプを見てください。");
                return true;
            }
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：対象プレイヤーが見つかりませんでした！");
            return true;
        }

        String name = player.getDisplayName() + (player.getDisplayName().equals(player.getName()) ? "": (" (" + player.getName() + ") "));

        if(PeyangSuperbAntiCheat.cheatMeta.exists(player.getUniqueId()))
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：対象プレイヤーはテスト中です。");
            return true;
        }

        sender.sendMessage(ChatColor.GREEN + name + "さんにAuraPanicBotを召喚します。");
        sender.sendMessage(ChatColor.GREEN + String.valueOf(sec) + "秒間お待ちください。");

        DetectType type = DetectType.AURA_PANIC;
        type.setPanicTime(sec);

        NPCConnection.scan(player, type, sender);
        return true;
    }
}
