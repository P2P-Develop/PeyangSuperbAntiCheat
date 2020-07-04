package ml.peya.plugins.Enum;

import org.bukkit.command.*;

import java.util.*;

public enum DetectType
{
    AURA_BOT("AuraBot"),
    AURA_PANIC("AuraPanic"),
    ANTI_KB("TestKB");

    private int count;
    private CommandSender sender;
    private String name;
    private HashMap<String, Object> meta;

    DetectType (String name)
    {
        count = 5;
        sender = null;
        this.name = name;
    }

    public String getName()
    {
        return name;
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

    public HashMap<String, Object> getMeta()
    {
        return meta;
    }

    public void setMeta(HashMap<String, Object> meta)
    {
        this.meta = meta;
    }
}
