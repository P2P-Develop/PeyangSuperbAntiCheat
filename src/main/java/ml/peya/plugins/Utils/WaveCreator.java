package ml.peya.plugins.Utils;

public class WaveCreator
{
    private boolean flag; //上昇フラグ

    private final double max;
    private final double min;

    private double now;

    public WaveCreator(double now, double max, double min)
    {
        this.max = max;
        this.now = now;
        this.min = min;
    }


    public double get(double db, boolean def)
    {
        if (def)
            return now;

        if (flag)
            now += db;
        else
            now -= db;

        if (now + db > max)
        {
            if (flag)
                flag = false;
            return max;
        }
        else if (now - db < min)
        {
            if (!flag)
                flag = true;
            return min;
        }


        return now;
    }

    public double getStatic()
    {
        return now;
    }

}
