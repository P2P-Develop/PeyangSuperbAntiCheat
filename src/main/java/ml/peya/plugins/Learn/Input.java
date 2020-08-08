package ml.peya.plugins.Learn;

/**
 * AI入力に特化するクラス。
 */
public class Input
{
    /**
     * ...なんこれ
     */
    private double value;
    /**
     * 重み(シナプス間結合強度)。
     */
    private double weight;

    /** コンストラクター。
     * @param value ...なんこれ
     * @param weight 重み(シナプス間結合強度)。
     */
    public Input(double value, double weight)
    {
        setValue(value);
        setWeight(weight);
    }

    /** ゲッター。
     * @return valueを参照。
     */
    public double getValue()
    {
        return value;
    }

    /** セッター。
     * @param value valueを参照。
     */
    public void setValue(double value)
    {
        this.value = value;
    }

    /** 重みのゲッター。
     * @return 重み。
     */
    public double getWeight()
    {
        return weight;
    }

    /** 重みのセッター。
     * @param weight 重み。
     */
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    /** 重みとvalueをかける。
     * @return 重みとvalueかけたやつ。
     */
    public double getWeightingValue()
    {
        return getValue() * getWeight();
    }
}
