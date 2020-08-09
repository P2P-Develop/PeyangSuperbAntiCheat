package ml.peya.plugins.Utils;

import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;

import java.sql.*;
import java.util.*;

/**
 * レベルのいろいろな取得方法まとめたクラス！！
 */
public class SeverityLevels
{
    /**
     * タイプそのまんま通過する奴
     *
     * @param types タイプ
     * @return 通過
     */
    public static EnumSeverity getSeverity(ArrayList<EnumCheatType> types)
    {
        return getSeverity(types.size());
    }

    /**
     * intでも突っ込めるようにしたやつ。基準かなこれ
     *
     * @param level Stream APIの餌食となる引数
     * @return 変換後？
     */
    public static EnumSeverity getSeverity(int level)
    {
        return getAllSeverity().parallelStream().filter(severity -> severity.getLevel() == level).findFirst().orElse(EnumSeverity.UNKNOWN);
    }

    /**
     * ArrayListとして全部かき集めたやつを返す。
     *
     * @return かき集めたやつ。
     */
    public static ArrayList<EnumSeverity> getAllSeverity()
    {
        ArrayList<EnumSeverity> severities = new ArrayList<>();

        severities.add(EnumSeverity.FINE);
        severities.add(EnumSeverity.FINER);
        severities.add(EnumSeverity.FINEST);
        severities.add(EnumSeverity.NORMAL);
        severities.add(EnumSeverity.PRIORITY);
        severities.add(EnumSeverity.REQUIRE_FAST);
        severities.add(EnumSeverity.SEVERE);
        return severities;
    }

    /**
     * IDからレベル取得する。
     *
     * @param id ID。
     * @return 取得できたやつ。
     */
    public static EnumSeverity getSeverityFromId(String id)
    {
        if (WatchEyeManagement.isExistsRecord(id))
            return EnumSeverity.UNKNOWN;
        try (Connection connection = PeyangSuperbAntiCheat.eye.getConnection();
             Statement statement = connection.createStatement())
        {
            ResultSet result = statement.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE MnGiD = '" + id + "'");
            if (result.next())
                return getSeverity(result.getInt("LEVEL"));
            return EnumSeverity.UNKNOWN;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            return EnumSeverity.UNKNOWN;
        }
    }
}
