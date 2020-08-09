package ml.peya.plugins.Utils;

import com.fasterxml.jackson.databind.*;
import com.mojang.authlib.*;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.lang.*;
import org.bukkit.World;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.*;

import java.util.*;

/**
 * プレイヤーをランダムに取得する関数が突っ込まれています。
 * プレイヤー誰かが餌食になります。
 */
public class RandomPlayer
{
    /**
     * ワールド内にいるプレイヤーどなたかを餌食にして{@code EntityPlayer}として返します。
     *
     * @param world ワールドのハンドルを取得するための引数。
     * @return 餌食になったプレイヤー。
     */
    public static EntityPlayer getPlayer(World world)
    {
        JsonNode node = Utils.getRandomUser();

        String first = node == null ? RandomStringUtils.randomAlphanumeric(new Random().nextInt(13) + 1): node.get("results").get(0).get("name").get("first").asText();
        String last = node == null ? RandomStringUtils.randomAlphanumeric(new Random().nextInt(13) + 1): node.get("results").get(0).get("name").get("last").asText();

        Random random = new Random();

        first = first.replaceAll("[^a-zA-Z0-9]", RandomStringUtils.randomAlphanumeric(0));
        last = last.replaceAll("[^a-zA-Z0-9]", RandomStringUtils.randomAlphanumeric(0));
        if (random.nextBoolean())
        {
            first = develop.p2p.lib.LeetConverter.convert(first);
            last = develop.p2p.lib.LeetConverter.convert(last);
        }

        String name = first + (random.nextBoolean() ? "_": "") + last + (random.nextBoolean() ? "19" + random.nextInt(120): "");
        if (name.length() > 14)
            name = random.nextBoolean() ? first: last;

        return new EntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld) world).getHandle(), new GameProfile(node != null ? UUID.fromString(node.get("results").get(0).get("login").get("uuid").asText()): UUID.randomUUID(), name), new PlayerInteractManager(((CraftWorld) world).getHandle()));
    }

}
