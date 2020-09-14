package ml.peya.plugins.Module.NoCheatPlus;

import fr.neatmonster.nocheatplus.checks.*;
import fr.neatmonster.nocheatplus.checks.access.*;
import fr.neatmonster.nocheatplus.hooks.*;
import ml.peya.plugins.Detect.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import org.bukkit.entity.*;

/**
 * NoCheatPLusのフック
 */
public class Hook implements NCPHook
{
    /**
     * フック名取得
     *
     * @return フック名
     */
    @Override
    public String getHookName()
    {
        return "AuraBot";
    }

    /**
     * フックバージョン取得
     *
     * @return フックバージョン
     */
    @Override
    public String getHookVersion()
    {
        return "PSAC-" + PeyangSuperbAntiCheat.getPlugin().getDescription().getVersion();
    }

    /**
     * NCPのチェックに引っかかったとき
     *
     * @param checkType 引っかかったタイプ
     * @param player    対象プレイヤー
     * @param info      VLInfo
     * @return 実行結果
     */
    @Override
    public boolean onCheckFailure(CheckType checkType, Player player, IViolationInfo info)
    {
        if (checkType != CheckType.FIGHT_ANGLE && checkType != CheckType.FIGHT_DIRECTION && checkType != CheckType.FIGHT_REACH)
            return true;

        if (Variables.cheatMeta.exists(player.getUniqueId()))
            return true;

        DetectConnection.scan(player, DetectType.AURA_BOT, null, false);
        return false;
    }
}
