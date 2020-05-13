package develop.p2p.plugin.Utils;

import develop.p2p.plugin.Enum.*;

import java.util.*;

public class CheatTypeUtils
{
    public static ArrayList<EnumCheatType> getFullType()
    {
        ArrayList<EnumCheatType> types = new ArrayList<>();

        types.add(EnumCheatType.ANTIKNOCKBACK);
        types.add(EnumCheatType.BHOP);
        types.add(EnumCheatType.FLY);
        types.add(EnumCheatType.KILLAURA);
        types.add(EnumCheatType.REACH);
        types.add(EnumCheatType.SPEED);
        return types;
    }
}
