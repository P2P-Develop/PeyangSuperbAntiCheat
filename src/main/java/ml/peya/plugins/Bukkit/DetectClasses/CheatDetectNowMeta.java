package ml.peya.plugins.Bukkit.DetectClasses;

import ml.peya.plugins.Bukkit.Enum.*;
import org.bukkit.entity.*;

import java.util.*;

/**
 * テスト状態管理
 */
public class CheatDetectNowMeta
{
    /**
     * ターゲット。
     */
    private final Player target;
    /**
     * UUID。
     */
    private final UUID uuids;
    /**
     * 管理ID。
     */
    private final int id;
    /**
     * 判定タイプ。
     */
    private final DetectType type;
    /**
     * 秒数。
     */
    private double seconds;
    /**
     * VL。
     */
    private int VL;
    /**
     * テスト中かどうか。
     */

    private boolean isTesting = false;

    /**
     * コンストラクター。
     *
     * @param target ターゲット。
     * @param uuids  UUID。
     * @param id     管理ID。
     * @param type   判定タイプ。
     */
    public CheatDetectNowMeta(Player target, UUID uuids, int id, DetectType type)
    {
        this.target = target;
        this.uuids = uuids;
        this.id = id;
        this.type = type;
    }

    /**
     * テスト中かどうかのゲッター。
     *
     * @return テスト可能かどうか。
     */
    public boolean isTesting()
    {
        return isTesting;
    }

    /**
     * テスト中かどうかのセッター。
     *
     * @param isTesting テスト中かどうか。
     */
    public void setTesting(boolean isTesting)
    {
        this.isTesting = isTesting;
    }

    /**
     * ターゲットのゲッター。
     *
     * @return ターゲット。
     */
    public Player getTarget()
    {
        return target;
    }

    /**
     * UUIDのゲッター。
     *
     * @return UUID。
     */
    public UUID getUUIDs()
    {
        return uuids;
    }

    /**
     * VLをそのまんま追加する。
     *
     * @return 追加したVL。
     */
    public int addVL()
    {
        return ++VL;
    }

    /**
     * VLをそのまんま減らす。
     *
     * @return 減らしたVL。
     */
    public int removeVL()
    {
        return --VL;
    }

    /**
     * VLのゲッター。
     *
     * @return VL。
     */
    public int getVL()
    {
        return VL;
    }

    /**
     * 管理IDのゲッター。
     *
     * @return 管理ID。
     */
    public int getId()
    {
        return id;
    }

    /**
     * 判定タイプのゲッター。
     *
     * @return 判定タイプ。
     */
    public DetectType getType()
    {
        return type;
    }

    /**
     * 秒数のゲッター。
     *
     * @return 秒数。
     */
    public double getSeconds()
    {
        return seconds;
    }

    /**
     * 秒数のセッター。
     *
     * @param seconds 秒数。
     */
    public void setSeconds(double seconds)
    {
        this.seconds = seconds;
    }

    /**
     * 秒数の追加。
     *
     * @param seconds 追加した秒数。
     */
    public void addSeconds(double seconds)
    {
        this.seconds += seconds;
    }
}
