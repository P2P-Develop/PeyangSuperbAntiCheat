package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.util.*;

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
            if (getPlayerNodes().contains(args[0]))
                sender.sendMessage(get("command.help." + args[0]));
            else
                sender.sendMessage(get("error.psac.notPage"));

            if (args[0].equals("show") || args[0].equals("drop"))
                sender.sendMessage(get("command.help.mngIdWarning"));

            return;
        }

        ArrayList<String> nodes = sender instanceof Player ? getPlayerNodes(): getNodes();
        new BukkitRunnable()
        {
            @Override
            public void run()
            {

                nodes.parallelStream()
                        .filter(node -> sender.hasPermission("psac." + node))
                        .forEachOrdered(str -> {
                            String msg = get("command.shelp." + str, pair("label", label));
                            BaseComponent[] st = new ComponentBuilder(msg)
                                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psac help " + str))
                                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.AQUA + "詳細").create()))
                                    .create();
                            sender.spigot().sendMessage(st);
                        });
            }
        }.runTaskAsynchronously(PeyangSuperbAntiCheat.getPlugin());

    }

    private static ArrayList<String> getNodes()
    {
        ArrayList<String> nodes = new ArrayList<>();
        nodes.add("report");
        nodes.add("view");
        nodes.add("aurapanic");
        nodes.add("aurabot");
        nodes.add("show");
        nodes.add("drop");
        nodes.add("kick");
        nodes.add("bans");
        nodes.add("testkb");
        return nodes;
    }

    private static ArrayList<String> getPlayerNodes()
    {
        ArrayList<String> nodes = getNodes();
        nodes.add("pull");
        nodes.add("target");
        nodes.add("silentteleport");
        nodes.add("tracking");
        return nodes;
    }
}
