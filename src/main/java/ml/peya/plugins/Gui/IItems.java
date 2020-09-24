package ml.peya.plugins.Gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * アイテムたちの実装を強制するインターフェース。
 */
public interface IItems
{
    /**
     * 処理を突っ込む。
     *
     * @param player プレイヤー。
     * @param target ターゲット。
     */
    void run(Player player, String target);

    /**
     * itemsのゲッター。
     *
     * @param target ターゲット。
     *
     * @return アイテム。
     */
    ItemStack getItem(String target);

    /**
     * スペースができるか...?
     *
     * @return わからん！
     */
    boolean canSpace();

    /**
     * ExecuteNameを取得する...らしい。
     *
     * @return ExecuteName(? ?)
     */
    String getExecName();

    /**
     * Typeのゲッター。
     *
     * @return Type。
     */
    Type getType();

    /**
     * メニューのタイプ
     */
    enum Type
    {
        /**
         * メイン。
         */
        MAIN("MAIN"),
        /**
         * ターゲット。
         */
        TARGET("TARGET"),
        /**
         * サブターゲット。
         */
        TARGET_2("TARGET_2"),
        /**
         * 全て。
         */
        ALL("ALL");
        /**
         * 名前。
         */
        final String name;

        /**
         * コンストラクター。
         *
         * @param name 名前。
         */
        Type(String name)
        {
            this.name = name;
        }

        /**
         * Type変換。
         *
         * @param type stringのType。
         *
         * @return 変換後。
         */
        public static Type toType(String type)
        {
            return Type.valueOf(type.toUpperCase());
        }

        /**
         * 名前のゲッター。
         *
         * @return 名前。
         */
        public String getName()
        {
            return name;
        }

        /**
         * 名前(小文字)のゲッター。
         *
         * @return 名前(小文字)。
         */
        public String getRaw()
        {
            return name.toLowerCase();
        }
    }
}
