package ml.peya.plugins.Commands;

import ml.peya.plugins.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Moderate.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

public class CommandTarget implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(MessageEngihe.get("error.requirePlayer"));
            return true;
        }

        if (ErrorMessageSender.invalidLengthMessage(sender, args, 1, 2))
            return true;
        if (ErrorMessageSender.unPermMessage(sender, "psac.target"))
            return true;

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(MessageEngihe.get("error.playerNotFound"));
            return true;
        }

        PeyangSuperbAntiCheat.tracker.add(sender.getName(), args[0]);

        if (args.length >= 2)
        {
            if (args[1].equals("1"))
                GuiItem.giveAllItems((Player) sender, IItems.Type.TARGET, player.getName());
            else if (args[1].equals("2"))
                GuiItem.giveAllItems((Player) sender, IItems.Type.TARGET_2, player.getName());
            ErrorMessageSender.invalidLengthMessage(sender, args, 1, 1);
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

        GuiItem.giveAllItems((Player) sender, IItems.Type.TARGET, player.getName());

        return true;
    }
}