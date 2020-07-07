package ml.peya.plugins.Detect;

import develop.p2p.lib.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.Vector;

import java.util.*;

public class NPCTeleport
{
    public static void teleport(Player player, EntityPlayer target, ItemStack[] arm, DetectType tpCase)
    {
        if (tpCase == DetectType.AURA_BOT)
            auraBot_teleport(player, target, arm);
        else if (tpCase == DetectType.AURA_PANIC)
            auraPanic_teleport(player, target, arm, tpCase.getPanicCount(), tpCase.getSender());
    }

    private static void auraPanic_teleport(Player player, EntityPlayer target, ItemStack[] arm, int count, CommandSender sender)
    {
        final double range = PeyangSuperbAntiCheat.config.getDouble("npc.panicRange");
        final double[] clt = {0.0};
        final int[] now = {0};

        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;

        int sec = PeyangSuperbAntiCheat.config.getInt("npc.seconds");
        double delay = (1.5 /  count) * sec;


        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                now[0]++;


                PacketPlayOutAnimation animation = new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 1);
                connection.sendPacket(animation);

                HashMap<String, Object> map = new HashMap<>();
                map.put("hit", now[0]);
                map.put("max", count);

                sender.sendMessage(MessageEngine.get("message.auraCheck.panic.lynx", map));
                if (now[0] >= count)
                    this.cancel();
            }
        }.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, 15 * (long) delay);

        new BukkitRunnable()
        {
            public void run()
            {
                for (double i = 0; i < Math.PI * 2; i++) {

                    Location center = player.getLocation();

                    if (center.getPitch() <= 0.0f || center.getPitch() > 15.0f)
                        center.setPitch(0.0f);

                    Vector vec = center.getDirection().multiply(0 - range);

                    Location n = center.add(vec);

                    n.setY(center.getY() + range);

                    n.setPitch(50);

                    float head = ((CraftPlayer) player).getHandle().getHeadRotation() * 0.5f;

                    if (head < 0)
                        head = head * 2;

                    NPC.setLocation(n, target);
                    connection.sendPacket(new PacketPlayOutEntityTeleport(target));
                    connection.sendPacket(new PacketPlayOutEntityHeadRotation(target, (byte)head));


                    NPC.setArmor(player, target, arm);
                    float finalHead = head;
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            for (Player p: Bukkit.getOnlinePlayers())
                            {
                                if (!p.hasPermission("psac.viewnpc"))
                                    continue;
                                PlayerConnection c = ((CraftPlayer)p).getHandle().playerConnection;
                                c.sendPacket(new PacketPlayOutEntityTeleport(target));
                                connection.sendPacket(new PacketPlayOutEntityHeadRotation(target, (byte) finalHead));

                                NPC.setArmor(p, target, arm);
                            }
                            this.cancel();
                        }
                    }.runTask(PeyangSuperbAntiCheat.getPlugin());
                }

                clt[0] += 0.035;
                if (clt[0] >= sec)
                    this.cancel();
            }
        }.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, 1);

    }

    private static void auraBot_teleport(Player player, EntityPlayer target, ItemStack[] arm)
    {
        final double yaw = 360.0;
        final double[] time = {0.0};
        final double radius = PeyangSuperbAntiCheat.config.getDouble("npc.range");

        WaveCreator creator = new WaveCreator(1.0, 2.0, 0.0);
        WaveCreator ypp = new WaveCreator(10.0, 100.0, 10.0);

        final boolean waveFlag = PeyangSuperbAntiCheat.config.getBoolean("npc.wave");

        WaveCreator wave = new WaveCreator(radius - 0.1, radius, PeyangSuperbAntiCheat.config.getDouble("npc.waveMin"));
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;

        final int[] count = {0};
        BukkitRunnable r = new BukkitRunnable() {
            public void run()
            {
                for (double i = 0; i < Math.PI * 2; i++) {

                    double rangeTmp = radius;

                    if (waveFlag)
                        rangeTmp = wave.get(0.01, true);

                    Location center = player.getLocation();
                    Location n = new Location(center.getWorld(),
                            auraBot_xPos(time[0], rangeTmp) + center.getX(),
                            center.getY() + creator.get(0.01, count[0] < 20),
                            auraBot_zPos(time[0], rangeTmp, yaw) + center.getZ(),
                            (float) ypp.getStatic(),
                            (float) ypp.get(4.5, false));

                    NPC.setLocation(n, target);
                    connection.sendPacket(new PacketPlayOutEntityTeleport(target));

                    NPC.setArmor(player, target, arm);
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            for (Player p: Bukkit.getOnlinePlayers())
                            {
                                if (!p.hasPermission("psac.viewnpc"))
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
                time[0] += PeyangSuperbAntiCheat.config.getDouble("npc.time");
            }
        };
        r.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, 1);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                r.cancel();
                this.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * PeyangSuperbAntiCheat.config.getLong("npc.seconds"));

    }

    private static double auraBot_zPos(double time, double radius, double yaw){
        return Math.sin(time) * radius * Math.cos(Math.PI/180 * yaw);
    }

    private static double auraBot_xPos(double time, double radius){
        return Math.cos(time)*radius;
    }

}
