package ml.peya.plugins.DetectClasses;

import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import ml.peya.plugins.Utils.PlayerUtils;
import net.minecraft.server.v1_12_R1.PacketPlayInUseEntity;

import java.lang.reflect.Field;

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
            if (e.getPacket().getEntityUseActions().readSafely(0) != EnumWrappers.EntityUseAction.ATTACK)
                return;

            final PacketPlayInUseEntity entity = (PacketPlayInUseEntity) e.getPacket().getHandle();
            Field field = entity.getClass().getDeclaredField("a");
            field.setAccessible(true);

            cheatMeta.getMetas()
                    .stream()
                    .filter(meta ->
                    {
                        try
                        {
                            return meta.getId() == field.getInt(entity) &&
                                    meta.getTarget().getUniqueId() == e.getPlayer().getUniqueId() ||
                                    PlayerUtils.hasCritical(e.getPlayer());
                        }
                        catch (IllegalAccessException ex)
                        {
                            ex.printStackTrace();
                        }
                        return false;
                    }).forEachOrdered(meta -> {
                if (meta.getNpcLocation() != null)
                    meta.addAngle(meta.getTarget().getEyeLocation().getDirection().angle(meta.getNpcLocation().subtract(meta.getTarget().getEyeLocation().toVector())));

                System.out.println(meta.addVL());
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
