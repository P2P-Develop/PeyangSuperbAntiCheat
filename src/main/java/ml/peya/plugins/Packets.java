package ml.peya.plugins;

import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.*;
import com.fasterxml.jackson.databind.*;
import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;

import javax.net.ssl.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

public class Packets
{
    public void useEntity(PacketEvent e)
    {
        try
        {
            PacketContainer packet = e.getPacket();

            PacketPlayInUseEntity entity = (PacketPlayInUseEntity) packet.getHandle();
            Field field = entity.getClass().getDeclaredField("a");
            field.setAccessible(true);
            int entityId = field.getInt(entity);
            if (e.getPacket().getEntityUseActions().readSafely(0) != EnumWrappers.EntityUseAction.ATTACK) return;
            DetectingList metas = PeyangSuperbAntiCheat.cheatMeta;
            for (CheatDetectNowMeta meta : metas.getMetas())
            {

                if (meta.getId() != entityId)
                    return;
                if (meta.getTarget().getUniqueId() == e.getPlayer().getUniqueId())
                    System.out.println(meta.addVL());
                if (Criticals.hasCritical(e.getPlayer()))
                    System.out.println(meta.addVL());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void playerInfo(PacketEvent e)
    {
        if (e.getPacket().getPlayerInfoAction().read(0) != EnumWrappers.PlayerInfoAction.ADD_PLAYER)
            return;

        PlayerInfoData infoData = e.getPacket().getPlayerInfoDataLists().read(0).get(0);

        if (PeyangSuperbAntiCheat.cheatMeta.getMetaByUUID(infoData.getProfile().getUUID()) == null)
            return;

        PlayerInfoData newInfo = new PlayerInfoData(infoData.getProfile().withName(ChatColor.RED + infoData.getProfile().getName()),
                0,
                infoData.getGameMode(),
                WrappedChatComponent.fromText(ChatColor.RED + infoData.getProfile().getName()));
        List<String> uuids = PeyangSuperbAntiCheat.config.getStringList("skins");
        Random random = new Random(infoData.getProfile().getUUID().hashCode());
        JsonNode nodeb = getSkin(uuids.get(random.nextInt(uuids.size() - 1)));
        newInfo.getProfile().getProperties().put("textures", new WrappedSignedProperty("textures",
                Objects.requireNonNull(nodeb).get("properties").get(0).get("value").asText(),
                nodeb.get("properties").get(0).get("signature").asText()));

        e.getPacket().getPlayerInfoDataLists().write(0, Collections.singletonList(newInfo));
    }

    public static JsonNode getSkin(String uuid)
    {
        try
        {
            HttpsURLConnection connection;
            connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", uuid)).openConnection();

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
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
            return null;
        }
    }

}
