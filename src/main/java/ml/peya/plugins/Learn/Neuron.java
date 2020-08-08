package ml.peya.plugins.Learn;

import java.util.*;

import static develop.p2p.lib.LearnMath.relu;

/**
 * 重みを突っ込む器。
 */
public class Neuron
{
    /**
     * 加算したWeight。
     */
    private double sum;

    /**
     * ...なんだったっけこれ
     */
    private double value = 0.0;

    /** ゲッター。
     * @return value参照。
     */
    public double getValue()
    {
        return value;
    }

    /** セッター。
     * @param value value参照。
     */
    public void setValue(double value)
    {
        this.value = value;
    }

    /** ReLU関数で値を強めて出力系に入れる。
     * @param inputData InputクラスのArrayListデータ。
     */
    public void input(ArrayList<Input> inputData)
    {
        inputData.parallelStream().forEachOrdered(input -> input(input.getWeightingValue()));
        setValue(relu(sum));
    }

    /** input(ArrayList)のオーバーロード。
     * @param value sumに加算する値。
     */
    public void input(double value)
    {
        sum += value;
    }
}
