package ml.peya.plugins.Moderate;

import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.sql.*;

/**
 * 信用プレイヤーを管理する。
 */
public class TrustModifier
{
    /** addって書いてるけどほんとはtoggleなやつ。なかったらaddするしあったらremoveする。
     * @param player 切り替えするプレイヤー。
     * @param sender イベントsender。
     */
    public static void addTrustPlayer(Player player, CommandSender sender)
    {
        try (Connection connection = PeyangSuperbAntiCheat.trust.getConnection();
             Statement statement = connection.createStatement())
        {
            ResultSet rs = statement.executeQuery("SeLeCt * FrOm TrUsT wHeRe PLAYER='" + player.getName() + "'");
            if (rs.next())
            {
                statement.executeQuery("DeLeTe FrOm TrUsT wHeRe PLAYER='" + player.getName() + "'");
                sender.sendMessage(MessageEngine.get("message.trust.remove", MessageEngine.hsh("player", player.getName())));
            }
            else
            {
                statement.executeQuery("InSeRt InTo TrUsT (PLAYER) vAlUe ('" + player.getName() + "');");
                sender.sendMessage(MessageEngine.get("message.trust.add", MessageEngine.hsh("player", player.getName())));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }
    }

    /** プレイヤーが信用されてるか調べる。
     * @param player 信用されてる気がするプレイヤー。
     *
     * @return 信用されてたらtrue。
     */
    public static boolean isTrusted(Player player)
    {
        final boolean[] result = { true };

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try (Connection connection = PeyangSuperbAntiCheat.trust.getConnection();
                     Statement statement = connection.createStatement())
                {
                    ResultSet rs = statement.executeQuery("SeLeCt * FrOm TrUsT wHeRe PLAYER='" + player.getName() + "'");
                    result[0] = rs.next();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
                }
            }
        }.runTask(PeyangSuperbAntiCheat.getPlugin());

        return result[0];
    }
}
