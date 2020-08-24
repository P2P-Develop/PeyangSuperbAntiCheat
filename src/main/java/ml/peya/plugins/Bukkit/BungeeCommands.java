package ml.peya.plugins.Bukkit;

import ml.peya.plugins.Bukkit.Enum.*;
import ml.peya.plugins.Bukkit.Moderate.*;
import ml.peya.plugins.Bukkit.Utils.*;
import ml.peya.plugins.BungeeStructure.*;
import org.bukkit.scheduler.*;

import java.sql.*;
import java.util.*;

public class BungeeCommands implements CommandExecutor
{
    @Command(label = "ping")
    public static void ping(CommandComponent command)
    {
        Bungee.sendMessage("pong");
        Variables.bungeeCord = true;
    }

    @Command(label = "pong")
    public static void pong(CommandComponent command)
    {
        Variables.bungeeCord = true;
    }

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

        String finalPlayer = player;
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                ArrayList<String> reasons = new ArrayList<>();
                try (Connection connection = Variables.eye.getConnection();
                     Statement statement = connection.createStatement())
                {
                    ResultSet result = statement.executeQuery("SELECT * FROM watchreason WHERE MNGID='" + id + "'");
                    while (result.next())
                    {
                        String reason = result.getString("REASON");
                        EnumCheatType type = CheatTypeUtils.getCheatTypeFromString(reason);
                        reasons.add(type.getText());
                    }
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
