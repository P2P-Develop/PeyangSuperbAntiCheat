package ml.peya.plugins.Moderate;

import ml.peya.plugins.Enum.*;

import java.util.*;
import java.util.function.*;

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

        for (EnumCheatType type : types)
        {
            type.setSelected(false);
        }
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
        types.forEach(type -> type.setSelected(false));
        return types;
    }

    public static ArrayList<EnumCheatType> getCheatTypeArrayFromString(String[] values)
    {
        ArrayList<EnumCheatType> types = getFullTypeArrayList();
        Arrays.stream(values).<Consumer<? super EnumCheatType>>map(reason -> type -> {
            if (reason.toLowerCase().equals(type.getSysName()) || aliasEquals(type, reason.toLowerCase()))
                type.setSelected(true);
        }).forEachOrdered(types::forEach);

        return types;
    }

    public static EnumCheatType getCheatTypeFromString(String sysname)
    {
        return getFullTypeArrayList().stream().filter(type -> type.getSysName().equals(sysname)).findFirst().orElse(null);
    }

    public static boolean aliasEquals(EnumCheatType types, String name)
    {
        return types.getAlias().stream().anyMatch(type -> type.equals(name));
    }
}
