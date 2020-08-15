package ml.peya.plugins.Bukkit.Enum;

import net.md_5.bungee.api.*;

/**
 * 重大度
 */
public enum EnumSeverity
{
    /**
     * 最も良い
     */
    FINEST("Finest", ChatColor.GREEN, 1),
    /**
     * より細かい
     */
    FINER("Finer", ChatColor.BLUE, 2),
    /**
     * 普通
     */
    NORMAL("Normal", ChatColor.BLUE, 3),
    /**
     * 良い
     */
    FINE("Fine", ChatColor.DARK_GREEN, 4),
    /**
     * 優先
     */
    PRIORITY("Priority", ChatColor.YELLOW, 5),
    /**
     * 必要最低限
     */
    REQUIRE_FAST("REQUIRE FAST", ChatColor.LIGHT_PURPLE, 6),
    /**
     * 重度
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
