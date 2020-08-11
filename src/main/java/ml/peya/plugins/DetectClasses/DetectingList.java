package ml.peya.plugins.DetectClasses;

import ml.peya.plugins.Enum.*;
import org.bukkit.entity.*;

import java.util.*;

/**
 * チート判定管理クラス
 */
public class DetectingList
{
    /**
     * 検出をまとめたメタをまとめるArrayListを定義する。
     */
    private final ArrayList<CheatDetectNowMeta> meta = new ArrayList<>();

    /**
     * サイズを取得する。
     *
     * @return サイズ。
     */
    public int getSize()
    {
        return meta.size();
    }

    /**
     * リストに追加する。
     *
     * @param target ターゲット。
     * @param npc    NPCのUUID。
     * @param id     管理ID。
     * @param type   判定タイプ。
     * @return 順次メソッド...に見える時代もありました。
     */
    public CheatDetectNowMeta add(Player target, UUID npc, int id, DetectType type)
    {
        CheatDetectNowMeta meta = new CheatDetectNowMeta(target, npc, id, type);
        this.meta.add(meta);
        return meta;
    }

    /**
     * metaをそのまんま返す。
     *
     * @return meta。
     */
    public ArrayList<CheatDetectNowMeta> getMetas()
    {
        return meta;
    }

    /**
     * metaからUUIDを消す。
     *
     * @param uuid 消すUUID。
     */
    public void remove(UUID uuid)
    {
        this.meta.removeIf(meta -> meta.getUUIDs() == uuid);
    }

    /**
     * metaをUUIDから手に入れる。
     *
     * @param uuid UUID。
     * @return meta。
     */
    public CheatDetectNowMeta getMetaByUUID(UUID uuid)
    {
        return meta.parallelStream().filter(meta -> meta.getUUIDs() == uuid).findFirst().orElse(null);
    }

    /**
     * metaを別のUUIDから手に入れる。
     *
     * @param uuid UUID。
     * @return meta。
     */
    public CheatDetectNowMeta getMetaByPlayerUUID(UUID uuid)
    {
        return meta.parallelStream().filter(meta -> meta.getTarget().getUniqueId() == uuid).findFirst().orElse(null);
    }

    /**
     * UUIDとして存在するかどうか。
     *
     * @param uuid UUID。
     * @return 存在したらtrue。
     */
    public boolean exists(UUID uuid)
    {
        return this.getMetaByPlayerUUID(uuid) != null;
    }
}
