package ml.peya.plugins.Bukkit.Enum;

import org.bukkit.command.*;

/**
 * 検出タイプ
 */
public enum DetectType
{
    /**
     * AuraBot NPCを示す。
     */
    AURA_BOT("AuraBot"),
    /**
     * AuraPanic NPCを示す。
     */
    AURA_PANIC("AuraPanic"),
    /**
     * TeskKnockbackを示す。
     */
    ANTI_KB("TestKB");

    /**
     * 名前。
     */
    private final String name;
    /**
     * 回数。
     */
    private int count;
    /**
     * イベントsender。
     */
    private CommandSender sender;

    /**
     * コンストラクター。
     *
     * @param name 名前。
     */
    DetectType(String name)
    {
        count = 5;
        sender = null;
        this.name = name;
    }

    /**
     * 名前のゲッター。
     *
     * @return 名前。
     */
    public String getName()
    {
        return name;
    }

    /**
     * 回数のゲッター。
     *
     * @return 回数。
     */
    public int getPanicCount()
    {
        return count;
    }

    /**
     * 回数のセッター。
     *
     * @param time 時間(?)。
     */
    public void setPanicCount(int time)
    {
        count = time;
    }

    /**
     * イベントsenderのゲッター。
     *
     * @return イベントsender。
     */
    public CommandSender getSender()
    {
        return sender;
    }

    /**
     * イベントsenderのセッター。
     *
     * @param sender イベントsender。
     */
    public void setSender(CommandSender sender)
    {
        this.sender = sender;
    }
}
