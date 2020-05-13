package develop.p2p.plugin.Enum;

public enum  EnumCheatType
{
    KILLAURA("KillAura", false, "killaura"),
    BHOP("BunnyHop", false, "bhop"),
    REACH("Reach", false, "reach"),
    SPEED("Speed", false, "speed"),
    ANTIKNOCKBACK("AntiKnockBack", false, "antiknockback"),
    FLY("Fly", false, "fly"),
    AUTOCLICKER("Auto Clicker", false, "autoclicker");

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
