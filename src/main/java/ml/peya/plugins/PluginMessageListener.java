package ml.peya.plugins;

import org.bukkit.entity.*;

import java.util.*;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener
{

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data) {
        if(player == null || !player.isOnline())
            return;

        if(channel.equals("FML|HS"))
        {
            HashMap<String, String> mods = new HashMap<>();
            boolean store = false;
            String tempName = null;

            for(int i = 2; i < data.length; store = !store)
            {
                int end = i + data[i] + 1;
                byte[] range = Arrays.copyOfRange(data, i + 1, end);
                String mod = new String(range);

                if(store)
                    mods.put(tempName, mod);
                else
                    tempName = mod;

                i = end;
            }

            PeyangSuperbAntiCheat.mods.put(player.getUniqueId(), mods);
        }
    }
}
