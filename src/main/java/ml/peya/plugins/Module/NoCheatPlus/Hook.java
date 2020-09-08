package ml.peya.plugins.Module.NoCheatPlus;

import fr.neatmonster.nocheatplus.checks.*;
import fr.neatmonster.nocheatplus.checks.access.*;
import fr.neatmonster.nocheatplus.hooks.*;
import ml.peya.plugins.Detect.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import org.bukkit.entity.*;

public class Hook implements NCPHook
{

    @Override
    public String getHookName()
    {
        return "AuraBot";
    }

    @Override
    public String getHookVersion()
    {
        return "PSAC-" + PeyangSuperbAntiCheat.getPlugin().getDescription().getVersion();
    }

    @Override
    public boolean onCheckFailure(CheckType checkType, Player player, IViolationInfo info)
    {
        if (checkType != CheckType.FIGHT_ANGLE && checkType != CheckType.FIGHT_DIRECTION && checkType != CheckType.FIGHT_REACH)
            return true;
        DetectConnection.scan(player, DetectType.AURA_BOT, null, false);
        return false;
    }
}
