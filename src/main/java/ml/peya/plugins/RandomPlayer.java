package ml.peya.plugins;

import com.fasterxml.jackson.databind.*;
import com.mojang.authlib.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.lang.*;
import org.bukkit.World;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.*;

import java.util.*;

public class RandomPlayer
{
    public static EntityPlayer getPlayer(World world)
    {
        String name;
        JsonNode node = StringUtil.getRandomUser();

        String first;
        String last;
        if (node == null)
        {
            first = RandomStringUtils.randomAlphanumeric(new Random().nextInt(13) + 1);
            last = RandomStringUtils.randomAlphanumeric(new Random().nextInt(13) + 1);
        }
        else
        {
            last = node.get("results").get(0).get("name").get("last").asText();
            first = node.get("results").get(0).get("name").get("first").asText();
        }


        first = first.replaceAll("[^a-zA-Z0-9]", RandomStringUtils.randomAlphanumeric(0));
        last = last.replaceAll("[^a-zA-Z0-9]", RandomStringUtils.randomAlphanumeric(0));

        Random random = new Random();

        if (random.nextBoolean())
        {
            first = develop.p2p.lib.LeetConverter.convert(first);
            last = develop.p2p.lib.LeetConverter.convert(last);
        }

        name = first + (random.nextBoolean() ? "_" : "") + last + (random.nextBoolean() ? "19" + random.nextInt(120) : "");
        if (name.length() > 14)
            name = random.nextBoolean() ? first : last;


        UUID uuid;
        if (node != null)
            uuid = UUID.fromString(node.get("results").get(0).get("login").get("uuid").asText());
        else
            uuid = UUID.randomUUID();

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = ((CraftWorld) world).getHandle();
        GameProfile profile = new GameProfile(uuid, name);

        PlayerInteractManager plMng = new PlayerInteractManager(worldServer);

        return new EntityPlayer(server, worldServer, profile, plMng);
    }

}
