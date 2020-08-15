package ml.peya.plugins.BungeeProxy;

import com.google.common.io.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.event.*;

import static ml.peya.plugins.Bukkit.Variables.*;

/**
 * Bungeecordプロキシのサーバーから受け取るデータに基づくイベントを管理します。
 */
public class Events implements Listener
{
    /**
     * プラグインメッセージをサーバーから受け取った時の処理。
     *
     * @param e イベントハンドラ引数。
     */
    @EventHandler
    public void onPluginMessage(PluginMessageEvent e)
    {
        if (!e.getTag().equals(bungeeChannel) || !(e.getSender() instanceof Server))
            return;

        bungeeCommand.runCommand(ByteStreams.newDataInput(e.getData()).readUTF(), ((Server) e.getSender()).getInfo().getName());
    }

    /*@EventHandler
    public void onServerDisconnect(ServerDisconnectEvent e)
    {
        PeyangSuperbAntiCheatProxy.servers.remove(e.getTarget().getName());
        Variables.logger.info("<-> " + e.getTarget().getName() + " has disconnected");
    }*/
}
