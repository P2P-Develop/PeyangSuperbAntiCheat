package ml.peya.plugins.Commands;

import ml.peya.plugins.*;
import ml.peya.plugins.Moderate.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.util.*;

public class CommandPull implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psac.pull") || ErrorMessageSender.invalidLengthMessage(sender, args, 1, 1))
            return true;

        if (!(sender instanceof Player))
        {
            sender.sendMessage(MessageEngine.get("error.requirePlayer"));
            return true;
        }

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

        Player playerSender = (Player) sender;

        if (!playerSender.getWorld().getName().equals(player.getWorld().getName()))
            player.teleport(new Location(playerSender.getWorld(), playerSender.getLocation().getX(), playerSender.getLocation().getY(), playerSender.getLocation().getZ()));
        else
            pull(player, playerSender.getLocation());

        sender.sendMessage(PeyangSuperbAntiCheat.config.getBoolean("message.lynx") ? MessageEngine.get("message.pull.lynx", MessageEngine.hsh("name", player.getName())): MessageEngine.get("message.pull.normal", MessageEngine.hsh("name", player.getName())));

        return true;
    }

    private void pull(Player player, Location pullLocation)
    {
        Location entityLoc = player.getLocation();
        entityLoc.setY(entityLoc.getY() + 0.5d);
        player.teleport(entityLoc);
        double distance = pullLocation.distance(entityLoc);
        double x = ((1.0D +
                (0.1d * distance)) *
                (pullLocation.getX() -
                        entityLoc.getX())) /
                distance;
        double y = (((1.0D +
                (0.03d *
                        distance)) *
                (pullLocation.getY() -
                        entityLoc.getY())) /
                distance) -
                ((0.5D * -0.08D) *
                        distance);
        Vector vector = player.getVelocity();
        vector.setX(x);
        vector.setY(y);
        vector.setZ(((1.0D +
                (0.1D * distance)) *
                (pullLocation.getZ() -
                        entityLoc.getZ())) / distance);
        player.setVelocity(vector);
    }

}
