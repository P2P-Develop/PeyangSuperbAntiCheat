package ml.peya.plugins.Enum;

public enum DetectType
{
    AURA_BOT,
    AURA_PANIC,
    ANTI_KB;

    private int count;


    DetectType ()
    {
        count = 5;
    }

    public void setKBCount(int time)
    {
        this.count = time;
    }

    public int getKBCount()
    {
        return count;
    }
}
