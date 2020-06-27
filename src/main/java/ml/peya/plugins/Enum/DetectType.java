package ml.peya.plugins.Enum;

import org.bukkit.command.*;

public enum DetectType
{
    AURA_BOT,
    AURA_PANIC,
    ANTI_KB;

    private int count;
    private CommandSender sender;

    DetectType ()
    {
        count = 5;
        sender = null;
    }

    public void getPanicCount(int time)
    {
        this.count = time;
    }

    public int getPanicCount()
    {
        return count;
    }

    public void setSender(CommandSender sender)
    {
        this.sender = sender;
    }

    public CommandSender getSender()
    {
        return sender;
    }
}
