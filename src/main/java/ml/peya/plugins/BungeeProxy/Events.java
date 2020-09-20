package ml.peya.plugins.BungeeProxy;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static ml.peya.plugins.Variables.*;

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

    /**
     * プレイヤーが接続を切ったときのやつ。
     *
     * @param e イベントハンドラ引数。
     */
    @EventHandler
    public void onServerDisconnect(ServerDisconnectEvent e)
    {
        if (e.getTarget().getPlayers().size() != 0) //プレイヤー人数が0人だったら開放
            return;
        PeyangSuperbAntiCheatProxy.servers.remove(e.getTarget().getName());
        logger.info("<-> Server [" + e.getTarget().getName() + "] has disconnected");
    }

    /**
     * プレイヤーが接続を<b>完了</b>したときのやつ
     *
     * @param e イベントハンドラ引数。
     */
    @EventHandler
    public void onServerConnecteD(ServerConnectedEvent e)
    {
        if (PeyangSuperbAntiCheatProxy.servers.contains(e.getServer().getInfo().getName())) //サーバがすでに登録済みの場合はreturn
            return;
        ServerInfo info = e.getServer().getInfo();
        logger.info("<-> Server [" + info.getName() + "] pinging...");
        PeyangSuperbAntiCheatProxy.sendData(info, "ping");
    }
}
