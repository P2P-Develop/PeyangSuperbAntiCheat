package ml.peya.plugins.Utils;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.entity.*;

import java.util.*;

public class CheatDetectNowMeta
{
    private  Player target;
    private  UUID uuids;
    private  int id;
    private int VL;
    private boolean canNPC = false;

    public CheatDetectNowMeta(Player target, UUID uuids, int id)
    {
        this.target = target;
        this.uuids = uuids;
        this.id = id;
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
}
