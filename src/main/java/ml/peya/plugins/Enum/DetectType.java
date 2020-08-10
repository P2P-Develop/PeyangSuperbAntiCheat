package ml.peya.plugins.Enum;

import org.bukkit.command.*;

/**
 * Doc(ry
 */
public enum DetectType
{
    AURA_BOT("AuraBot"),
    AURA_PANIC("AuraPanic"),
    ANTI_KB("TestKB");

    private final String name;
    private int count;
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
