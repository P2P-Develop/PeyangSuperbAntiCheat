package develop.p2p.plugin.Enum;

public enum  EnumCheatType
{
    KILLAURA("KillAura", false),
    BHOP("BunnyHop", false),
    REACH("Reach", false),
    SPEED("Speed", false),
    ANTIKNOCKBACK("AntiKnockBack", false),
    FLY("Fly", false);

    private String text;
    private boolean isSelected;
    private boolean isChecked = false;

    EnumCheatType(String text, boolean isSelected)
    {
        this.text = text;
        this.isSelected = isSelected;
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
}
