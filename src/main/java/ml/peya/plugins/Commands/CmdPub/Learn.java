package ml.peya.plugins.Commands.CmdPub;

import ml.peya.plugins.Detect.*;
import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.sql.*;

public class Learn
{
    public static void run(CommandSender sender)
    {
        if (sender instanceof ConsoleCommandSender)
        {
            sender.sendMessage(MessageEngine.get("error.requirePlayer"));

            return;
        }

        CheatDetectNowMeta meta = DetectConnection.spawnWithArmor((Player) sender, DetectType.AURA_BOT);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try (Connection connection = PeyangSuperbAntiCheat.eye.getConnection();
                     Statement statement = connection.createStatement())
                {
                    statement.execute("InSeRt InTo WdLeArN VaLuEs (" + meta.getVL() + ")");
                    Init.initBypass();
                    PeyangSuperbAntiCheat.cheatMeta.remove(meta.getUuids());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    sender.sendMessage(MessageEngine.get("error.unknownSQLError"));

                    ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
                }

            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * 5);


    }
}
