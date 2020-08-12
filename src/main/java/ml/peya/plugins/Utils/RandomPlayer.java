package ml.peya.plugins.Utils;

import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import ml.peya.plugins.*;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.lang.*;
import org.apache.commons.lang3.tuple.*;
import org.bukkit.World;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.*;

import java.sql.*;
import java.util.*;

/**
 * プレイヤーをランダムに取得する関数が突っ込まれています。
 */
public class RandomPlayer
{
    /**
     * ワールド内に新しいプレイヤーを捏造して、{@code EntityPlayer}として返します。
     *
     * @param world ワールドのハンドルを取得するための引数。
     * @return 創造されたプレイヤー。
     */
    public static EntityPlayer getPlayer(World world)
    {
        Random random = new Random();
        String first = random.nextBoolean() ? RandomStringUtils.randomAlphanumeric(new Random().nextInt(13) + 1): RandomWordUtils.getRandomWord();
        String last = random.nextBoolean() ? RandomStringUtils.randomAlphanumeric(new Random().nextInt(13) + 1): RandomWordUtils.getRandomWord();

        if (random.nextBoolean())
        {
            first = develop.p2p.lib.LeetConverter.convert(first);
            last = develop.p2p.lib.LeetConverter.convert(last);
        }

        String name = first + (random.nextBoolean() ? "_": "") + last + (random.nextBoolean() ? "19" + random.nextInt(120): "");
        if (name.length() > 16)
            name = random.nextBoolean() ? first: last;

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = ((CraftWorld) world).getHandle();
        PlayerInteractManager manager = new PlayerInteractManager(worldServer);

        Pair<String, String> skin = getSkin();

        GameProfile profile = new GameProfile(UUID.randomUUID(), name);

        profile.getProperties().put("textures", new Property("textures", skin.getLeft(), skin.getRight()));

        return new EntityPlayer(server, worldServer, profile, manager);
    }

    /**
     * ランダムスキンをパパラッチします。
     *
     * @return すきん
     */
    public static Pair<String, String> getSkin()
    {
        try (Connection connection = Variables.skin.getConnection();
             Statement statement = connection.createStatement())
        {
            ResultSet result = statement.executeQuery("SELECT * FROM Skin ORDER BY RANDOM() LIMIT 1");
            if (!result.next())
                return Pair.of("", "");
            return Pair.of(result.getString("Texture"), result.getString("Signature"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            return Pair.of("", "");
        }
    }
}
