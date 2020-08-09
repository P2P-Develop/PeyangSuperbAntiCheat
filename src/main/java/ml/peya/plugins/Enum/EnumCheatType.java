package ml.peya.plugins.Enum;

import java.util.*;

/**
 * D(ry
 */
public enum EnumCheatType
{
    /**
     * Fly, Flying
     */
    FLY("Fly", false, "fly", "flight"),
    /**
     * KillAura, ka
     */
    KILLAURA("KillAura", false, "killaura", "aura", "ka"),
    /**
     * AutoClicker, Macro
     */
    AUTOCLICKER("AutoClicker", false, "autoclicker", "ac", "autoclick"),
    /**
     * Speed, timer
     */
    SPEED("Speed", false, "speed", "bhop", "timer"),
    /**
     * AntiKnockback
     */
    ANTIKNOCKBACK("AntiKnockback", false, "antiknockback", "antikb", "akb", "velocity"),
    /**
     * Reach, Reaching
     */
    REACH("Reach", false, "reach"),
    /**
     * Dolphin, bhop
     */
    DOLPHIN("Dolphin", false, "dolphin");
    /**
     * テキスト。
     */
    private final String text;
    /**
     * 名前。
     */
    private final String sysName;
    /**
     * エイリアス。
     */
    private final ArrayList<String> alias;
    /**
     * 選択されているかどうか。
     */
    private boolean isSelected;
    /**
     * チェックされているかどうか。
     */
    private boolean isChecked;

    /**
     * コンストラクター。
     *
     * @param text       テキスト。
     * @param isSelected 選択されているかどうか。
     * @param sysName    名前。
     * @param alias      エイリアス。
     */
    EnumCheatType(String text, boolean isSelected, String sysName, String... alias)
    {
        this.text = text;
        this.isSelected = isSelected;
        this.sysName = sysName;
        this.alias = new ArrayList<>(Arrays.asList(alias));
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
     * 選択されているかどうかのゲッター。
     *
     * @return 選択されているかどうか。
     */
    public boolean isSelected()
    {
        return isSelected;
    }

    /**
     * 選択されているかどうかのセッター。
     *
     * @param selected 選択されているかどうか。
     */
    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    /**
     * チェックされているかどうかのゲッター。
     *
     * @return チェックされているかどうか。
     */
    public boolean isChecked()
    {
        return isChecked;
    }

    /**
     * チェックされているかどうかのセッター。
     *
     * @param checked チェックされているかどうか。
     */
    public void setChecked(boolean checked)
    {
        isChecked = checked;
    }

    /**
     * 名前のゲッター。
     *
     * @return 名前。
     */
    public String getSysName()
    {
        return sysName;
    }

    /**
     * エイリアスのゲッター。
     *
     * @return エイリアス。
     */
    public ArrayList<String> getAlias()
    {
        return alias;
    }
}
