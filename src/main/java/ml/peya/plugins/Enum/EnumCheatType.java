package ml.peya.plugins.Enum;

public enum  EnumCheatType
{
    FLY("Fly", false, "fly"),
    KILLAURA("KillAura", false, "killaura"),
    AUTOCLICKER("Auto Clicker", false, "autoclicker"),
    SPEED("Speed", false, "speed"),
    ANTIKNOCKBACK("AntiKnockBack", false, "antiknockback"),
    REACH("Reach", false, "reach"),
    DOLPHIN("Dolphin", false, "dolphin");
    private String text;
    private boolean isSelected;
    private boolean isChecked;
    private String sysName;

    EnumCheatType(String text, boolean isSelected, String sysName)
    {
        this.text = text;
        this.isSelected = isSelected;
        this.sysName = sysName;
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
}
