package ml.peya.plugins.Detect;

import com.fasterxml.jackson.databind.*;
import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.craftbukkit.v1_12_R1.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class NPC
{
    static void setLocation(Location location, EntityPlayer player)
    {
        player.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    static EntityPlayer spawn(Player player, DetectType teleportCase)
    {
        String name;
        JsonNode node = StringUtil.getRandomUser();
        String first = Objects.requireNonNull(node).get("results").get(0).get("name").get("first").asText();
        String last = node.get("results").get(0).get("name").get("last").asText();

        first = first.replaceAll("[^a-zA-Z0-9]", "");
        last = last.replaceAll("[^a-zA-Z0-9]", "");

        Random random = new Random();

        if (random.nextBoolean())
        {
            first = develop.p2p.lib.LeetConverter.convert(first);
            last = develop.p2p.lib.LeetConverter.convert(last);
        }

        name = first + (random.nextBoolean() ? "_": "") + last + (random.nextBoolean() ?  "19" + random.nextInt(120): "");
        if (name.length() > 16)
            name = random.nextBoolean() ? first: last;


        UUID uuid = UUID.fromString(node.get("results").get(0).get("login").get("uuid").asText());
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = ((CraftWorld) player.getWorld()).getHandle();
        GameProfile profile = new GameProfile(uuid, name);

        PlayerInteractManager plMng = new PlayerInteractManager(worldServer);

        EntityPlayer npc = new EntityPlayer(server, worldServer, profile, plMng);

        setLocation(player.getLocation().add(3, 1, 0), npc);

        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;


        ItemStack[] arm = {CraftItemStack.asNMSCopy(RandomArmor.getHelmet()),
                CraftItemStack.asNMSCopy(RandomArmor.getChestPlate()),
                CraftItemStack.asNMSCopy(RandomArmor.getLeggings()),
                CraftItemStack.asNMSCopy(RandomArmor.getBoots()),
                CraftItemStack.asNMSCopy(RandomArmor.getSwords())};

        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                List<String> uuids = PeyangSuperbAntiCheat.config.getStringList("skins");
                Random random = new Random();
                JsonNode node = getSkin(uuids.get(random.nextInt(uuids.size() - 1)));
                if (node != null)
                    profile.getProperties().put("textures", new Property("textures", node.get("properties").get(0).get("value").asText(), node.get("properties").get(0).get("signature").asText()));
            }
        }.runTask(PeyangSuperbAntiCheat.getPlugin());

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, npc));
                connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20);


        BukkitRunnable run = new BukkitRunnable()
        {
            @Override
            public void run()
            {

                for (Player p: Bukkit.getOnlinePlayers())
                {
                    if (!p.hasPermission("psac.viewnpc"))
                        continue;
                    PlayerConnection c = ((CraftPlayer)p).getHandle().playerConnection;
                    c.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
                    c.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));

                    c.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                    p.hidePlayer(PeyangSuperbAntiCheat.getPlugin(), npc.getBukkitEntity());
                    p.showPlayer(PeyangSuperbAntiCheat.getPlugin(), npc.getBukkitEntity());
                }
                NPCTeleport.teleport(player, npc, arm, teleportCase);
            }
        };
        run.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20);



        BukkitRunnable runnable = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getBukkitEntity().getEntityId()));
                for (Player p: Bukkit.getOnlinePlayers())
                {
                    if (!p.hasPermission("psac.viewnpc"))
                        continue;
                    PlayerConnection c = ((CraftPlayer)p).getHandle().playerConnection;
                    c.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                    c.sendPacket(new PacketPlayOutEntityDestroy(npc.getBukkitEntity().getEntityId()));
                }
            }
        };
        runnable.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), (20 * PeyangSuperbAntiCheat.config.getInt("npc.seconds")) + 20);



        return npc;
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

    public  static void setArmor(Player target, EntityPlayer player,  ItemStack[] arm)
    {

        PlayerConnection connection = ((CraftPlayer)target).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.HEAD, arm[0]));
        connection.sendPacket(new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.CHEST, arm[1]));
        connection.sendPacket(new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.LEGS, arm[2]));
        connection.sendPacket(new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.FEET, arm[3]));
        connection.sendPacket(new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.MAINHAND, arm[4]));
    }

}
