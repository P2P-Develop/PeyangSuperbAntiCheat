package ml.peya.plugins.Enum;

public enum DetectType {
    AURA_BOT, AURA_PANIC;

    private int time;

    DetectType() {
        time = 5;
    }

    public void setPanicTime(int time) {
        this.time = time;
    }

    public int getPanicTime() {
        return time;
    }
}
