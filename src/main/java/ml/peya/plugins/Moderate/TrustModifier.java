package ml.peya.plugins.Moderate;

import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.sql.*;

public class TrustModifier
{
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
