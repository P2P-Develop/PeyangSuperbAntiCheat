package ml.peya.plugins.Enum;

import net.md_5.bungee.api.*;

/**
 * Docめんｄ
 */
public enum EnumSeverity
{
    /**
     * フィネスト？？？？
     */
    FINEST("Finest", ChatColor.GREEN, 1),
    /**
     * フィナー？？？？？？？
     */
    FINER("Finer", ChatColor.BLUE, 2),
    /**
     * 通常。
     */
    NORMAL("Normal", ChatColor.BLUE, 3),
    /**
     * 良い。
     */
    FINE("Fine", ChatColor.DARK_GREEN, 4),
    /**
     * 有線。
     */
    PRIORITY("Priority", ChatColor.YELLOW, 5),
    /**
     * 早いのいる。
     */
    REQUIRE_FAST("REQUIRE FAST", ChatColor.LIGHT_PURPLE, 6),
    /**
     * 重度。
     */
    SEVERE("SEVERE", ChatColor.DARK_RED, 7),
    /**
     * わからん。
     */
    UNKNOWN("Unknown", ChatColor.GRAY, 0);

    /**
     * テキスト。
     */
    private final String text;
    /**
     * いろ。
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
