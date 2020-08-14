package ml.peya.plugins.Bukkit.DetectClasses;

import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.*;
import ml.peya.plugins.Bukkit.*;
import ml.peya.plugins.Bukkit.Utils.*;
import net.minecraft.server.v1_12_R1.*;

import java.lang.reflect.*;

/**
 * サーバーとプラグイン間とのパケットを管理します。
 * たまに編集します。
 */
public class Packets
{

    /**
     * パケットイベント処理を餌食に、主にVL評価に使います。
     *
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
            for (CheatDetectNowMeta meta : Variables.cheatMeta.getMetas())
            {
                if (meta.getId() != field.getInt(entity))
                    continue;
                if (meta.getTarget().getUniqueId() == e.getPlayer().getUniqueId() || Criticals.hasCritical(e.getPlayer()))
                    System.out.println(meta.addVL());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
