package ml.peya.plugins.Learn;

public class Input
{
    private double value;
    private double weight;

    public Input()
    {

    }

    public Input(double value, double weight)
    {
        setValue(value);
        setWeight(weight);
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    public double getWeight()
    {
        return weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public double getWeightingValue()
    {
        return getValue() * getWeight();
    }
}
