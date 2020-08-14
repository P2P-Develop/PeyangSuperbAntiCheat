package ml.peya.plugins.Bukkit.Commands;

import ml.peya.plugins.Bukkit.Gui.*;
import ml.peya.plugins.Bukkit.Moderate.*;
import ml.peya.plugins.Bukkit.*;
import ml.peya.plugins.Bukkit.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

/**
 * ターゲティングコマンド系クラス。
 */
public class CommandTarget implements CommandExecutor
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
        if (!(sender instanceof Player))
        {
            sender.sendMessage(MessageEngine.get("error.requirePlayer"));
            return true;
        }

        if (ErrorMessageSender.invalidLengthMessage(sender, args, 1, 2) || ErrorMessageSender.unPermMessage(sender, "psac.target"))
            return true;

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(MessageEngine.get("error.playerNotFound"));

            return true;
        }

        if (TrustModifier.isTrusted(player) && !player.hasPermission("psac.trust"))
        {
            sender.sendMessage(MessageEngine.get("error.trusted"));

            return true;
        }

        if (args.length >= 2)
        {
            switch (args[2])
            {
                case "1":
                    GuiItem.giveAllItems((Player) sender, IItems.Type.TARGET, player.getName());
                    break;
                case "2":
                    GuiItem.giveAllItems((Player) sender, IItems.Type.TARGET_2, player.getName());
                    break;
                default:
                    ErrorMessageSender.invalidLengthMessage(sender, args, 1, 1);
                    break;
            }
            return true;
        }

        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                ((Player) sender).performCommand("bans -a " + player.getName());
                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 15L);

        Variables.tracker.add(sender.getName(), args[0]);

        GuiItem.giveAllItems((Player) sender, IItems.Type.TARGET, player.getName());

        return true;
    }
}
