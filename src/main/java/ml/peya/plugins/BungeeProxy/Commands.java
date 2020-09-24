package ml.peya.plugins.BungeeProxy;

import ml.peya.plugins.BungeeStructure.*;
import net.md_5.bungee.api.*;

import java.util.*;

import static ml.peya.plugins.Variables.logger;

/**
 * Bungee関係のコマンドのやつ(Bungee側)
 */
public class Commands implements CommandExecutor
{
    /**
     * pingコマンドを受け取ったときのやつ
     *
     * @param cmd コマンド
     */
    @Command(label = "ping")
    public void ping(CommandComponent cmd)
    {
        logger.info("<-> Pinged from Server [" + cmd.getServer() + "]");
        PeyangSuperbAntiCheatProxy.sendData(ProxyServer.getInstance().getServerInfo(cmd.getServer()), "pong");
    }

    /**
     * pongコマンドを受け取ったときのやつ
     *
     * @param cmd コマンド
     */
    @Command(label = "pong")
    public void pong(CommandComponent cmd)
    {
        PeyangSuperbAntiCheatProxy.servers.add(cmd.getServer());
        logger.info("<-> Server [" + cmd.getServer() + "] has connected");
    }

    /**
     * reportコマンドを受け取ったときのやつ
     * コマンドを解析し、サーバ内のスタッフにブロードキャストする。
     * SQLの設定が不十分でも、最低限実行される。
     *
     * @param cmd コマンド
     */
    @Command(label = "report")
    public void report(CommandComponent cmd)
    {
        String[] args = cmd.getArgs();

        if (args.length > 2)
            return;
        String id = args[0];
        String player = args[1];

        logger.info("Reported Player [" + player + "](id=" + id + ")");

        PeyangSuperbAntiCheatProxy.servers.parallelStream()
                .filter(serverName -> !cmd.getServer()
                        .equals(serverName))
                .map(serverName -> PeyangSuperbAntiCheatProxy.getPlugin()
                        .getProxy()
                        .getServerInfo(serverName))
                .filter(Objects::nonNull)
                .forEachOrdered(server -> PeyangSuperbAntiCheatProxy.sendData(server, "report " + id + " " + player));
    }
}
