package ml.peya.plugins.Utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import ml.peya.plugins.Moderate.BanWithEffect;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Variables.skin;

/**
 * プレイヤーに関してまとめたやつ！
 */
public class PlayerUtils
{
    /**
     * 誰が見てるのかわかるやつ。
     *
     * @param player 見られてるプレイヤー。
     * @return 見てるプレイヤー。
     */
    public static Player getLookingEntity(Player player)
    {
        for (Location location : player.getLineOfSight(null, 4).parallelStream().map(Block::getLocation)
            .collect(Collectors.toCollection(ArrayList::new)))
            for (Entity entity : player.getNearbyEntities(3.5, 3.5, 3.5))
                if (isLooking((Player) entity, location) && entity.getType() == EntityType.PLAYER)
                    return (Player) entity;

        return null;
    }

    /**
     * 今見てるかわかるやつ。
     *
     * @param player   見られてるプレイヤー。
     * @param location あと場所。
     * @return 見られてたらtrue。
     */
    public static boolean isLooking(Player player, Location location)
    {
        BlockIterator it = new BlockIterator(player, 4);

        while (it.hasNext())
        {
            final Block block = it.next();
            if (block.getX() == location.getBlockX() &&
                block.getY() == location.getBlockY() &&
                block.getZ() == location.getBlockZ())
                return true;
        }
        return false;
    }

    /**
     * めっちゃクリティカルされたよぉふえええええぇぇぇっていうの確認するやつ
     *
     * @param player クリティカルゥ！プレイヤー。
     * @return クリティカル警察が反応したらtrueを返してくれます。
     */
    public static boolean hasCritical(Player player)
    {
        return player.getFallDistance() > 0.0F &&
            !player.getLocation().getBlock().isLiquid() &&
            !player.isOnGround() &&
            !player.hasPotionEffect(PotionEffectType.BLINDNESS) &&
            player.getVehicle() == null;
    }

    /**
     * ワールド内に新しいプレイヤーを捏造して、{@code EntityPlayer}として返します。
     *
     * @param world ワールドのハンドルを取得するための引数。
     * @return 創造されたプレイヤー。
     */
    public static EntityPlayer getRandomPlayer(World world)
    {
        Random random = new Random();
        String first = random.nextBoolean()
            ? RandomStringUtils.randomAlphanumeric(new Random().nextInt(13) + 1)
            : RandomWordUtils.getRandomWord();
        String last = random.nextBoolean()
            ? RandomStringUtils.randomAlphanumeric(new Random().nextInt(13) + 1)
            : RandomWordUtils.getRandomWord();

        if (random.nextBoolean())
        {
            first = develop.p2p.lib.LeetConverter.convert(first);
            last = develop.p2p.lib.LeetConverter.convert(last);
        }

        String name = first + (random.nextBoolean() ? "_": "") + last + (random.nextBoolean() ? "19" + random.nextInt(120): "");
        if (name.length() > 16)
            name = random.nextBoolean() ? first: last;

        if (name.length() > 16)
            name = RandomStringUtils.randomAlphanumeric(random.nextInt(16));

        WorldServer worldServer = ((CraftWorld) world).getHandle();

        Pair<String, String> skin = getRandomSkin();

        GameProfile profile = new GameProfile(UUID.randomUUID(), name);

        profile.getProperties().put("textures", new Property("textures", skin.getLeft(), skin.getRight()));

        return new EntityPlayer(
            ((CraftServer) Bukkit.getServer()).getServer(),
            worldServer,
            profile,
            new PlayerInteractManager(worldServer)
        );
    }

    /**
     * ランダムスキンをパパラッチします。
     *
     * @return すきん
     */
    public static Pair<String, String> getRandomSkin()
    {
        try (Connection connection = skin.getConnection();
             Statement statement = connection.createStatement())
        {
            ResultSet result = statement.executeQuery("SELECT Texture, Signature FROM Skin ORDER BY RANDOM() LIMIT 1");
            return !result.next()
                ? Pair.of("", "")
                : Pair.of(result.getString("Texture"), result.getString("Signature"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            return Pair.of("", "");
        }
    }

    /**
     * プレイヤーを取得する。たぶん。
     *
     * @param sender イベントセンダー。
     * @param name   なまえ
     * @return Playerの取得に失敗した場合null。
     */
    @Nullable
    public static Player getPlayer(CommandSender sender, String name)
    {
        Player player = Bukkit.getPlayer(name);

        if (player == null)
        {
            sender.sendMessage(get("error.playerNotFound"));

            return null;
        }
        return player;
    }

    /**
     * プレイヤーを取得します(オフラインでも可)
     *
     * @return 取得したプレイヤー
     */
    public static Player getPlayerAllowOffline(String playerName)
    {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null)
        {
            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers())
            {
                if (offlinePlayer.getName().toLowerCase().equals(playerName.toLowerCase()))
                    player = offlinePlayer.getPlayer();
            }
        }

        return player;
    }

    /**
     * オフラインプレイヤーを取得します
     *
     * @return 取得したプレイヤー
     */
    public static OfflinePlayer getOfflinePlayer(String playerName)
    {
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers())
        {
            if (offlinePlayer.getName().toLowerCase().equals(playerName.toLowerCase()))
                return offlinePlayer;
        }
        return null;
    }

    /**
     * プレイヤーのGGなIDを生成します
     *
     * @param seed シード値
     * @return GGなID
     */
    public static String getGGID(long seed)
    {
        StringBuilder builder = new StringBuilder();
        Random random = new Random(seed);
        for (int i = 0; i < 7; i++)
            builder.append(random.nextInt(9));
        return builder.toString();
    }

    /**
     * ログイン資格の審査をします。
     *
     * @param target ターゲット
     * @return kickメッセージ。Login可能なら空。
     */
    public static String preLoginPending(UUID target)
    {

        HashMap<String, String> banInfo = BanWithEffect.getBanInfo(target);


        if (!Boolean.parseBoolean(banInfo.get("banned")))
            return "";

        HashMap<String, Object> map = new HashMap<>();

        final String id = banInfo.get("id");

        map.put("reason", banInfo.get("reason"));
        map.put("ggid", getGGID(id.hashCode()));
        map.put("id", id);

        String message;

        if (banInfo.get("expire").equals("_PERM"))
            message = MessageEngine.get("ban.permReason", map);
        else
        {
            long time;
            try
            {
                time = Long.parseLong(banInfo.get("expire"));
            }
            catch (Exception ignored)
            {
                return "";
            }

            Date date = new Date(time);
            if (date.before(new Date()))
                return "";

            map.put("date", TimeParser.convertFromDate(date));
            message = MessageEngine.get("ban.tempReason", map);
        }

        return message;
    }

}
