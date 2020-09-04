package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.text.*;
import java.util.*;

import static ml.peya.plugins.Utils.MessageEngine.get;

public class CommandUserInfo implements CommandExecutor
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
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 1, 2) || ErrorMessageSender.unPermMessage(sender, "psac.userinfo"))
            return true;

        Player player;
        boolean lynx = false;
        if (args[0].equals("-f"))
        {
            player = Bukkit.getPlayer(args[1]);
            lynx = true;
        }
        else
            player = Bukkit.getPlayer(args[0]);

        if (player == null)
        {
            for (OfflinePlayer op : Bukkit.getOfflinePlayers())
            {
                if (!op.getName().equals(args[0]) || !op.getName().equals(args[1]))
                    continue;
                player = op.getPlayer();
            }

            if (player == null)
            {
                sender.sendMessage(get("error.playerNotFound"));

                return true;
            }
        }

        ComponentBuilder builder = new ComponentBuilder("");
        for (TextComponent component : userInfo(player, lynx))
            builder.append(component);
        builder.append(action(player.getName()));
        player.spigot().sendMessage(builder.create());
        return true;
    }

    private static BaseComponent[] action(String player)
    {
        ComponentBuilder builder = new ComponentBuilder(ChatColor.GOLD + "Actions: ");

        builder.append(ChatColor.AQUA + "[TPTO] ")
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpto " + player));
        builder.append(ChatColor.AQUA + "[BAN] ")
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ban " + player + " "));
        /*builder.append(ChatColor.AQUA + "[TEMPBAN] ")
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tempban " + player + " "));*/
        builder.append(ChatColor.AQUA + "[KICK] ")
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/psac kick " + player + " "));
        /*builder.append(ChatColor.AQUA + "[MUTE] ")
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mute " + player + " "));*/
        return builder.create();
    }

    private static TextComponent t(String str)
    {
        //String opts = ChatColor.RESET + ChatColor.WHITE.toString();
        String prefix = /*opts +*/ChatColor.GOLD.toString();
        return new TextComponent(prefix + str + prefix + "\n");
    }

    private static ArrayList<TextComponent> userInfo(OfflinePlayer offline, boolean lynx)
    {
        Player player = offline.getPlayer();
        ArrayList<TextComponent> p = new ArrayList<>();

        String opts = ChatColor.RESET + ChatColor.WHITE.toString();
        String prefix = opts + ChatColor.GOLD;
        String data = ChatColor.WHITE.toString();

        p.add(new TextComponent(ChatColor.RESET + ChatColor.WHITE.toString() +
                prefix +
                "--- Info about " +
                player.getName() +
                prefix +
                " ---" +
                opts +
                prefix +
                "\n"));
        if (lynx)
            p.add(t("Most Recent Name: " + data + player.getName()));
        p.add(t("UUID: " + data + player.getUniqueId().toString()));
        String rank;
        if (player.hasPermission("psac.admin"))
            rank = ChatColor.RED + "ADMIN";
        else if (player.hasPermission("psac.mod"))
            rank = ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "MOD";
        else
            rank = ChatColor.GRAY + ChatColor.ITALIC.toString() + "MEMBER";
        p.add(t("Rank: " + rank));
        if (lynx)
        {
            p.add(t("PackageRank: " + ChatColor.GRAY + ChatColor.ITALIC.toString() + "MEMBER"));
            p.add(t("OldPackageRank: " + ChatColor.GRAY + ChatColor.ITALIC.toString() + "MEMBER"));
        }

        p.add(t("Network Level: " + data + player.getTotalExperience()));
        p.add(t("Network EXP: " + data + (int) player.getExp()));
        p.add(t("Guild: " + ChatColor.GRAY + ChatColor.ITALIC.toString() + "NONE"));
        p.add(t("Current Server: " + data + player.getWorld().getName()));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:s z");
        p.add(t("First Login: " + data + formatter.format(new Date(offline.getFirstPlayed()))));
        p.add(t("Last Login: " + data + formatter.format(new Date(offline.getLastPlayed()))));

        p.add(t("Packages: "));
        p.add(t("Boosters: "));

        ArrayList<BanAnalyzer.Bans> bans = BanAnalyzer.getAbuse(offline.getUniqueId(), BanAnalyzer.Type.ALL);

        int ban = 0;
        int kick = 0;
        int mute = 0;

        for (BanAnalyzer.Bans b : bans)
        {
            switch (b.getType())
            {
                case BAN:
                    ban++;
                    break;
                case KICK:
                    kick++;
                    break;
                case MUTE:
                    mute++;
                    break;
            }
        }


        p.add(t(String.format(
                "Punishments: §a§lBans §r§f%d §6- §a§lMutes §r§f%d §r§6- §a§lKicks §r§f%d",
                ban,
                mute,
                kick
        )));

        return p;
    }
}
