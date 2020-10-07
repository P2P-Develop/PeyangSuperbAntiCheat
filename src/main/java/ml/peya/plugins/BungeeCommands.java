package ml.peya.plugins;

import ml.peya.plugins.BungeeStructure.Command;
import ml.peya.plugins.BungeeStructure.CommandComponent;
import ml.peya.plugins.BungeeStructure.CommandExecutor;
import ml.peya.plugins.Moderate.CheatTypeUtils;
import ml.peya.plugins.Utils.Utils;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * バンジーのコマンド
 */
public class BungeeCommands implements CommandExecutor
{
    /**
     * pingコマンドを受け取ったとき
     *
     * @param command コマンド
     */
    @Command(label = "ping")
    public static void ping(CommandComponent command)
    {
        Bungee.sendMessage("pong");
        Variables.logger.info("<-> [BungeeCord] connected.");
        Variables.bungeeCord = true;
    }

    /**
     * BungeeCordがDisconnectしたとき
     *
     * @param command コマンド
     */
    @Command(label = "dc")
    public static void disconnect(CommandComponent command)
    {
        Variables.logger.info("<-> [BungeeCord] has disconnected.");
        Variables.bungeeCord = false;
    }

    /**
     * pongコマンドを受け取ったとき
     *
     * @param command コマンド
     */
    @Command(label = "pong")
    public static void pong(CommandComponent command)
    {
        Variables.bungeeCord = true;
    }

    /**
     * reportコマンドを受け取ったとき
     *
     * @param command コマンド
     */
    @Command(label = "report")
    public static void report(CommandComponent command)
    {
        String[] args = command.getArgs();

        if (args.length < 1)
            return;

        String player = "Unknown";
        String id = args[0];

        if (args.length == 2)
            player = args[1];

        final String finalPlayer = player;
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                ArrayList<String> reasons = new ArrayList<>();
                try (Connection connection = Variables.eye.getConnection();
                     PreparedStatement statement = connection.prepareStatement("SELECT REASON FROM watchreason WHERE MNGID=?"))
                {
                    statement.setString(1, id);
                    ResultSet result = statement.executeQuery();
                    while (result.next())
                        reasons.add(CheatTypeUtils.getCheatTypeFromString(result.getString("REASON")).getText());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                Utils.adminNotification(finalPlayer, id, reasons.toArray(new String[0]));
            }
        }.runTaskAsynchronously(PeyangSuperbAntiCheat.getPlugin());
    }
}
