package ml.peya.plugins.Detect;

import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.Vector;
import org.yaml.snakeyaml.representer.*;

import java.lang.reflect.*;
import java.util.*;

public class NPCTeleport
{
    public static void teleport(Player player, EntityPlayer target, ItemStack[] arm, DetectType tpCase)
    {
        if (tpCase == DetectType.AURA_BOT)
            auraBot_teleport(player, target, arm);
        else if (tpCase == DetectType.AURA_PANIC)
            auraPanic_teleport(player, target, arm, tpCase.getPanicTime());
    }

    private static void auraPanic_teleport(Player player, EntityPlayer target, ItemStack[] arm, int sec)
    {
        final double range = PeyangSuperbAntiCheat.config.getDouble("npc.panicRange");
        final double[] clt = {0.0};

        new BukkitRunnable() {
            public void run()
            {
                for (double i = 0; i < Math.PI * 2; i++) {

                    Location center = player.getLocation();

                    if (center.getPitch() <= 0.0f || center.getPitch() > 15.0f)
                    center.setPitch(0.0f);

                    Vector vec = center.getDirection().multiply(0 - range);

                    Location n = center.add(vec);

                    n.setY(center.getY() + range);

                    NPC.setLocation(n, target);
                    target.getBukkitEntity().teleport(n, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
                    connection.sendPacket(new PacketPlayOutEntityTeleport(target));
                    NPC.setArmor(player, target, arm);
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            for (Player p: Bukkit.getOnlinePlayers())
                            {
                                if (!p.hasPermission("psr.viewnpc"))
                                    continue;
                                PlayerConnection c = ((CraftPlayer)p).getHandle().playerConnection;
                                c.sendPacket(new PacketPlayOutEntityTeleport(target));
                                NPC.setArmor(p, target, arm);
                            }
                            this.cancel();
                        }
                    }.runTask(PeyangSuperbAntiCheat.getPlugin());
                }

                clt[0] += 0.001;
                if (clt[0] >= sec)
                    this.cancel();
            }
        }.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, 1);
    }

    private static void auraBot_teleport(Player player, EntityPlayer target, ItemStack[] arm)
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

                    Location n = new Location(center.getWorld(),
                            auraBot_xPos(time[0], radius) + center.getX(),
                            center.getY() + creator.get(0.01, count[0] < 20),
                            auraBot_zPos(time[0], radius, yaw) + center.getZ());
                    NPC.setLocation(n, target);
                    target.getBukkitEntity().teleport(n, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
                    connection.sendPacket(new PacketPlayOutEntityTeleport(target));
                    NPC.setArmor(player, target, arm);
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            for (Player p: Bukkit.getOnlinePlayers())
                            {
                                if (!p.hasPermission("psr.viewnpc"))
                                    continue;
                                PlayerConnection c = ((CraftPlayer)p).getHandle().playerConnection;
                                c.sendPacket(new PacketPlayOutEntityTeleport(target));
                                NPC.setArmor(p, target, arm);
                            }
                            this.cancel();
                        }
                    }.runTask(PeyangSuperbAntiCheat.getPlugin());
                    count[0]++;
                }


                //time[0] += 0.133;
                time[0] += PeyangSuperbAntiCheat.config.getDouble("npc.time") + (dob / 100.0);
                if (time[0] >= range)
                    this.cancel();
            }
        }.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, 1);
    }

    private static double auraBot_zPos(double time, double radius, double yaw){
        return Math.sin(time) * radius * Math.cos(Math.PI/180 * yaw);
    }

    private static double auraBot_xPos(double time, double radius){
        return Math.cos(time)*radius;
    }

}
