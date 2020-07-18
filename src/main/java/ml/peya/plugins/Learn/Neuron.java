package ml.peya.plugins.Learn;

import java.util.ArrayList;

public class Neuron
{
    double sum;

    private double value = 0.0;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    void input(ArrayList<Input> value)
    {
        for (Input input: value)
            sum += input.getWeightingValue();
    }
    public void input(double value)
    {
        sum += value;
    }
}
