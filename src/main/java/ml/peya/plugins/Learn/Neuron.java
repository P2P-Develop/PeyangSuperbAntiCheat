package ml.peya.plugins.Learn;

import develop.p2p.lib.LearnMath;

import java.util.*;

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

    void input(ArrayList<Input> inputData)
    {
        inputData.forEach(input -> input(input.getWeightingValue()));
        setValue(LearnMath.relu(sum));
    }

    public void input(double value)
    {
        sum += value;
    }
}
