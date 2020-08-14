package ml.peya.plugins.Bukkit.Learn;

/**
 * AI入力に特化するクラス。
 */
public class Input
{
    /**
     * 入力用値。
     */
    private double value;
    /**
     * 重み(シナプス間結合強度)。
     */
    private double weight;

    /**
     * コンストラクター。
     *
     * @param value  入力用値引数。
     * @param weight 重み(シナプス間結合強度)。
     */
    public Input(double value, double weight)
    {
        setValue(value);
        setWeight(weight);
    }

    /**
     * 入力用値のゲッター。
     *
     * @return 入力用値引数。
     */
    public double getValue()
    {
        return value;
    }

    /**
     * 入力用値のセッター。
     *
     * @param value 入力用値引数。
     */
    public void setValue(double value)
    {
        this.value = value;
    }

    /**
     * 重みのゲッター。
     *
     * @return 重み。
     */
    public double getWeight()
    {
        return weight;
    }

    /**
     * 重みのセッター。
     *
     * @param weight 重み。
     */
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    /**
     * 重みとvalueをかける。
     *
     * @return 重みとvalueかけたやつ。
     */
    public double getWeightingValue()
    {
        return getValue() * getWeight();
    }
}
