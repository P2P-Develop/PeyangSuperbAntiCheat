package ml.peya.plugins.Detect;

import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.sql.*;
import java.util.*;

import static ml.peya.plugins.PeyangSuperbAntiCheat.network;

public class DetectConnection
{
    public static CheatDetectNowMeta spawnWithArmor(Player player, DetectType type)
    {
        EntityPlayer uuid = NPC.spawn(player, type);
        CheatDetectNowMeta meta = PeyangSuperbAntiCheat.cheatMeta.add(player, uuid.getUniqueID(), uuid.getId(), type);
        meta.setCanTesting(true);
        return meta;
    }

    public static void scan(Player player, DetectType type, CommandSender sender)
    {
        if (type == DetectType.ANTI_KB)
        {
            TestKnockback.scan(player, type, sender);
            return;
        }

        CheatDetectNowMeta meta = spawnWithArmor(player, type);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                meta.setCanTesting(false);

                if (PeyangSuperbAntiCheat.banLeft <= meta.getVL())
                {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            double vl = meta.getVL();
                            ArrayList<Triple<Double, Double, Double>> arr = new ArrayList<>();
                            arr.add(Triple.of(vl, vl, vl));
                            network.learn(arr, 1000);

                            PeyangSuperbAntiCheat.banLeft = (int) Math.round(network.commit(Pair.of(vl, vl)));
                            
                            try (Connection connection = PeyangSuperbAntiCheat.learn.getConnection();
                                 Statement statement = connection.createStatement())
                            {
                                statement.execute("InSeRt Or RePlAcE iNtO wdlearn(standard) vAlUeS (" + 
                                                  PeyangSuperbAntiCheat.banLeft.toString() + 
                                                  ");");
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
                            }
                        }
                    };

                    ArrayList<String> reason = new ArrayList<>();
                    try (Connection connection = PeyangSuperbAntiCheat.eye.getConnection();
                         Statement statement = connection.createStatement();
                         Statement statement2 = connection.createStatement())
                    {
                        ResultSet rs = statement.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE ID='" + player.getName() + "'");
                        while (rs.next())
                        {
                            ResultSet set = statement2.executeQuery("SeLeCt * FrOm WaTcHrEaSon WhErE MNGID='" +
                                    rs.getString("MNGID") + "'");
                            while (set.next())
                            {
                                reason.add(Objects.requireNonNull(CheatTypeUtils.getCheatTypeFromString(set.getString("REASON"))).getText());
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
                    }

                    ArrayList<String> realReason = new ArrayList<>(new HashSet<>(reason));

                    KickUtil.kickPlayer(player, (String.join(", ", realReason).equals("") ? "KillAura" : "Report: " + String.join(", ", realReason)), true, false);

                }


                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        String name = player.getDisplayName() + (player.getDisplayName().equals(player.getName()) ? "" : (" (" + player.getName() + ") "));

                        switch (type)
                        {
                            case AURA_BOT:
                                if (sender == null)
                                {
                                    for (Player np : Bukkit.getOnlinePlayers())
                                    {
                                        if (!np.hasPermission("psac.aurabot"))
                                            continue;
                                        np.spigot().sendMessage(TextBuilder.textTestRep(name, meta.getVL(), PeyangSuperbAntiCheat.banLeft).create());
                                    }
                                }
                                else
                                    sender.spigot().sendMessage(TextBuilder.textTestRep(name, meta.getVL(), PeyangSuperbAntiCheat.banLeft).create());
                                break;

                            case AURA_PANIC:
                                if (sender == null)
                                {
                                    for (Player np : Bukkit.getOnlinePlayers())
                                    {
                                        if (!np.hasPermission("psac.aurapanic"))
                                            continue;
                                        np.spigot().sendMessage(TextBuilder.textPanicRep(name, meta.getVL()).create());
                                    }
                                }
                                else
                                    sender.spigot().sendMessage(TextBuilder.textPanicRep(name, meta.getVL()).create());
                                break;
                        }

                        PeyangSuperbAntiCheat.cheatMeta.remove(meta.getUuids());
                        this.cancel();
                    }
                }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 10);
                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * PeyangSuperbAntiCheat.config.getInt("npc.seconds"));
    }
}
