package ml.peya.plugins.Commands;

import ml.peya.plugins.Commands.CmdPub.Kick;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import static ml.peya.plugins.Variables.config;

public class VanillaCommands implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(PlayerCommandPreprocessEvent e)
    {
        if (e.isCancelled())
            return;

        String[] args = e.getMessage().split(" ");
        String label = args[0].replaceFirst("/", "");

        switch (label)
        {
            case "kick":
                if (!config.getBoolean("commands.override.kick"))
                    return;
                Kick.run(e.getPlayer(), args);
                e.setCancelled(true);
                break;
            default:
                return;
        }

    }

}
