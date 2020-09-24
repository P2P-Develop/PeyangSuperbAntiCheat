package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.PeyangSuperbAntiCheat;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;

/**
 * /psac helpで動くクラス。
 */
public class Help
{
    /**
     * コマンド
     *
     * @param sender イベントsender。
     * @param label  参照するコマンドラベル。
     * @param args   スタートとか(適当
     */
    public static void run(CommandSender sender, String label, String[] args)
    {
        sender.sendMessage(get("base.prefix"));

        if (args.length != 0)
        {
            sender.sendMessage(getPlayerNodes().contains(args[0])
                    ? get("command.help." + args[0])
                    : get("error.psac.notPage"));

            if (args[0].equals("show") || args[0].equals("drop"))
                sender.sendMessage(get("command.help.mngIdWarning"));

            return;
        }

        ArrayList<String> nodes = sender instanceof Player
                ? getPlayerNodes()
                : getNodes();
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                nodes.parallelStream()
                     .filter(node -> sender.hasPermission("psac." + node))
                     .forEachOrdered(str ->
                             sender.spigot()
                                   .sendMessage(new ComponentBuilder(get("command.shelp." + str, pair("label", label)))
                                           .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psac help " + str))
                                           .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.AQUA + "詳細")
                                                   .create()))
                                           .create()));
            }
        }.runTaskAsynchronously(PeyangSuperbAntiCheat.getPlugin());
    }

    private static ArrayList<String> getNodes()
    {
        return new ArrayList<>(Arrays.asList("report", "view", "aurapanic", "aurabot", "show", "drop", "kick", "bans", "testkb"));
    }

    private static ArrayList<String> getPlayerNodes()
    {
        return new ArrayList<>(Arrays.asList("pull", "target", "silentteleport", "tracking"));
    }
}
