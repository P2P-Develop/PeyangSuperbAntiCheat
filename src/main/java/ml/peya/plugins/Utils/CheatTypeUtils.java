package ml.peya.plugins.Utils;

import ml.peya.plugins.Enum.*;

import java.util.*;

public class CheatTypeUtils
{
    public static EnumCheatType[] getFullType()
    {
        ArrayList<EnumCheatType> types = new ArrayList<>();

        types.add(EnumCheatType.FLY);
        types.add(EnumCheatType.KILLAURA);
        types.add(EnumCheatType.AUTOCLICKER);
        types.add(EnumCheatType.SPEED);
        types.add(EnumCheatType.ANTIKNOCKBACK);
        types.add(EnumCheatType.REACH);
        types.add(EnumCheatType.DOLPHIN);

        for (EnumCheatType type: types)
            type.setSelected(false);
        return types.toArray(new EnumCheatType[0]);
    }

    public static ArrayList<EnumCheatType> getFullTypeArrayList()
    {
        ArrayList<EnumCheatType> types = new ArrayList<>();

        types.add(EnumCheatType.FLY);
        types.add(EnumCheatType.KILLAURA);
        types.add(EnumCheatType.AUTOCLICKER);
        types.add(EnumCheatType.SPEED);
        types.add(EnumCheatType.ANTIKNOCKBACK);
        types.add(EnumCheatType.REACH);
        types.add(EnumCheatType.DOLPHIN);
        for (EnumCheatType type: types)
            type.setSelected(false);
        return types;
    }

    public static ArrayList<EnumCheatType> getCheatTypeArrayFromString(String[] values)
    {
        ArrayList<EnumCheatType> types = getFullTypeArrayList();
        for (String reason: values)
        {
            for (EnumCheatType type: types)
            {
                if (reason.contains(type.getSysName()))
                    type.setSelected(true);
            }
        }
        return types;
    }

    public static EnumCheatType getCheatTypeFromString(String sysname)
    {
        ArrayList<EnumCheatType> types = getFullTypeArrayList();
        for (EnumCheatType type: types)
        {
            if (type.getSysName().equals(sysname))
                return type;
        }
        return null;
    }
}
