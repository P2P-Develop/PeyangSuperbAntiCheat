package ml.peya.plugins.DetectClasses;

import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.*;
import ml.peya.plugins.Utils.*;
import net.minecraft.server.v1_12_R1.*;

import java.lang.reflect.*;

import static ml.peya.plugins.Variables.cheatMeta;

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
            PacketPlayInUseEntity entity = (PacketPlayInUseEntity) e.getPacket().getHandle();
            Field field = entity.getClass().getDeclaredField("a");
            field.setAccessible(true);
            if (e.getPacket().getEntityUseActions().readSafely(0) != EnumWrappers.EntityUseAction.ATTACK) return;
            for (CheatDetectNowMeta meta : cheatMeta.getMetas())
            {
                if (meta.getId() == field.getInt(entity) && meta.getTarget().getUniqueId() == e.getPlayer().getUniqueId() || PlayerUtils.hasCritical(e.getPlayer()))
                    System.out.println(meta.addVL());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
