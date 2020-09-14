package ml.peya.plugins.Enum;

import net.md_5.bungee.api.*;

/**
 * 重大度
 */
public enum EnumSeverity
{
    /**
     * 後回し
     */
    FINEST("Finest", ChatColor.GREEN, 1),
    /**
     * 遅くても可
     */
    FINER("Finer", ChatColor.BLUE, 2),
    /**
     * 普通
     */
    NORMAL("Normal", ChatColor.BLUE, 3),
    /**
     * ちょい優先
     */
    FINE("Fine", ChatColor.DARK_GREEN, 4),
    /**
     * 優先
     */
    PRIORITY("Priority", ChatColor.YELLOW, 5),
    /**
     * 結構優先
     */
    REQUIRE_FAST("REQUIRE FAST", ChatColor.LIGHT_PURPLE, 6),
    /**
     * 最優先
     */
    SEVERE("SEVERE", ChatColor.DARK_RED, 7),
    /**
     * 不明
     */
    UNKNOWN("Unknown", ChatColor.GRAY, 0);

    /**
     * 名前。
     */
    private final String text;
    /**
     * 色。
     */
    private final net.md_5.bungee.api.ChatColor color;
    /**
     * レベル。
     */
    private final int level;

    /**
     * コンストラクター。
     *
     * @param text  テキスト。
     * @param color いろ。
     * @param level レベル。
     */
    EnumSeverity(String text, net.md_5.bungee.api.ChatColor color, int level)
    {
        this.text = text;
        this.color = color;
        this.level = level;
    }

    /**
     * テキストのゲッター。
     *
     * @return テキスト。
     */
    public String getText()
    {
        return text;
    }

    /**
     * いろのゲッター。
     *
     * @return いろ。
     */
    public net.md_5.bungee.api.ChatColor getColor()
    {
        return color;
    }

    /**
     * レベルのゲッター。
     *
     * @return レベル。
     */
    public int getLevel()
    {
        return level;
    }
}
