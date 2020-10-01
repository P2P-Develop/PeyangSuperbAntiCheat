package ml.peya.plugins.Moderate;

import ml.peya.plugins.PeyangSuperbAntiCheat;
import ml.peya.plugins.Utils.SQL;
import ml.peya.plugins.Utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;
import static ml.peya.plugins.Variables.trust;

/**
 * 信用プレイヤーを管理する。
 */
public class TrustModifier
{
    /**
     * addって書いてるけどほんとはtoggleなやつ。なかったらaddするしあったらremoveする。
     *
     * @param player 切り替えするプレイヤー。
     * @param sender イベントsender。
     */
    public static void addTrustPlayer(Player player, CommandSender sender)
    {

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try (Connection connection = trust.getConnection();
                     Statement statement = connection.createStatement())
                {
                    if (isTrusted(player))
                    {
                        SQL.delete(connection, "trust",
                                new HashMap<String, String>(){{put("PLAYER",
                                        player.getUniqueId().toString().replace("-", ""));}});
                        sender.sendMessage(get("message.trust.remove", pair("name", player.getName())));
                    }
                    else
                    {
                        SQL.insert(connection, "trust",
                                player.getUniqueId().toString().replace("-", ""));
                        sender.sendMessage(get("message.trust.add", pair("name", player.getName())));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Utils.errorNotification(Utils.getStackTrace(e));
                }
                this.cancel();
            }
        }.runTask(PeyangSuperbAntiCheat.getPlugin());

    }

    /**
     * プレイヤーが信用されてるか調べる。
     *
     * @param player 信用されてる気がするプレイヤー。
     * @return 信用されてたらtrue。
     */
    public static boolean isTrusted(Player player)
    {
        boolean result = false;

        try (Connection connection = trust.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT PLAYER FROM trust WHERE PLAYER=?"))
        {
            statement.setString(1, player.getUniqueId().toString().replace("-", ""));
            result = statement.executeQuery().next();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
        }

        return result;
    }
}
