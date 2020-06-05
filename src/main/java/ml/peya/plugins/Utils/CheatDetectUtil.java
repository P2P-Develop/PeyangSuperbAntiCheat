package ml.peya.plugins.Utils;

import com.fasterxml.jackson.databind.*;
import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import javafx.util.*;
import ml.peya.plugins.*;
import net.citizensnpcs.api.*;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.*;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class CheatDetectUtil
{
    public static CheatDetectNowMeta spawnWithArmor(Player player)
    {
        UUID uuid = spawn(player);

        CheatDetectNowMeta meta = PeyangSuperbAntiCheat.cheatMeta.add(player, uuid);
        meta.setCanNPC(true);
        NPC npc = CitizensAPI.getNPCRegistry().getByUniqueId(uuid);
        RandomArmor.setRandomArmor(npc);
        return meta;
    }

    public static void scan(Player player, CommandSender sender)
    {
        CheatDetectNowMeta meta = spawnWithArmor(player);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                meta.setCanNPC(false);
                if (PeyangSuperbAntiCheat.banLeft <= meta.getVL())
                    KickUtil.kickPlayer(player, true, false);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        String name = player.getDisplayName() + (player.getDisplayName().equals(player.getName()) ? "": (" (" + player.getName() + ") "));
                        if (sender != null)
                            sender.spigot().sendMessage(TextBuilder.textTestRep(name, meta.getVL(), PeyangSuperbAntiCheat.banLeft).create());
                        else
                        {
                            for (Player np: Bukkit.getOnlinePlayers())
                            {
                                if (!np.hasPermission("psr.admin"))
                                    continue;
                                np.spigot().sendMessage(TextBuilder.textTestRep(name, meta.getVL(), PeyangSuperbAntiCheat.banLeft).create());
                            }
                        }

                        PeyangSuperbAntiCheat.cheatMeta.remove(meta.getUuids());
                    }
                }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 10);

            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * PeyangSuperbAntiCheat.config.getInt("npc.seconds"));
    }

    private static UUID spawn(Player player)
    {

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.RED + UUID.randomUUID().toString());
        npc.spawn(player.getLocation().add(3, 1, 0));

        Player player1 = (Player) npc.getEntity();
        for (Player p: Bukkit.getOnlinePlayers())
            p.hidePlayer(PeyangSuperbAntiCheat.getPlugin(), player1);

        BukkitRunnable run = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                List<String> uuids = PeyangSuperbAntiCheat.config.getStringList("skins");
                Random random = new Random();
                JsonNode node = getSkin(uuids.get(random.nextInt(uuids.size() - 1)));
                if (node != null)
                {
                    GameProfile profile = ((CraftPlayer) player1).getHandle().getProfile();
                    profile.getProperties().put("textures", new Property("textures", node.get("properties").get(0).get("value").asText(), node.get("properties").get(0).get("signature").asText()));
                }

                for (Player p: Bukkit.getOnlinePlayers())
                {
                    if (!p.hasPermission("psr.viewnpc"))
                        continue;
                    p.showPlayer(PeyangSuperbAntiCheat.getPlugin(), player1);
                }
                player.showPlayer(PeyangSuperbAntiCheat.getPlugin(), player1);

                teleport(player, npc);
            }
        };
        run.runTask(PeyangSuperbAntiCheat.getPlugin());




        BukkitRunnable runnable = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                npc.despawn();
                CitizensAPI.getNPCRegistry().deregister(npc);
            }
        };
        runnable.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * PeyangSuperbAntiCheat.config.getInt("npc.seconds"));


        return npc.getUniqueId();
    }

    public static JsonNode getSkin(String uuid)
    {
        try
        {
            HttpsURLConnection connection;
            connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", uuid)).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK)
            {

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String readed = reader.readLine();
                while (readed != null)
                {
                    builder.append(readed);
                    readed = reader.readLine();
                }
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(builder.toString());
            }
            else
            {
                PeyangSuperbAntiCheat.logger.info("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
            return null;
        }
    }

    private static void teleport (Player player, NPC target)
    {
        final double yaw = 358.0;
        final double[] time = {0.0};
        final double radius = PeyangSuperbAntiCheat.config.getDouble("npc.range");
        final double range = PeyangSuperbAntiCheat.config.getDouble("npc.bump");
        Random generator = new Random();
        int dob = generator.nextInt(3);
        WaveCreator creator = new WaveCreator(1.0, 2.0, 0.0);
        final int[] count = {0};

        new BukkitRunnable() {
            public void run()
            {
                for (double i = 0; i < Math.PI * 2; i++) {

                    Location center = player.getLocation();
                    /*
                    Location n = new Location(center.getWorld(),
                            xPos(time[0], radius, yaw) + center.getX(),
                            center.getY() + creator.get(0.01),
                            zPos(time[0], radius) + center.getZ());
                    */

                    Location n = new Location(center.getWorld(),
                            zPos(time[0], radius) + center.getX(),
                            center.getY() + creator.get(0.01, count[0] < 20),
                            xPos(time[0], radius, yaw) + center.getZ());

                    target.teleport(n, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    count[0]++;
                }


                //time[0] += 0.133;
                time[0] += PeyangSuperbAntiCheat.config.getDouble("npc.time") + (dob / 100.0);
                if (time[0] >= range)
                    this.cancel();
            }
        }.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, 1);
    }


    private static double xPos(double time, double radius, double yaw){
        return Math.sin(time) * radius * Math.cos(Math.PI/180 * yaw);
    }

    private static double zPos(double time, double radius){
        return Math.cos(time)*radius;
    }
}
