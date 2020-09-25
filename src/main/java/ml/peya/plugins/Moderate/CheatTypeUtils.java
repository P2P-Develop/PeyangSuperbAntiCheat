package ml.peya.plugins.Moderate;

import ml.peya.plugins.Enum.EnumCheatType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

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
        return createTypes().toArray(new EnumCheatType[0]);
    }

    private static ArrayList<EnumCheatType> createTypes()
    {
        ArrayList<EnumCheatType> types = new ArrayList<>(Arrays
                .asList(EnumCheatType.FLY, EnumCheatType.KILLAURA, EnumCheatType.AUTOCLICKER, EnumCheatType.SPEED, EnumCheatType.ANTIKNOCKBACK, EnumCheatType.REACH, EnumCheatType.DOLPHIN));

        types.parallelStream()
                .forEachOrdered(type -> type.setSelected(false));

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
        ArrayList<EnumCheatType> types = createTypes();
        Arrays.stream(values).parallel().<Consumer<? super EnumCheatType>>map(reason -> type ->
        {
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
        return createTypes().parallelStream()
                .filter(type -> type.getSysName()
                        .equals(sysname))
                .findFirst()
                .orElse(null);
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
