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
        ArrayList<UUID> scanNPC =  spawn(player);
        NPC xPlusNPC = CitizensAPI.getNPCRegistry().getByUniqueId(scanNPC.get(0));
        NPC xMinusNPC = CitizensAPI.getNPCRegistry().getByUniqueId(scanNPC.get(1));
        NPC yPlusNPC = CitizensAPI.getNPCRegistry().getByUniqueId(scanNPC.get(2));
        NPC zMinusNPC = CitizensAPI.getNPCRegistry().getByUniqueId(scanNPC.get(3));
        NPC zPlusNPC = CitizensAPI.getNPCRegistry().getByUniqueId(scanNPC.get(4));

        RandomArmor.setRandomArmor(xPlusNPC);
        RandomArmor.setRandomArmor(xMinusNPC);
        RandomArmor.setRandomArmor(yPlusNPC);
        RandomArmor.setRandomArmor(zMinusNPC);
        RandomArmor.setRandomArmor(zPlusNPC);


        PeyangSuperbAntiCheat.cheatMeta = new CheatDetectNowMeta(player, scanNPC);

    }

    private static ArrayList<UUID> spawn(Player player)
    {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.RED + UUID.randomUUID().toString());
        NPC npc2 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.RED + UUID.randomUUID().toString());
        NPC npc3 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.RED + UUID.randomUUID().toString());
        NPC npc4 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.RED + UUID.randomUUID().toString());
        NPC npc5 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.RED + UUID.randomUUID().toString());
        npc.spawn(player.getLocation().add(3, 0, 0));
        npc2.spawn(player.getLocation().add(-3, 0, 0));
        npc3.spawn(player.getLocation().add(0, 0, 3));
        npc4.spawn(player.getLocation().add(-0, 0, -3));
        npc5.spawn(player.getLocation().add(-0, 3, 0));

        Player player1 = (Player) npc.getEntity();
        Player player2 = (Player) npc2.getEntity();
        Player player3 = (Player) npc3.getEntity();
        Player player4 = (Player) npc4.getEntity();
        Player player5 = (Player) npc5.getEntity();
        for (Player p: Bukkit.getOnlinePlayers())
        {
            p.hidePlayer(PeyangSuperbAntiCheat.getPlugin(), player1);
            p.hidePlayer(PeyangSuperbAntiCheat.getPlugin(), player2);
            p.hidePlayer(PeyangSuperbAntiCheat.getPlugin(), player3);
            p.hidePlayer(PeyangSuperbAntiCheat.getPlugin(), player4);
            p.hidePlayer(PeyangSuperbAntiCheat.getPlugin(), player5);
        }
        List<String> uuids = PeyangSuperbAntiCheat.config.getStringList("skins");
        Random random = new Random();
        setSkin(player1, uuids.get(random.nextInt(uuids.size() - 1)), player);
        setSkin(player2, uuids.get(random.nextInt(uuids.size() - 1)), player);
        setSkin(player3, uuids.get(random.nextInt(uuids.size() - 1)), player);
        setSkin(player4, uuids.get(random.nextInt(uuids.size() - 1)), player);
        setSkin(player5, uuids.get(random.nextInt(uuids.size() - 1)), player);

        for (int i = 0; i < 115; i++)
        {
            BukkitRunnable cause = new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    npc.teleport(player.getLocation().add(3 + random.nextDouble() + random.nextDouble(), (double) random.nextInt(2) + 1 + random.nextDouble() + random.nextDouble(), 0), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    npc2.teleport(player.getLocation().add(-3, (double) random.nextInt(2) + 1 +random.nextDouble() + random.nextDouble(), 0), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    npc3.teleport(player.getLocation().add(0, (double) random.nextInt(2) + 1 + random.nextDouble() + random.nextDouble(), 3 + random.nextDouble() + random.nextDouble()), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    npc4.teleport(player.getLocation().add(0, (double) random.nextInt(2) + 1 + random.nextDouble() + random.nextDouble(), -3 + random.nextDouble() + random.nextDouble()), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    npc5.teleport(player.getLocation().add(0, (double) random.nextInt(2) + 2 + random.nextDouble() + random.nextDouble(),  0 + random.nextDouble() + random.nextDouble()), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
            };
            cause.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), i);
        }

        BukkitRunnable runnable = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                npc.despawn();
                npc2.despawn();
                npc3.despawn();
                npc4.despawn();
                npc5.despawn();
                CitizensAPI.getNPCRegistry().deregister(npc);
                CitizensAPI.getNPCRegistry().deregister(npc2);
                CitizensAPI.getNPCRegistry().deregister(npc3);
                CitizensAPI.getNPCRegistry().deregister(npc4);
                CitizensAPI.getNPCRegistry().deregister(npc5);
            }
        };
        runnable.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 115L);

        ArrayList<UUID> resp = new ArrayList<>();
        resp.add(npc.getUniqueId());
        resp.add(npc2.getUniqueId());
        resp.add(npc3.getUniqueId());
        resp.add(npc4.getUniqueId());
        resp.add(npc5.getUniqueId());
        return resp;
    }

    public static void setSkin(Player player, String uuid, Player target) {

        BukkitRunnable runnable = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                GameProfile profile = ((CraftPlayer) player).getHandle().getProfile();
                try
                {

                    HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", uuid)).openConnection();
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
                        JsonNode node = mapper.readTree(builder.toString());
                        profile.getProperties().put("textures", new Property("textures", node.get("properties").get(0).get("value").asText(), node.get("properties").get(0).get("signature").asText()));
                    }
                    else
                    {
                        PeyangSuperbAntiCheat.logger.info("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
                    }

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                BukkitRunnable runnable = new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        target.showPlayer(PeyangSuperbAntiCheat.getPlugin(),player);
                    }
                };
                runnable.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 35L);
            }
        };

        runnable.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 15L);

    }
}
