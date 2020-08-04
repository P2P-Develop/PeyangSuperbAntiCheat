package ml.peya.plugins.Detect;

import develop.p2p.lib.*;
import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.Vector;

import java.util.*;

import static ml.peya.plugins.Utils.LookingUtils.isLooking;

public class NPCTeleport
{
    public static void teleport(Player player, EntityPlayer target, ItemStack[] arm, DetectType tpCase)
    {
        switch (tpCase)
        {
            case AURA_BOT:
                auraBot_teleport(player, target, arm);
                break;
            case AURA_PANIC:
                auraPanic_teleport(player, target, arm, tpCase.getPanicCount(), tpCase.getSender());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tpCase);
        }
    }

    private static void auraPanic_teleport(Player player, EntityPlayer target, ItemStack[] arm, int count, CommandSender sender)
    {
        final double range = PeyangSuperbAntiCheat.config.getDouble("npc.panicRange");
        final double[] clt = {0.0};
        final int[] now = {0};

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        int sec = PeyangSuperbAntiCheat.config.getInt("npc.seconds");

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                now[0]++;

                connection.sendPacket(new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 1));

                HashMap<String, Object> map = new HashMap<>();
                map.put("hit", now[0]);
                map.put("max", count);

                sender.sendMessage(MessageEngine.get("message.auraCheck.panic.lynx", map));
                if (now[0] >= count)
                    this.cancel();
            }
        }.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, 15 * (long) (1.5 / count) * sec);

        new BukkitRunnable()
        {
            public void run()
            {
                for (double i = 0; i < Math.PI * 2; i++)
                {
                    Location center = player.getLocation();

                    if (center.getPitch() <= 0.0f || center.getPitch() > 15.0f)
                        center.setPitch(0.0f);

                    Location n = center.add(center.getDirection().multiply(0 - range));

                    n.setY(center.getY() + range);

                    n.setPitch(50);

                    float head = ((CraftPlayer) player).getHandle().getHeadRotation() * 0.5f;

                    if (head < 0)
                        head *= 2;

                    NPC.setLocation(n, target);
                    connection.sendPacket(new PacketPlayOutEntityTeleport(target));
                    connection.sendPacket(new PacketPlayOutEntityHeadRotation(target, (byte) head));

                    NPC.setArmor(player, target, arm);
                    float finalHead = head;
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            Bukkit.getOnlinePlayers().parallelStream().filter(p -> p.hasPermission("psac.viewnpc")).forEachOrdered(p -> {
                                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityTeleport(target));
                                connection.sendPacket(new PacketPlayOutEntityHeadRotation(target, (byte) finalHead));
                                NPC.setArmor(p, target, arm);
                            });
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
        final double[] time = {0.0};
        final double radius = PeyangSuperbAntiCheat.config.getDouble("npc.range");

        WaveCreator ypp = new WaveCreator(10.0, 100.0, 10.0);

        final int[] count = { 0 };
        BukkitRunnable r = new BukkitRunnable()
        {
            public void run()
            {

                double speed = 0.0;

                if (player.hasMetadata("speed"))
                    for (MetadataValue value : player.getMetadata("speed"))
                        if (value.getOwningPlugin().getName().equals(PeyangSuperbAntiCheat.getPlugin().getName()))
                            speed = value.asDouble() * 2.0;
                for (double i = 0; i < Math.PI * 2; i++)
                {
                    double rangeTmp = radius;

                    if (PeyangSuperbAntiCheat.config.getBoolean("npc.wave"))
                        rangeTmp = new WaveCreator(radius - 0.1, radius, PeyangSuperbAntiCheat.config.getDouble("npc.waveMin")).get(0.01, true);

                    Location center = player.getLocation();
                    Location n = new Location(
                            center.getWorld(),
                            auraBot_xPos(time[0], rangeTmp + speed) + center.getX(),
                            center.getY() + new WaveCreator(1.0, 2.0, 0.0).get(0.01, count[0] < 20),
                            auraBot_zPos(time[0], rangeTmp + speed) + center.getZ(),
                            (float) ypp.getStatic(),
                            (float) ypp.get(4.5, false)
                    );

                    NPC.setLocation(n, target);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityTeleport(target));

                    NPC.setArmor(player, target, arm);
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            Bukkit.getOnlinePlayers().parallelStream().filter(p -> p.hasPermission("psac.viewnpc")).forEachOrdered(p -> {
                                PlayerConnection c = ((CraftPlayer) p).getHandle().playerConnection;
                                c.sendPacket(new PacketPlayOutEntityTeleport(target));
                                NPC.setArmor(p, target, arm);
                            });
                            this.cancel();
                        }
                    }.runTask(PeyangSuperbAntiCheat.getPlugin());
                    count[0]++;
                    CheatDetectNowMeta meta = PeyangSuperbAntiCheat.cheatMeta.getMetaByPlayerUUID(player.getUniqueId());
                    if (meta == null) continue;
                    meta.addSeconds(((isLooking(player, n) || isLooking(player, n.clone().add(0, 1, 0))) ? (PeyangSuperbAntiCheat.config.getLong("npc.seconds") * 0.1 / 2): 0.0));
                }
                time[0] += PeyangSuperbAntiCheat.config.getDouble("npc.time") + (PeyangSuperbAntiCheat.config.getBoolean("npc.speed.wave") ? new WaveCreator(0.0, PeyangSuperbAntiCheat.config.getDouble("npc.speed.waveRange"), 0 - PeyangSuperbAntiCheat.config.getDouble("npc.speed.waveRange")).get(0.001, true): 0.0);
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
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * (PeyangSuperbAntiCheat.config.getLong("npc.seconds")));

    }

    private static double auraBot_zPos(double time, double radius)
    {
        return Math.sin(time) * radius * Math.cos(Math.PI / 180 * 360.0);
    }

    private static double auraBot_xPos(double time, double radius)
    {
        return Math.cos(time) * radius;
    }

}
