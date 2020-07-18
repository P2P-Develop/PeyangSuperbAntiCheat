package ml.peya.plugins.Learn;

public class LearnMath
{
    public static double sigmoid(double x)
    {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public static double sigmoidDef(double x)
    {
        return sigmoid(x) * (1.0 - sigmoid(x));
    }
}
