package ml.peya.plugins.Commands.CmdTst;

import ml.peya.plugins.Detect.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

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
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：対象プレイヤーが見つかりませんでした！");
            return true;
        }

        String name = player.getDisplayName() + (player.getDisplayName().equals(player.getName()) ? "": (" (" + player.getName() + ") "));

        if(PeyangSuperbAntiCheat.cheatMeta.exists(player.getUniqueId()))
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：対象プレイヤーはテスト中です。");
            return true;
        }

        sender.sendMessage(ChatColor.GREEN + name + "さんにAuraBotを召喚します。");
        sender.sendMessage(ChatColor.GREEN + PeyangSuperbAntiCheat.config.getString("npc.seconds") + "秒間お待ちください。");
        NPCConnection.scan(player, DetectType.AURA_BOT, sender);
        return true;
    }
}
