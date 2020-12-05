package ml.peya.plugins;

import com.mojang.authlib.properties.Property;
import ml.peya.plugins.Moderate.KickManager;
import ml.peya.plugins.Objects.Books;
import ml.peya.plugins.Utils.PlayerUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftArrow;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import static ml.peya.plugins.Variables.cheatMeta;
import static ml.peya.plugins.Variables.counting;
import static ml.peya.plugins.Variables.module;
import static ml.peya.plugins.Variables.tracker;

/**
 * イベントハンドラをめちゃくちゃ管理します。
 */
public class Events implements Listener
{
    /**
     * キルされたときにカウントを増やします。
     *
     * @param e キャー！どっかのプレイヤーが死んだー！っていうのを表すイベントハンドラ。
     */
    @EventHandler
    public void onKill(PlayerDeathEvent e)
    {
        if (e.getEntity().getKiller() == null)
            return;

        counting.kill(e.getEntity().getKiller().getUniqueId());
    }

    /**
     * 痛いっ！の時のTestKBの弓のダメージを
     * 私は親切なので無効化してあげます。
     *
     * @param e 痛かったよぉ...
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageByEntityEvent e)
    {
        if (!(e.getEntity() instanceof CraftPlayer) ||
            !(e.getDamager() instanceof CraftArrow) ||
            !cheatMeta.exists(e.getEntity().getUniqueId()) ||
            !e.getDamager().hasMetadata("testArrow-" + e.getDamager().getUniqueId()))
            return;

        e.setDamage(0);
    }

    /**
     * プレイヤーが立ち去ったらこれやってくれます。
     *
     * @param e プレイヤーが立ち去ったぞおおおおお！っていう時のイベントハンドラ。
     */
    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        tracker.remove(e.getPlayer().getName());
    }

    /**
     * プレイヤーが動いたらこれやってくれます。動くだけってかなり世紀末だよね。
     *
     * @param e 動いたときに発行されるイベントハンドラ。これ毎回発行してんのか...
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent e)
    {
        if (!tracker.isTrackingByPlayer(e.getPlayer().getName()) && !cheatMeta.exists(e.getPlayer().getUniqueId()))
            return;

        e.getPlayer().setMetadata(
            "speed",
            new FixedMetadataValue(PeyangSuperbAntiCheat.getPlugin(), e.getFrom().distance(e.getTo()))
        );
    }

    /**
     * 誰かさんがお話したがってるときに動くイベントハンドラ。
     *
     * @param e 非同期でチャットイベントを送ってくれます。こういうところやさしい。
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e)
    {
        if (e.isCancelled())
            return;

        e.setCancelled(true);

        final String format = e.getFormat()
            .replace("%1$s", e.getPlayer().getDisplayName())
            .replace("%2$s", e.getMessage());

        e.getRecipients()
            .parallelStream()
            .forEachOrdered(receiver ->
                receiver.spigot().sendMessage((BaseComponent[]) ArrayUtils.addAll(
                    new ComponentBuilder("").append(ChatColor.RED + "[" + ChatColor.YELLOW + "➤" + ChatColor.RESET + ChatColor.RED + "] ")
                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/target " + e.getPlayer().getName()))
                        .event(new HoverEvent(
                            HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(ChatColor.RED + "Target " + e.getPlayer().getName()).create()
                        )).create(),
                    new ComponentBuilder(format).create()
                )));
        Bukkit.getConsoleSender().sendMessage(format);
    }

    /**
     * プレイヤーが参上した時に処理が行われるやつ。
     *
     * @param e 三条市田尾(誰)
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        EntityPlayer tab = PlayerUtils.getRandomPlayer(e.getPlayer().getWorld());
        tab.getBukkitEntity().setPlayerListName(ChatColor.RED + tab.getName());
        PlayerConnection connection = ((CraftPlayer) e.getPlayer()).getHandle().playerConnection;

        new BukkitRunnable()
        {

            @Override
            public void run()
            {

                Pair<String, String> skin = PlayerUtils.getRandomSkin();

                tab.getProfile().getProperties().put(
                    "textures",
                    new Property(
                        "textures",
                        skin.getLeft(),
                        skin.getRight()
                    )
                );

                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, tab));
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 10);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, tab));
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20 * 3);
    }

    /**
     * アイテム落とした時のイベント。
     *
     * @param e ドロップゥ！
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDrop(PlayerDropItemEvent e)
    {
        if (e.getItemDrop().getItemStack().getType() == Material.WRITTEN_BOOK &&
            Books.hasPSACBook(e.getItemDrop().getItemStack()))
            e.setCancelled(true);
    }

    /**
     * プレイヤーが蹴られるときのやつ
     *
     * @param e キックゥ！
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onKick(PlayerKickEvent e)
    {
        if (e.getPlayer().hasMetadata("psac-kick"))
        {
            e.getPlayer().removeMetadata("psac-kick", PeyangSuperbAntiCheat.getPlugin());

            e.setLeaveMessage("");
        }

        if (!module.isEnable("Matrix"))
            return;

        if (e.getReason().startsWith(ChatColor.GRAY + "[" + ChatColor.AQUA + "Matrix" + ChatColor.GRAY + "]")) //Matrix Detection
        {
            e.setCancelled(true);
            KickManager.kickPlayer(e.getPlayer(), e.getReason().replaceFirst("§7\\[§bMatrix§7]§r ", ""), true, false);
        }
    }
}

