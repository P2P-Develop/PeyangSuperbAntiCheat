package ml.peya.plugins.Detect;

import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.lang3.tuple.*;
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

                String mng = UUID.randomUUID().toString();

                try (Connection connection = PeyangSuperbAntiCheat.learn.getConnection();
                     Statement statement = connection.createStatement())
                {
                    statement.execute("InSeRt iNtO wdlearn vAlUeS (" +
                            PeyangSuperbAntiCheat.learnCountLimit +
                            ", 0, \"" +
                            mng +
                            "\", 0);");

                    ResultSet result = statement.executeQuery("SeLeCt * fRoM wDlEaRn");
                    while (result.next())
                    {
                        PeyangSuperbAntiCheat.learnCount = result.getInt("learncount");
                    }

                    double vl = meta.getVL();
                    double seconds = PeyangSuperbAntiCheat.cheatMeta.getMetaByPlayerUUID(player.getUniqueId()).getSeconds();

                    if ((PeyangSuperbAntiCheat.learnCount > PeyangSuperbAntiCheat.learnCountLimit && network.commit(Pair.of(vl, seconds)) > 0.5) || (PeyangSuperbAntiCheat.learnCount < PeyangSuperbAntiCheat.learnCountLimit && PeyangSuperbAntiCheat.banLeft <= meta.getVL()))
                    {
                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                ArrayList<Triple<Double, Double, Double>> arr = new ArrayList<>();
                                arr.add(Triple.of(vl, seconds, seconds / meta.getVL()));
                                network.learn(arr, 1000);

                                PeyangSuperbAntiCheat.banLeft = (int) Math.round(network.commit(Pair.of(vl, vl)));

                                try (Connection connection2 = PeyangSuperbAntiCheat.learn.getConnection();
                                     Statement statement2 = connection2.createStatement())
                                {
                                    statement2.execute("update wdlearn set " +
                                            "learncount = " + PeyangSuperbAntiCheat.banLeft + " " +
                                            "where MNGID = \"" + mng + "\"");
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                    ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
                                }
                                this.cancel();
                            }
                        }.runTask(PeyangSuperbAntiCheat.getPlugin());

                        ArrayList<String> reason = new ArrayList<>();
                        try (Connection connection3 = PeyangSuperbAntiCheat.eye.getConnection();
                             Statement statement3 = connection3.createStatement();
                             Statement statement4 = connection3.createStatement())
                        {
                            ResultSet rs = statement3.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE ID='" + player.getName() + "'");
                            while (rs.next())
                            {
                                ResultSet set = statement4.executeQuery("SeLeCt * FrOm WaTcHrEaSon WhErE MNGID='" +
                                        rs.getString("MNGID") + "'");
                                while (set.next())
                                    reason.add(Objects.requireNonNull(CheatTypeUtils.getCheatTypeFromString(set.getString("REASON"))).getText());
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
                        }

                        ArrayList<String> realReason = new ArrayList<>(new HashSet<>(reason));

                        KickUtil.kickPlayer(player, (String.join(", ", realReason).equals("") ? "KillAura": "Report: " + String.join(", ", realReason)), true, false);

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
                }


                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        String name = player.getDisplayName() + (player.getDisplayName().equals(player.getName()) ? "": (" (" + player.getName() + ") "));

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

                        PeyangSuperbAntiCheat.cheatMeta.remove(meta.getUUIDs());
                        this.cancel();
                    }
                }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 10);
                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * PeyangSuperbAntiCheat.config.getInt("npc.seconds"));
    }
}
