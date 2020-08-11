package ml.peya.plugins.Utils;

import com.fasterxml.jackson.databind.*;
import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import ml.peya.plugins.*;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.lang.*;
import org.bukkit.World;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.*;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * プレイヤーをランダムに取得する関数が突っ込まれています。
 * プレイヤー誰かが餌食になります。
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

        List<String> uuids = PeyangSuperbAntiCheat.config.getStringList("skins");
        JsonNode skinNode = getSkin(uuids.get(new Random().nextInt(uuids.size() - 1)));


        GameProfile profile = new GameProfile(UUID.randomUUID(), name);

        if (skinNode != null)
            profile.getProperties().put("textures", new Property("textures", skinNode.get("properties").get(0).get("value").asText(),
                    skinNode.get("properties").get(0).get("signature").asText()
            ));

        return new EntityPlayer(server, worldServer, profile, manager);
    }

    /**
     * 指定されたUUIDのプレイヤーのスキンをパパラッチします。
     *
     * @param uuid 指定するUUID。
     * @return パパラッチしたスキンをJsonNodeに変換したやつ。
     */
    public static JsonNode getSkin(String uuid)
    {
        try
        {
            HttpsURLConnection connection;
            connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", uuid)).openConnection();

            connection.setReadTimeout(1500);

            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK)
            {
                PeyangSuperbAntiCheat.logger.info("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String readed = reader.readLine();
            while (readed != null)
            {
                builder.append(readed);
                readed = reader.readLine();
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(builder.toString());
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
