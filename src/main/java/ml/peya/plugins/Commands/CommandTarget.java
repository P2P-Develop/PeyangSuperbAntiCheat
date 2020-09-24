package ml.peya.plugins.Commands;

import ml.peya.plugins.Gui.GuiItem;
import ml.peya.plugins.Gui.IItems;
import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Moderate.TrustModifier;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.tracker;

/**
 * ターゲティングコマンド系クラス。
 * /target コマンドで動く
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
            sender.sendMessage(get("error.requirePlayer"));
            return true;
        }

        if (ErrorMessageSender.invalidLengthMessage(sender, args, 1, 2) || ErrorMessageSender.unPermMessage(sender, "psac.target"))
            return true;

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(get("error.playerNotFound"));

            return true;
        }

        if (TrustModifier.isTrusted(player) && !player.hasPermission("psac.trust"))
        {
            sender.sendMessage(get("error.trusted"));

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

        tracker.add(sender.getName(), args[0]);

        GuiItem.giveAllItems((Player) sender, IItems.Type.TARGET, player.getName());

        return true;
    }
}
