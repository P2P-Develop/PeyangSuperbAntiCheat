package ml.peya.plugins.Commands;

import com.comphenix.protocol.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.util.*;

public class CommandBans implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!Permission.unPermMessage(sender, "psr.bans"))
            return true;

        if (args.length < 1)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：引数の数が不正です。/psr help でヘルプを見てください。");
            return true;
        }

        String type;
        String name;
        if (args.length == 2)
        {
            type = args[0];
            name = args[1];
        }
        else
        {
            type = "-a";
            name = args[0];
        }

        if (!type.equals("-a") && !type.toLowerCase().equals("ban") && !type.toLowerCase().equals("kick"))
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：検索のタイプが不明です。");
            return true;
        }


        BanAnalyzer.Type typeP = BanAnalyzer.Type.toType(type);

        UUID player = null;

        for (OfflinePlayer ofPly: Bukkit.getOfflinePlayers())
            if (ofPly.getName().toLowerCase().equals(name.toLowerCase()))
                player = ofPly.getUniqueId();

        if (player == null)
            for (Player onPly: Bukkit.getOnlinePlayers())
                if (onPly.getName().toLowerCase().equals(name.toLowerCase()))
                    player = onPly.getUniqueId();

        if (player == null)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：プレイヤーが見つかりませんでした。");
            return true;
        }

        ArrayList<BanAnalyzer.Bans> bans = BanAnalyzer.getAbuse(player, typeP);

        for (BanAnalyzer.Bans ban: bans)
            sender.spigot().sendMessage(TextBuilder.getTextBan(ban, ban.getType()).create());
        if (bans.size() == 0)
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：データベースに情報が見つかりませんでした。");
        return true;
    }
}
