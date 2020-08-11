
package ml.peya.plugins.Learn;

import ml.peya.plugins.*;
import org.apache.commons.lang3.tuple.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * The・AI中枢
 */
public class NeuralNetwork
{

    private final static Random random = new Random();
    private final static double weightRange = 10.0;
    private static final double RandomWeight = (random.nextDouble() - 0.5) * weightRange;
    private final double middleLayerBias = 1.0;

    public double[][] inputWeight = new double[][]{{RandomWeight, RandomWeight, RandomWeight}, {RandomWeight, RandomWeight, RandomWeight}, {RandomWeight, RandomWeight, RandomWeight}};
    public double[] middleWeight = new double[]{RandomWeight, RandomWeight, RandomWeight};

    private double[] inputLayer;
    private Neuron[] middleLayer;

    private Neuron outputLayer;

    /**
     * 二次元配列のカラムを取得するそれっぽい関数。
     *
     * @param array 二次元配列。
     * @param index 第一配列のインデックス。
     * @return カラムを表すdouble一次元配列。
     */
    public static double[] getColumn(double[][] array, int index)
    {
        double[] column = new double[array[0].length];
        Arrays.parallelSetAll(column, i -> array[i][index]);
        return column;
    }

    /**
     * ArrayListに変換してくれる。便利。
     *
     * @param inputLayer  入力自体の表現
     * @param inputWeight 入力の重み
     * @return 変換後
     */
    static ArrayList<Input> toInputData(double[] inputLayer, double[] inputWeight)
    {
        ArrayList<Input> it = new ArrayList<>();
        int count = 0;
        for (double layer : inputLayer) it.add(new Input(layer, inputWeight[count++] - 1));
        return it;
    }

    /**
     * 出力結果を算出する。
     *
     * @param data 計算させるデータ。
     * @return 0.0~1.0までの出力結果。
     */
    public double commit(Pair<Double, Double> data)
    {
        double inputLayerBias = 1.0;
        inputLayer = new double[]{data.getLeft(), data.getRight(), inputLayerBias};
        middleLayer = new Neuron[]{new Neuron(), new Neuron()};
        outputLayer = new Neuron();

        IntStream.range(0, middleLayer.length).parallel().forEachOrdered(i -> middleLayer[i].input(toInputData(inputLayer, getColumn(inputWeight, i))));

        outputLayer.input(new ArrayList<>(Arrays.asList(new Input(middleLayer[0].getValue(), middleWeight[0]), new Input(middleLayer[1].getValue(), middleWeight[1]), new Input(middleLayerBias, middleWeight[2]))));

        return outputLayer.getValue();
    }

    /**
     * 学習。
     *
     * @param dataCollection データ。
     * @param times          ディレイ。
     */
    public void learn(ArrayList<Triple<Double, Double, Double>> dataCollection, int times)
    {
        IntStream.range(0, times).parallel().<Consumer<? super Triple<Double, Double, Double>>>mapToObj(i -> this::learn).forEachOrdered(dataCollection::forEach);
    }

    /**
     * さらに深い学習。
     *
     * @param data データ。
     */
    private void learn(Triple<Double, Double, Double> data)
    {
        final double outputData = commit(Pair.of(data.getLeft(), data.getMiddle()));
        final double correctValue = data.getRight();

        final double learningRate = PeyangSuperbAntiCheat.config.getDouble("npc.learn");

        final double deltaMO = (correctValue - outputData) * outputData * (1.0 - outputData);
        final double[] oldMiddleWeight = middleWeight.clone();

        int bound = middleLayer.length;
        IntStream.range(0, bound).parallel().forEachOrdered(i -> middleWeight[i] += new Neuron().getValue() * deltaMO * learningRate);

        middleWeight[2] += middleLayerBias * deltaMO * learningRate;

        final double[] deltaIM = new double[]{
                deltaMO * oldMiddleWeight[0] * middleLayer[0].getValue() * (1.0 - middleLayer[0].getValue()),
                deltaMO * oldMiddleWeight[1] * middleLayer[1].getValue() * (1.0 - middleLayer[1].getValue())
        };

        inputWeight[0][0] += inputLayer[0] * deltaIM[0] * learningRate;
        inputWeight[0][1] += inputLayer[0] * deltaIM[1] * learningRate;
        inputWeight[1][0] += inputLayer[1] * deltaIM[0] * learningRate;
        inputWeight[1][1] += inputLayer[1] * deltaIM[1] * learningRate;
        inputWeight[2][0] += inputLayer[2] * deltaIM[0] * learningRate;
        inputWeight[2][1] += inputLayer[2] * deltaIM[1] * learningRate;
    }
}
