package ml.peya.plugins.DetectClasses;

import ml.peya.plugins.Enum.*;
import org.bukkit.entity.*;

import java.util.*;

public class CheatDetectNowMeta
{
    private final Player target;
    private final UUID uuids;
    private final int id;
    private final DetectType type;
    private double seconds;
    private int VL;
    private boolean canTesting = false;

    public CheatDetectNowMeta(Player target, UUID uuids, int id, DetectType type)
    {
        this.target = target;
        this.uuids = uuids;
        this.id = id;
        this.type = type;
    }

    public boolean isCanTesting()
    {
        return canTesting;
    }

    public void setCanTesting(boolean canTesting)
    {
        this.canTesting = canTesting;
    }

    public Player getTarget()
    {
        return target;
    }

    public UUID getUUIDs()
    {
        return uuids;
    }

    public int addVL()
    {
        VL++;
        return VL;
    }

    public int removeVL()
    {
        VL--;
        return VL;
    }

    public int getVL()
    {
        return VL;
    }

    public int getId()
    {
        return id;
    }

    public DetectType getType()
    {
        return type;
    }

    public double getSeconds()
    {
        return seconds;
    }

    public void setSeconds(double seconds)
    {
        this.seconds = seconds;
    }

    public void addSeconds(double seconds)
    {
        this.seconds += seconds;
    }
}
