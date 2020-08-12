package ml.peya.plugins.BungeeProxy;

import ml.peya.plugins.*;
import net.md_5.bungee.api.plugin.*;

public class PeyangSuperbAntiCheatProxy extends Plugin
{
    private static final int __BSTATS_PLUGIN_ID = 8084;

    @Override
    public void onEnable()
    {
        Metrics metrics = new Metrics(this, __BSTATS_PLUGIN_ID);
        Variables.logger = getLogger();
        getLogger().info("PeyangSuperbAntiCheat has been activated!");
    }
}
