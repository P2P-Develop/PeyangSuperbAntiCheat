package ml.peya.plugins.Module;

import java.util.*;

/**
 * モジュールリスト
 */
public class Modules
{
    /**
     * moduel
     */
    private final ArrayList<String> modules;

    /**
     * コンストラクタ
     */
    public Modules()
    {
        modules = new ArrayList<>();
    }

    /**
     * 有効化
     *
     * @param module モジュール
     */
    public void enable(String module)
    {
        if (isEnable(module))
            return;
        modules.add(module);
    }

    /**
     * 無効化
     *
     * @param module モジュール
     */
    public void disable(String module)
    {
        modules.remove(module);
    }

    /**
     * 有効チェック
     *
     * @param module モジュール
     * @return 存在
     */
    public boolean isEnable(String module)
    {
        return modules.contains(module);
    }
}
