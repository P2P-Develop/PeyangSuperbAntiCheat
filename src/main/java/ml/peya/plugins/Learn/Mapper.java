package ml.peya.plugins.Learn;

public class Mapper
{
    double[][] inputWeight;
    double[] middleWeight;
    int learnCount;

    public Mapper(double[][] iw, double[] mw, int lc)
    {
        inputWeight = iw;
        middleWeight = mw;
        learnCount = lc;
    }
}
