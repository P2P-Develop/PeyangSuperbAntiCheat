package ml.peya.plugins;

import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import sun.java2d.pipe.*;

import java.lang.reflect.*;
import java.util.*;

public class Packets
{
    public void onPacketReceiving(PacketEvent e)
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
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void onPacketSending(PacketEvent e)
    {
        if(e.getPacket().getPlayerInfoAction().read(0) != EnumWrappers.PlayerInfoAction.ADD_PLAYER)
            return;

        PlayerInfoData infoData = e.getPacket().getPlayerInfoDataLists().read(0).get(0);

        if (PeyangSuperbAntiCheat.cheatMeta.getMetaByUUID(infoData.getProfile().getUUID()) == null)
            return;

        PlayerInfoData newInfo = new PlayerInfoData(infoData.getProfile().withName(ChatColor.RED + infoData.getProfile().getName()),
                0,
                infoData.getGameMode(),
                WrappedChatComponent.fromText(ChatColor.RED + infoData.getProfile().getName()));
        e.getPacket().getPlayerInfoDataLists().write(0, Collections.singletonList(newInfo));
    }
}
