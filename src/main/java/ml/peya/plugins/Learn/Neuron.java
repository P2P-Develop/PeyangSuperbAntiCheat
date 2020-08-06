package ml.peya.plugins.Learn;

import java.util.*;

import static develop.p2p.lib.LearnMath.relu;

public class Neuron
{
    double sum;

    private double value = 0.0;

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    public void input(ArrayList<Input> inputData)
    {
        inputData.parallelStream().forEachOrdered(input -> input(input.getWeightingValue()));
        setValue(relu(sum));
    }

    public void input(double value)
    {
        sum += value;
    }
}
