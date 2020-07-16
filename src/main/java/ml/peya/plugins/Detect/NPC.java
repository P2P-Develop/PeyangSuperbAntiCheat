package ml.peya.plugins.Detect;

import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.craftbukkit.v1_12_R1.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.*;

public class NPC
{
    static void setLocation(Location location, EntityPlayer player)
    {
        player.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    static EntityPlayer spawn(Player player, DetectType teleportCase)
    {

        EntityPlayer npc = RandomPlayer.getPlayer(player.getWorld());

        Location center = player.getLocation();

        if (center.getPitch() <= 0.0f || center.getPitch() > 15.0f)
            center.setPitch(0.0f);

        Vector vec = center.getDirection().multiply(-3);

        setLocation(player.getLocation().add(0, 1, 0).add(vec), npc);

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;


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

                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));

                player.hidePlayer(PeyangSuperbAntiCheat.getPlugin(), npc.getBukkitEntity());

                for (Player p : Bukkit.getOnlinePlayers())
                {
                    if (!p.hasPermission("psac.viewnpc"))
                        continue;
                    PlayerConnection c = ((CraftPlayer) p).getHandle().playerConnection;
                    c.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
                    c.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));

                    c.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                }

                NPCTeleport.teleport(player, npc, arm, teleportCase);


                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getBukkitEntity().getEntityId()));
                        for (Player p : Bukkit.getOnlinePlayers())
                        {
                            if (!p.hasPermission("psac.viewnpc"))
                                continue;
                            PlayerConnection c = ((CraftPlayer) p).getHandle().playerConnection;
                            c.sendPacket(new PacketPlayOutEntityDestroy(npc.getBukkitEntity().getEntityId()));
                        }
                    }
                }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), (20 * PeyangSuperbAntiCheat.config.getInt("npc.seconds")));
            }
        }.runTask(PeyangSuperbAntiCheat.getPlugin());

        return npc;
    }

    public static void setArmor(Player target, EntityPlayer player, ItemStack[] arm)
    {

        PlayerConnection connection = ((CraftPlayer) target).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.HEAD, arm[0]));
        connection.sendPacket(new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.CHEST, arm[1]));
        connection.sendPacket(new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.LEGS, arm[2]));
        connection.sendPacket(new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.FEET, arm[3]));
        connection.sendPacket(new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.MAINHAND, arm[4]));
    }
}

