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
     * プレイヤーからのNPC攻撃イベントを元に、VLを追加
     *
     * @param e 餌食にするパケットイベント。
     */
    public static void useEntity(PacketEvent e)
    {
        try
        {
            final PacketPlayInUseEntity entity = (PacketPlayInUseEntity) e.getPacket()
                    .getHandle();
            Field field = entity.getClass()
                    .getDeclaredField("a");
            field.setAccessible(true);
            if (e.getPacket()
                    .getEntityUseActions()
                    .readSafely(0) != EnumWrappers.EntityUseAction.ATTACK) return;
            cheatMeta.getMetas()
                    .parallelStream()
                    .filter(meta ->
                    {
                        try
                        {
                            return meta.getId() == field.getInt(entity) && meta.getTarget()
                                    .getUniqueId() == e.getPlayer()
                                    .getUniqueId() || PlayerUtils.hasCritical(e.getPlayer());
                        }
                        catch (IllegalAccessException ex)
                        {
                            ex.printStackTrace();
                        }
                        return false;
                    })
                    .forEachOrdered(meta -> System.out.println(meta.addVL()));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
