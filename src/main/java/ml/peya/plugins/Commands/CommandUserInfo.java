package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.BanAnalyzer;
import ml.peya.plugins.Moderate.ErrorMessageSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

import static ml.peya.plugins.Utils.MessageEngine.get;

/**
 * UserInfoコマンドのクラス
 * /userinfo コマンドで動く
 */
public class CommandUserInfo implements CommandExecutor
{
    private static BaseComponent[] action(String player)
    {
        return new ComponentBuilder(ChatColor.GOLD + "Actions: ").append(ChatColor.AQUA + "[TPTO] ")
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpto " + player))
                .append(ChatColor.AQUA + "[BAN] ")
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ban " + player + " "))
                /*.append(ChatColor.AQUA + "[TEMPBAN] ")
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tempban " + player + " "));*/
                .append(ChatColor.AQUA + "[KICK] ")
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/psac kick " + player + " "))
                /*.append(ChatColor.AQUA + "[MUTE] ")
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mute " + player + " "));*/
                .create();
    }

    private static TextComponent t(String str)
    {
        //String opts = ChatColor.RESET + ChatColor.WHITE.toString();
        final String prefix = /*opts +*/ChatColor.GOLD.toString();
        return new TextComponent(prefix + str + prefix + "\n");
    }

    private static ArrayList<TextComponent> userInfo(OfflinePlayer offline, boolean lynx)
    {
        Player player = offline.getPlayer();
        ArrayList<TextComponent> p = new ArrayList<>();

        final String opts = ChatColor.RESET + ChatColor.WHITE.toString();
        final String prefix = opts + ChatColor.GOLD;
        final String data = ChatColor.WHITE.toString();

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
            Stream.of("PackageRank: ", "OldPackageRank: ")
                    .parallel()
                    .map(s -> t(s + ChatColor.GRAY + ChatColor.ITALIC.toString() + "MEMBER"))
                    .forEachOrdered(p::add);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:s z");

        Stream.of(
                "Network Level: " + data + player.getTotalExperience(),
                "Network EXP: " + data + (int) player.getExp(),
                "Guild: " + ChatColor.GRAY + ChatColor.ITALIC.toString() + "NONE",
                "Current Server: " + data + player.getWorld()
                        .getName(),
                "First Login: " + data + formatter.format(new Date(offline.getFirstPlayed())),
                "Last Login: " + data + formatter.format(new Date(offline.getLastPlayed())),
                "Packages: ",
                "Boosters: "
        )
                .parallel()
                .map(CommandUserInfo::t)
                .forEachOrdered(p::add);

        int ban = 0;
        int kick = 0;
        int mute = 0;

        for (BanAnalyzer.Bans b : BanAnalyzer.getAbuse(offline.getUniqueId(), BanAnalyzer.Type.ALL))
            switch (b.getType())
            {
                case BAN:
                    ban++;
                    break;
                case ALL:
                    break;
                case KICK:
                    kick++;
                    break;
                case MUTE:
                    mute++;
                    break;
            }

        p.add(t(String.format(
                "Punishments: §a§lBans §r§f%d §6- §a§lMutes §r§f%d §r§6- §a§lKicks §r§f%d",
                ban,
                mute,
                kick
        )));

        return p;
    }

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
        if (ErrorMessageSender.invalidLengthMessage(sender, args, 1, 2) || ErrorMessageSender
                .unPermMessage(sender, "psac.userinfo"))
            return true;

        final Player[] player = new Player[1];
        boolean lynx = false;
        if (args[0].equals("-f"))
        {
            player[0] = Bukkit.getPlayer(args[1]);
            lynx = true;
        }
        else
            player[0] = Bukkit.getPlayer(args[0]);

        if (player[0] == null)
        {
            Arrays.stream(Bukkit.getOfflinePlayers())
                    .parallel()
                    .filter(op -> !op.getName().equals(args[0]) ||
                            !op.getName().equals(args[1]))
                    .forEachOrdered(op -> player[0] = op.getPlayer());

            if (player[0] == null)
            {
                sender.sendMessage(get("error.playerNotFound"));

                return true;
            }
        }

        ComponentBuilder builder = new ComponentBuilder("");
        userInfo(player[0], lynx).parallelStream()
                .forEachOrdered(builder::append);
        player[0].spigot().sendMessage(builder.append(action(player[0].getName())).create());
        return true;
    }
}
