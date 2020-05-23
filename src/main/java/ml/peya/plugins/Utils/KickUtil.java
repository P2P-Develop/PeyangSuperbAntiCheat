package ml.peya.plugins.Utils;

import ml.peya.plugins.*;
import net.md_5.bungee.api.*;
import org.bukkit.entity.*;

import javax.swing.plaf.nimbus.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class KickUtil
{
    public static void kickPlayer (Player player)
    {
        UUID uuid = UUID.randomUUID();
        try (Connection kickC = PeyangSuperbAntiCheat.banKick.getConnection();
             Connection eyeC = PeyangSuperbAntiCheat.eye.getConnection();
             Statement kickS = kickC.createStatement();
             Statement eyeS = eyeC.createStatement();
             Statement eyeS2 = eyeC.createStatement())
        {
            kickS.execute("InSeRt InTo KiCk VaLuEs(" +
                    "'" + player.getName() + "'," +
                    "'" + player.getUniqueId().toString() + "'," +
                    "'" + uuid.toString() + "'," +
                    "'" + new Date().getTime() + "'");

             ResultSet eyeList = eyeS.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE UuId = '" + player.getUniqueId().toString().replace("-", "") + "'");

            while (eyeList.next())
            {
                String MNGID = eyeList.getString("MNGID");
                eyeS2.execute("DeLeTe FrOm WaTcHrEaSoN WhErE MnGiD = '" + MNGID + "'");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }

        String message = ChatColor.RED + "あなたは、このサーバーからKickされました！" +
                "\n" +
                ChatColor.GRAY + "理由: " +
                ChatColor.WHITE + "PYGANTICHEAT DETECTION " +
                "\n" +
                "\n" +
                ChatColor.GRAY + "Kick ID: " +
                ChatColor.WHITE + uuid.toString();
        player.kickPlayer(message);
    }
}
