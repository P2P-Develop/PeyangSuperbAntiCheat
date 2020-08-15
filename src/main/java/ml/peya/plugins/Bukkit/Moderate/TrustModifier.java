package ml.peya.plugins.Bukkit.Moderate;

import ml.peya.plugins.Bukkit.*;
import ml.peya.plugins.Bukkit.Utils.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.sql.*;

import static ml.peya.plugins.Bukkit.Utils.MessageEngine.get;
import static ml.peya.plugins.Bukkit.Utils.MessageEngine.pair;
import static ml.peya.plugins.Bukkit.Variables.trust;

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
                    ResultSet rs = statement.executeQuery("SeLeCt * FrOm TrUsT wHeRe PLAYER='" + player.getName() + "'");
                    if (rs.next())
                    {
                        statement.execute("DeLeTe FrOm TrUsT wHeRe PLAYER='" + player.getName() + "'");
                        sender.sendMessage(get("message.trust.remove", pair("name", player.getName())));
                    }
                    else
                    {
                        statement.execute("InSeRt InTo TrUsT vAlUeS ('" + player.getName() + "');");
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
             Statement statement = connection.createStatement())
        {
            ResultSet rs = statement.executeQuery("SeLeCt * FrOm TrUsT wHeRe PLAYER='" + player.getName() + "'");
            result = rs.next();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
        }


        return result;
    }
}
