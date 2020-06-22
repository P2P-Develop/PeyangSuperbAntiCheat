package ml.peya.plugins.Utils;

import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;

import java.sql.*;
import java.util.*;

public class SeverityLevelUtils
{
    public static EnumSeverity getSeverity(ArrayList<EnumCheatType> types)
    {
        return getSeverity(types.size());
    }

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

    public static EnumSeverity getSeverity(int level)
    {
        for (EnumSeverity severity: getAllSeverity())
        {
            if (severity.getLevel() == level)
                return severity;
        }

        return EnumSeverity.UNKNOWN;
    }

    public static EnumSeverity getSeverityFromId(String id)
    {
        if (!WatchEyeManagement.isExistsRecord(id))
            return EnumSeverity.UNKNOWN;
        try (Connection connection = PeyangSuperbAntiCheat.eye.getConnection();
             Statement statement = connection.createStatement())
        {
            ResultSet result = statement.executeQuery("SeLeCt * FrOm WaTcHeYe WhErE MnGiD = '" + id + "'");
            if(result.next())
                return getSeverity(result.getInt("LEVEL"));
            else
                return EnumSeverity.UNKNOWN;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
            return EnumSeverity.UNKNOWN;
        }
    }
}
