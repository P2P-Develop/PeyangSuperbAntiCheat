package ml.peya.plugins.Detect;

import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.craftbukkit.v1_12_R1.inventory.*;
import org.bukkit.craftbukkit.v1_12_R1.scoreboard.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.util.*;

/**
 * NPC自体である。
 */
public class NPC
{
    /**
     * 場所を設定する。
     *
     * @param location 場所。
     * @param player   プレイヤー・
     */
    public static void setLocation(Location location, EntityPlayer player)
    {
        player.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    /**
     * スポーンさせる。
     *
     * @param player       プレイヤー。
     * @param teleportCase 判定タイプ。
     * @param reachMode    リーチモードかどうか。
     * @return スポーンするNPCを返す。
     */
    public static EntityPlayer spawn(Player player, DetectType teleportCase, boolean reachMode)
    {
        EntityPlayer npc = PlayerUtils.getRandomPlayer(player.getWorld());

        Location center = player.getLocation();

        if (center.getPitch() <= 0.0f || center.getPitch() > 15.0f)
            center.setPitch(0.0f);

        setLocation(player.getLocation().add(0, 1, 0).add(center.getDirection().multiply(-3)), npc);

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

                ScoreboardTeam team = new ScoreboardTeam((((CraftScoreboard) Bukkit.getScoreboardManager().getMainScoreboard()).getHandle()), player.getName());

                team.setPrefix(EnumChatFormat.RED.toString());

                connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 1));
                connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 0));
                connection.sendPacket(new PacketPlayOutScoreboardTeam(team, Collections.singleton(npc.getName()), 3));

                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));

                player.hidePlayer(PeyangSuperbAntiCheat.getPlugin(), npc.getBukkitEntity());

                Bukkit.getOnlinePlayers().parallelStream().filter(p -> p.hasPermission("psac.viewnpc")).map(p -> ((CraftPlayer) p).getHandle().playerConnection).forEachOrdered(c -> {
                    c.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
                    c.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
                });

                NPCTeleport.teleport(player, npc, arm, teleportCase, reachMode);

                player.hidePlayer(PeyangSuperbAntiCheat.getPlugin(), npc.getBukkitEntity());

                Bukkit.getOnlinePlayers().parallelStream().filter(p -> p.hasPermission("psac.viewnpc")).map(p -> ((CraftPlayer) p).getHandle().playerConnection).forEachOrdered(c -> c.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc)));

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getBukkitEntity().getEntityId()));
                        Bukkit.getOnlinePlayers().parallelStream().filter(p -> p.hasPermission("psac.viewnpc")).map(p -> ((CraftPlayer) p).getHandle().playerConnection).forEachOrdered(c -> c.sendPacket(new PacketPlayOutEntityDestroy(npc.getBukkitEntity().getEntityId())));
                    }
                }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), (Math.multiplyExact(Variables.config.getInt("npc.seconds"), 20)));
            }
        }.runTask(PeyangSuperbAntiCheat.getPlugin());

        return npc;
    }

    /**
     * ことごとくランダムアーマーを着せる。
     *
     * @param target ターゲット。
     * @param player プレイヤー。
     * @param arm    腕？？？？
     */
    public static void setArmor(Player target, EntityPlayer player, ItemStack[] arm)
    {
        Arrays.asList(new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.HEAD, arm[0]), new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.CHEST, arm[1]), new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.LEGS, arm[2]), new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.FEET, arm[3]), new PacketPlayOutEntityEquipment(player.getBukkitEntity().getEntityId(), EnumItemSlot.MAINHAND, arm[4])).parallelStream().forEachOrdered(((CraftPlayer) target).getHandle().playerConnection::sendPacket);
    }
}

