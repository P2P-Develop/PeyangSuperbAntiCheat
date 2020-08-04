package ml.peya.plugins;

import org.bukkit.entity.*;

import java.util.*;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener
{

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data)
    {
        if (player == null || !player.isOnline())
            return;

        if (!channel.equals("FML|HS"))
            return;

        HashMap<String, String> mods = new HashMap<>();
        boolean store = false;
        String tempName = null;

        for (int i = 2; i < data.length; store = !store)
        {
            String mod = new String(Arrays.copyOfRange(data, i + 1, i + data[i] + 1));

            if (store)
                mods.put(tempName, mod);
            else
                tempName = mod;

            i = i + data[i] + 1;
        }

        PeyangSuperbAntiCheat.mods.put(player.getUniqueId(), mods);
    }
}
