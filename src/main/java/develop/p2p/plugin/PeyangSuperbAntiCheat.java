package develop.p2p.plugin;

import develop.p2p.plugin.Commands.*;
import develop.p2p.plugin.Gui.*;
import org.bukkit.*;
import org.bukkit.plugin.java.*;

import java.util.logging.*;

public class PeyangSuperbAntiCheat extends JavaPlugin
{
    public static Logger logger= Bukkit.getServer().getLogger();
    private static PeyangSuperbAntiCheat plugin;
    @Override
    public void onEnable()
    {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null)
        {
            logger.log(Level.SEVERE, "PeyangSuperbAntiCheat has Require ProtocolLib");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        plugin = this;
        getCommand("report").setExecutor(new CommandReport());
        Book.init();
        logger.info("PeyangSuperbAntiCheat has started!");
    }

    public static PeyangSuperbAntiCheat getPlugin()
    {
        return plugin;
    }

}
