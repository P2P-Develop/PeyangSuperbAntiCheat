package ml.peya.plugins;

import java.util.*;

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
