package ml.peya.plugins.Utils;

import ml.peya.plugins.Enum.*;
import org.bukkit.entity.*;

import java.util.*;

public class CheatDetectNowMeta
{
    private  Player target;
    private  UUID uuids;
    private  int id;
    private int VL;
    private boolean canNPC = false;
    private DetectType type;

    public CheatDetectNowMeta(Player target, UUID uuids, int id, DetectType type)
    {
        this.target = target;
        this.uuids = uuids;
        this.id = id;
        this.type = type;
    }

    public boolean isCanNPC()
    {
        return canNPC;
    }

    public void setCanNPC(boolean canNPC)
    {
        this.canNPC = canNPC;
    }

    public Player getTarget()
    {
        return target;
    }

    public UUID getUuids()
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
}
