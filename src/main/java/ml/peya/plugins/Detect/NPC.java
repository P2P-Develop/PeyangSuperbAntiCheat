package ml.peya.plugins.Detect;

import com.fasterxml.jackson.databind.*;
import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.command.*;
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
        if (name.length() > 14)
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

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));

                connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));

                player.hidePlayer(PeyangSuperbAntiCheat.getPlugin(), npc.getBukkitEntity());

                for (Player p: Bukkit.getOnlinePlayers())
                {
                    if (!p.hasPermission("psac.viewnpc"))
                        continue;
                    PlayerConnection c = ((CraftPlayer)p).getHandle().playerConnection;
                    c.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
                    c.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
                }

                NPCTeleport.teleport(player, npc, arm, teleportCase);


                new BukkitRunnable()
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
                }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), (20 * PeyangSuperbAntiCheat.config.getInt("npc.seconds")));
            }
        }.runTask(PeyangSuperbAntiCheat.getPlugin());

        return npc;
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

