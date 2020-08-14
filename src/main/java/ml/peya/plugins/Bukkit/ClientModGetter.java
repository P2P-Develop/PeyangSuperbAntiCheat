package ml.peya.plugins.Bukkit;

import org.bukkit.entity.*;
import org.bukkit.plugin.messaging.*;

import java.util.*;

/**
 * プラグインメッセージのオーバーライドを実装します。
 * イベントを改変してます。
 */
public class ClientModGetter implements PluginMessageListener
{
    /**
     * プラグインのメッセージを取得した際のイベントをオーバーライドします。
     *
     * @param channel どのコンソール・ウィンドウ・ダイアログで取得したか。
     * @param player  誰が発信したのか。
     * @param data    バイト配列の本文データ。
     */
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
            if (store)
                mods.put(tempName, new String(Arrays.copyOfRange(data, i + 1, i + data[i] + 1)));
            else
                tempName = new String(Arrays.copyOfRange(data, i + 1, i + data[i] + 1));

            i += data[i] + 1;
        }

        Variables.mods.put(player.getUniqueId(), mods);
    }
}
