package ml.peya.plugins.Commands;

import ml.peya.plugins.Moderate.ErrorMessageSender;
import ml.peya.plugins.Moderate.TrustModifier;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;
import static ml.peya.plugins.Variables.config;

/**
 * 引き寄せコマンド系クラス。
 * /pull コマンドで動く
 */
public class CommandPull implements CommandExecutor
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
        if (ErrorMessageSender.unPermMessage(sender, "psac.pull") || ErrorMessageSender.invalidLengthMessage(sender, args, 1, 1))
            return true;

        if (!(sender instanceof Player))
        {
            sender.sendMessage(get("error.requirePlayer"));
            return true;
        }

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

        final Player playerSender = (Player) sender;

        if (!playerSender.getWorld().getName().equals(player.getWorld().getName()))
            player.teleport(new Location(playerSender.getWorld(), playerSender.getLocation().getX(), playerSender.getLocation().getY(), playerSender.getLocation().getZ()));
        else
            pull(player, playerSender.getLocation());

        sender.sendMessage(config.getBoolean("message.lynx") ? get("message.pull.lynx", pair("name", player.getName())): get("message.pull.normal", pair("name", player.getName())));

        return true;
    }

    /**
     * 思いっきりプレイヤーを引き寄せる。
     *
     * @param player       プレイヤー。
     * @param pullLocation 引き寄せ先。
     */
    private void pull(Player player, Location pullLocation)
    {
        Location entityLoc = player.getLocation();
        entityLoc.setY(entityLoc.getY() + 0.5d);
        player.teleport(entityLoc);
        final double distance = pullLocation.distance(entityLoc);
        Vector vector = player.getVelocity();
        vector.setX(((1.0D +
                (0.1d * distance)) *
                (pullLocation.getX() - entityLoc.getX())) / distance)
                .setY((((1.0D +
                        (0.03d * distance)) *
                        (pullLocation.getY() - entityLoc.getY())) / distance) -
                        ((0.5D * -0.08D) * distance))
                .setZ(((1.0D +
                        (0.1D * distance)) *
                        (pullLocation.getZ() - entityLoc.getZ())) / distance);
        player.setVelocity(vector);
    }

}
