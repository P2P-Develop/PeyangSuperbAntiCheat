package ml.peya.plugins.Utils;

import ml.peya.plugins.DetectClasses.WatchEyeManagement;
import ml.peya.plugins.Enum.EnumCheatType;
import ml.peya.plugins.Enum.EnumSeverity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import static ml.peya.plugins.Variables.eye;

/**
 * 重要度取得
 */
public class SeverityLevels
{
    /**
     * タイプそのまんま通過する奴
     *
     * @param types タイプ
     *
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
     *
     * @return 変換後？
     */
    public static EnumSeverity getSeverity(int level)
    {
        return getAllSeverity().parallelStream().filter(severity -> severity.getLevel() == level).findFirst()
                               .orElse(EnumSeverity.UNKNOWN);
    }

    /**
     * ArrayListとして全部かき集めたやつを返す。
     *
     * @return かき集めたやつ。
     */
    public static ArrayList<EnumSeverity> getAllSeverity()
    {
        return new ArrayList<>(Arrays
                .asList(EnumSeverity.FINE, EnumSeverity.FINER, EnumSeverity.FINEST, EnumSeverity.NORMAL, EnumSeverity.PRIORITY, EnumSeverity.REQUIRE_FAST, EnumSeverity.SEVERE));
    }

    /**
     * IDからレベル取得する。
     *
     * @param id ID。
     *
     * @return 取得できたやつ。
     */
    public static EnumSeverity getSeverityFromId(String id)
    {
        if (WatchEyeManagement.isExistsRecord(id))
            return EnumSeverity.UNKNOWN;
        try (Connection connection = eye.getConnection();
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
