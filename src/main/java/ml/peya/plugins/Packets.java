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

/**
 * サーバーとプラグイン間とのパケットを管理します。
 * たまに編集します。
 */
public class Packets
{
    /** 指定されたUUIDのプレイヤーのスキンをパパラッチします。
     * @param uuid 指定するUUID。
     *
     * @return パパラッチしたスキンをJsonNodeに変換したやつ。
     */
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

    /** パケットイベント処理を餌食に、主にVL評価に使います。
     * @param e 餌食にするパケットイベント。
     */
    public static void useEntity(PacketEvent e)
    {
        try
        {
            PacketContainer packet = e.getPacket();

            PacketPlayInUseEntity entity = (PacketPlayInUseEntity) packet.getHandle();
            Field field = entity.getClass().getDeclaredField("a");
            field.setAccessible(true);
            if (e.getPacket().getEntityUseActions().readSafely(0) != EnumWrappers.EntityUseAction.ATTACK) return;
            for (CheatDetectNowMeta meta : PeyangSuperbAntiCheat.cheatMeta.getMetas())
            {
                if (meta.getId() != field.getInt(entity))
                    return;
                if (meta.getTarget().getUniqueId() == e.getPlayer().getUniqueId() || Criticals.hasCritical(e.getPlayer()))
                    System.out.println(meta.addVL());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /** プレイヤー情報をパケットに乗せて送るそうです。
     * @param e 乗車()するパケットイベント。
     */
    public static void playerInfo(PacketEvent e)
    {
        if (e.getPacket().getPlayerInfoAction().read(0) != EnumWrappers.PlayerInfoAction.ADD_PLAYER)
            return;

        PlayerInfoData infoData = e.getPacket().getPlayerInfoDataLists().read(0).get(0);

        if (PeyangSuperbAntiCheat.cheatMeta.getMetaByUUID(infoData.getProfile().getUUID()) == null)
            return;

        PlayerInfoData newInfo = new PlayerInfoData(
                infoData.getProfile().withName(ChatColor.RED + infoData.getProfile().getName()),
                0,
                infoData.getGameMode(),
                WrappedChatComponent.fromText(ChatColor.RED + infoData.getProfile().getName())
        );
        List<String> uuids = PeyangSuperbAntiCheat.config.getStringList("skins");
        JsonNode nodeb = getSkin(uuids.get(new Random(infoData.getProfile().getUUID().hashCode()).nextInt(uuids.size() - 1)));
        newInfo.getProfile().getProperties().put("textures", new WrappedSignedProperty(
                "textures",
                Objects.requireNonNull(nodeb).get("properties").get(0).get("value").asText(),
                nodeb.get("properties").get(0).get("signature").asText()
        ));

        e.getPacket().getPlayerInfoDataLists().write(0, Collections.singletonList(newInfo));
    }

}
