package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.BanManager;
import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import ml.peya.plugins.Utils.PlayerUtils;
import ml.peya.plugins.Utils.TimeParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ml.peya.plugins.Utils.MessageEngine.get;

/**
 * Tempban系。
 * /tempban で動く。
 */
public class CommandTempBan implements CommandExecutor
{
    /**
     * コマンド動作のオーバーライド。
     *
     * @param sender イベントsender。
     * @param cmd    コマンド。
     * @param label  ラベル。
     * @param args   引数。
     * @return 正常に終わったかどうか。
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psac.tempban"))
            return true;
        if (args.length < 2)
        {
            sender.sendMessage(get("error.invalidArgument"));
            return true;
        }

        String regex = "^[0-9]+((year|y)|(month|mo)|(day|d)|(hour|h)|(minute|min|m)|(second|sec|s))(s)?$";

        new BukkitRunnable()
        {
            @Override
            public void run()
            {

                ArrayList<String> ex = new ArrayList<>(Arrays.asList(args));

                Player pl = null;

                Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

                StringBuilder reason = new StringBuilder();

                for (String arg : ex)
                {
                    Player player = PlayerUtils.getPlayerAllowOffline(arg);
                    if (player != null)
                    {
                        if (pl == null)
                            pl = player;
                        continue;
                    }

                    Matcher m = p.matcher(arg);
                    if (m.find())
                        continue;

                    reason.append(arg).append(" ");
                }

                if (pl == null)
                {
                    sender.sendMessage(get("error.playerNotFound"));
                    return;
                }

                Date date = TimeParser.convert(ex.toArray(new String[0]));

                BanManager.ban(pl, reason.toString(), date);

            }
        }.runTask(PeyangSuperbAntiCheat.getPlugin());

        return true;
    }
}











