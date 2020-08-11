package ml.peya.plugins.DetectClasses;

import ml.peya.plugins.Enum.*;
import org.bukkit.entity.*;

import java.util.*;

/**
 * 万能クラス。
 */
public class CheatDetectNowMeta
{

    private final Player target;
    private final UUID uuids;
    private final int id;
    private final DetectType type;
    private double seconds;
    private int VL;
    private boolean canTesting = false;

    /**
     * コンストラクター。
     *
     * @param target ターゲット。
     * @param uuids  UUID。
     * @param id     管理ID。
     * @param type   罪状。
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
    public boolean isCanTesting()
    {
        return canTesting;
    }

    /**
     * テスト中かどうかのセッター。
     *
     * @param canTesting テスト可能かどうか。
     */
    public void setCanTesting(boolean canTesting)
    {
        this.canTesting = canTesting;
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
     * 罪状のゲッター。
     *
     * @return 罪状。
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
