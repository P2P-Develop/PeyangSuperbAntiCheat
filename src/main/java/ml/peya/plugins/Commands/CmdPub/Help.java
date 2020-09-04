package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.*;
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
     */
    public static void run(CommandSender sender, String label)
    {
        final boolean[] flag = {false};
        sender.sendMessage(get("base.prefix"));

        ArrayList<String> nodes = sender instanceof Player ? getPlayerNodes(): getNodes();

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                nodes.parallelStream().filter(node -> sender.hasPermission("psac." + node)).forEachOrdered(node -> {
                    sender.sendMessage(get("command.help." + node, pair("label", label)));
                    flag[0] = true;
                });

                if ((sender.hasPermission("psac.drop") || sender.hasPermission("psac.show")) && sender instanceof Player)
                    sender.sendMessage(get("command.help.mngIdWarning"));

                if (!flag[0])
                    sender.sendMessage(get("error.psac.notPage"));
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
