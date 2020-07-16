package ml.peya.plugins.Enum;

import java.util.*;

public enum EnumCheatType
{
    FLY("Fly", false, "fly", "flight"),
    KILLAURA("KillAura", false, "killaura", "aura", "ka"),
    AUTOCLICKER("AutoClicker", false, "autoclicker", "ac", "autoclick"),
    SPEED("Speed", false, "speed", "bhop", "timer"),
    ANTIKNOCKBACK("AntiKnockback", false, "antiknockback", "antikb", "akb", "velocity"),
    REACH("Reach", false, "reach"),
    DOLPHIN("Dolphin", false, "dolphin");
    private String text;
    private boolean isSelected;
    private boolean isChecked;
    private String sysName;
    private ArrayList<String> alias;

    EnumCheatType(String text, boolean isSelected, String sysName, String... alias)
    {
        this.text = text;
        this.isSelected = isSelected;
        this.sysName = sysName;
        this.alias = new ArrayList<>(Arrays.asList(alias));
    }

    public String getText()
    {
        return text;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    public boolean isChecked()
    {
        return isChecked;
    }

    public void setChecked(boolean checked)
    {
        isChecked = checked;
    }

    public String getSysName()
    {
        return sysName;
    }

    public ArrayList<String> getAlias()
    {
        return alias;
    }
}
