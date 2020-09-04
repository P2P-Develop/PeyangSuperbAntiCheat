package ml.peya.plugins;

import com.mojang.authlib.properties.*;
import ml.peya.plugins.Utils.*;
import net.md_5.bungee.api.chat.*;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.lang.*;
import org.apache.commons.lang3.tuple.*;
import org.bukkit.Material;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.metadata.*;
import org.bukkit.scheduler.*;

import static ml.peya.plugins.Variables.*;

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
     * 痛いっ！の時のダメージをたまに無効化します。
     *
     * @param e 痛かったよぉ...
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent e)
    {
        if (!(e.getEntity() instanceof CraftPlayer) || !(e.getDamager() instanceof CraftArrow) || !cheatMeta.exists(e.getEntity().getUniqueId()) || !e.getDamager().hasMetadata("testArrow-" + e.getDamager().getUniqueId()))
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
        Player player = e.getPlayer();

        tracker.remove(player.getName());

        mods.remove(player.getUniqueId());
    }

    /**
     * プレイヤーが動いたらこれやってくれます。動くだけってかなり世紀末だよね。
     *
     * @param e 動いたときに発行されるイベントハンドラ。これ毎回発行してんのか...
     */
    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        if (!tracker.isTrackingByPlayer(e.getPlayer().getName()) && !cheatMeta.exists(e.getPlayer().getUniqueId()))
            return;
        e.getPlayer().setMetadata("speed", new FixedMetadataValue(PeyangSuperbAntiCheat.getPlugin(), e.getFrom().distance(e.getTo())));
    }

    /**
     * 誰かさんがお話したがってるときに動くイベントハンドラ。
     *
     * @param e 非同期でチャットイベントを送ってくれます。こういうところやさしい。
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e)
    {
        e.setCancelled(true);
        String format = e.getFormat().replace("%1$s", e.getPlayer().getDisplayName()).replace("%2$s", e.getMessage());

        ComponentBuilder builder = new ComponentBuilder("");

        builder.append(ChatColor.RED + "[" + ChatColor.YELLOW + "➤" + ChatColor.RESET + ChatColor.RED + "] ");
        builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/target " + e.getPlayer().getName()));
        builder.event(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ChatColor.RED + "Target " + e.getPlayer().getName()).create()
        ));

        e.getRecipients().parallelStream().forEach(receiver -> {
            if (!receiver.hasPermission("psac.chattarget") || (mods.get(receiver.getUniqueId()) != null && mods.get(receiver.getUniqueId()).containsKey("Lynx")))
                receiver.sendMessage(format);
            else
                receiver.spigot().sendMessage((BaseComponent[]) ArrayUtils.addAll(builder.create(), new ComponentBuilder(format).create()));
        });
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
        Player p = e.getPlayer();

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                p.sendPluginMessage(PeyangSuperbAntiCheat.getPlugin(), "FML|HS", new byte[]{-2, 0});
                p.sendPluginMessage(PeyangSuperbAntiCheat.getPlugin(), "FML|HS", new byte[]{0, 2, 0, 0, 0, 0});
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 5);

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
        if (e.getItemDrop().getItemStack().getType() == Material.WRITTEN_BOOK && Books.hasPSACBook(e.getItemDrop().getItemStack()))
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
    }
}

