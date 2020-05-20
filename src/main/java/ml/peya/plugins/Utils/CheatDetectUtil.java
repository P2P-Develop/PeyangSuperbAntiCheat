package ml.peya.plugins.Utils;

import com.fasterxml.jackson.databind.*;
import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import ml.peya.plugins.*;
import net.citizensnpcs.api.*;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
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
    public static void scan(Player player)
    {
        UUID uuid = spawn(player);
        CheatDetectNowMeta meta = PeyangSuperbAntiCheat.cheatMeta.add(player, uuid);

        NPC xPlusNPC = CitizensAPI.getNPCRegistry().getByUniqueId(uuid);

        RandomArmor.setRandomArmor(xPlusNPC);



        new BukkitRunnable()
        {
            @Override
            public void run()
            {
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * 9);

    }

    private static UUID spawn(Player player)
    {

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.RED + UUID.randomUUID().toString());
        npc.spawn(player.getLocation().add(3, 0, 0));

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

                player.showPlayer(PeyangSuperbAntiCheat.getPlugin(), player1);

                teleport(player, npc);


                BukkitRunnable runnable = new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        npc.despawn();
                        CitizensAPI.getNPCRegistry().deregister(npc);
                    }
                };
                runnable.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * 5);
            }
        };

        run.runTask(PeyangSuperbAntiCheat.getPlugin());

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

        private static void teleport (Player player, NPC target) {
        final double yaw = 358.0;
        final double[] time = {0.0};
        final double radius = 3.5;
        final double range = 25.0;
        new BukkitRunnable() {
            public void run() {
                for (double i = 0; i < Math.PI * 2; i++) {

                    Location center = player.getLocation();
                    Location n = new Location(center.getWorld(),
                            xPos(time[0], radius, yaw) + center.getX(),
                            center.getY() + 1,
                            yPos(time[0], radius) + center.getZ());
                    target.teleport(n, PlayerTeleportEvent.TeleportCause.PLUGIN);
                }


                time[0] += 0.133;
                if (time[0] >= range)
                    this.cancel();
            }
        }.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, 1);
    }


    private static double xPos(double time, double radius, double yaw){
        return Math.sin(time) * radius * Math.cos(Math.PI/180 * yaw);
    }

    private static double yPos(double time, double radius){
        return Math.cos(time)*radius;
    }
}
