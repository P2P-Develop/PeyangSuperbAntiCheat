package ml.peya.plugins.DetectClasses;

import ml.peya.plugins.Enum.*;
import org.bukkit.entity.*;

import java.util.*;

public class DetectingList
{
    private final ArrayList<CheatDetectNowMeta> meta = new ArrayList<>();

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
        this.meta.removeIf(meta -> meta.getUUIDs() == uuid);
    }

    public CheatDetectNowMeta getMetaByUUID(UUID uuid)
    {
        return this.meta.stream().filter(meta -> meta.getUUIDs() == uuid).findFirst().orElse(null);
    }

    public CheatDetectNowMeta getMetaByPlayerUUID(UUID uuid)
    {
        return this.meta.stream().filter(meta -> meta.getTarget().getUniqueId() == uuid).findFirst().orElse(null);
    }

    public boolean exists(UUID uuid)
    {
        return this.getMetaByPlayerUUID(uuid) != null;
    }
}
