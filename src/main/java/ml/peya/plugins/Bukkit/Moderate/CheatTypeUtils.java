package ml.peya.plugins.Bukkit.Moderate;

import ml.peya.plugins.Bukkit.Enum.*;

import java.util.*;
import java.util.function.*;

/**
 * 判定タイプ管理するやつ。
 */
public class CheatTypeUtils
{
    /**
     * 種類確認するみたいなノリで全部タイプ返してくれるクラス。
     *
     * @return 全部返してくれる。
     */
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

        types.parallelStream().forEachOrdered(type -> type.setSelected(false));
        return types.toArray(new EnumCheatType[0]);
    }

    /**
     * 上のメソッドのArrayList版。
     *
     * @return 全部！
     */
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
        types.parallelStream().forEachOrdered(type -> type.setSelected(false));
        return types;
    }

    /**
     * まぁこれも。
     *
     * @param values Stringから変換する奴。
     * @return 変換後。
     */
    public static ArrayList<EnumCheatType> getCheatTypeArrayFromString(String[] values)
    {
        ArrayList<EnumCheatType> types = getFullTypeArrayList();
        Arrays.stream(values).parallel().<Consumer<? super EnumCheatType>>map(reason -> type -> {
            if (reason.toLowerCase().equals(type.getSysName()) || aliasEquals(type, reason.toLowerCase()))
                type.setSelected(true);
        }).forEachOrdered(types::forEach);

        return types;
    }

    /**
     * まぁ同じくらい。
     *
     * @param sysname Stringから普通に変換する奴。
     * @return 変換後。
     */
    public static EnumCheatType getCheatTypeFromString(String sysname)
    {
        return getFullTypeArrayList().parallelStream().filter(type -> type.getSysName().equals(sysname)).findFirst().orElse(null);
    }

    /**
     * エイリアスOK?
     *
     * @param types 判定タイプ。
     * @param name  なまえ
     * @return OK=true
     */
    public static boolean aliasEquals(EnumCheatType types, String name)
    {
        return types.getAlias().parallelStream().anyMatch(type -> type.equals(name));
    }
}
