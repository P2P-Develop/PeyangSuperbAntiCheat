package ml.peya.plugins.BungeeProxy;

import com.google.common.io.*;
import net.md_5.bungee.api.config.*;
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

    @EventHandler
    public void onServerDisconnect(ServerDisconnectEvent e)
    {
        if (e.getTarget().getPlayers().size() != 0)
            return;
        PeyangSuperbAntiCheatProxy.servers.remove(e.getTarget().getName());
        logger.info("<-> " + e.getTarget().getName() + " has disconnected");
    }

    @EventHandler
    public void onServerConnecteD(ServerConnectedEvent e)
    {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF(bungeeChannel);

        out.writeUTF("ping");

        ServerInfo info = e.getServer().getInfo();
        logger.info("<-> " + info.getName() + " pinging...");
        info.sendData(bungeeChannel, out.toByteArray());

    }
}
