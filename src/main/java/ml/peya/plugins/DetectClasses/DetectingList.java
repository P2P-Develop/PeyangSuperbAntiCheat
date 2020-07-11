package ml.peya.plugins.DetectClasses;

import ml.peya.plugins.Enum.*;
import org.bukkit.entity.*;

import java.util.*;

public class DetectingList
{
    private ArrayList<CheatDetectNowMeta> meta = new ArrayList<>();

    public int getSize()
    {
        return meta.size();
    }

    public CheatDetectNowMeta add(Player target, UUID npc, int id, DetectType type)
    {
        CheatDetectNowMeta meta = new CheatDetectNowMeta(target, npc, id, type);
        this.meta.add(meta);
        return meta;
    }

    public ArrayList<CheatDetectNowMeta> getMetas()
    {
        return this.meta;
    }

    public void remove(UUID uuid)
    {
        this.meta.removeIf(meta -> meta.getUuids() == uuid);
    }

    public CheatDetectNowMeta getMetaByUUID(UUID uuid)
    {
        for (CheatDetectNowMeta meta: this.meta)
        {
            if (meta.getUuids() == uuid)
                return meta;
        }
        return null;
    }

    public CheatDetectNowMeta getMetaByPlayerUUID(UUID uuid)
    {
        for (CheatDetectNowMeta meta: this.meta)
        {
            if (meta.getTarget().getUniqueId() == uuid)
                return meta;
        }
        return null;
    }

    public boolean exists(UUID uuid)
    {
        CheatDetectNowMeta meta = this.getMetaByPlayerUUID(uuid);
        return meta != null;
    }
}
