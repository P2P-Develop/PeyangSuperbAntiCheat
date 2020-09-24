package ml.peya.plugins.Learn;

import org.apache.commons.lang3.tuple.*;

import java.util.*;
import java.util.stream.*;

import static ml.peya.plugins.Variables.config;

/**
 * The・AI中枢
 */
public class NeuralNetwork
{
    /**
     * RandomWeight()取得に使用。
     */
    private final static Random random = new Random();
    /**
     * 重みのふり幅。
     */
    private static final double weightRange = 10.0;
    /**
     * 前層の重み。
     */
    public static final double[][] inputWeight = new double[][]{{RandomWeight(), RandomWeight(), RandomWeight()}, {RandomWeight(), RandomWeight(), RandomWeight()}, {RandomWeight(), RandomWeight(), RandomWeight()}};
    /**
     * 中層の重み。
     */
    public static final double[] middleWeight = new double[]{RandomWeight(), RandomWeight(), RandomWeight()};
    /**
     * 前層のバイアス。
     */
    private static final double inputLayerBias = 1.0;
    /**
     * 中層のバイアス。
     */
    private static final double middleLayerBias = 1.0;
    /**
     * 前層自体の表現。
     */
    private double[] inputLayer;
    /**
     * 中層自体の表現。
     */
    private Neuron[] middleLayer;
    /**
     * 出力層。
     * ローカル変数問題は気にしない。
     */
    private Neuron outputLayer;

    /**
     * ランダムに重みを設定する関数。
     *
     * @return ランダムなdoubleから適切な値を引いた数とふり幅をかけてふり幅を最大として値を落とします。
     */
    private static double RandomWeight()
    {
        return (random.nextDouble() - 0.5) * weightRange;
    }

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
        return IntStream.range(0, inputLayer.length)
                .parallel()
                .mapToObj(i -> new Input(inputLayer[i], inputWeight[i] - 1))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * 出力結果を算出する。
     *
     * @param data 計算させるデータ。
     * @return 0.0~1.0までの出力結果。
     */
    public double commit(Pair<Double, Double> data)
    {
        inputLayer = new double[]{data.getLeft(), data.getRight(), inputLayerBias};
        middleLayer = new Neuron[]{new Neuron(), new Neuron()};
        outputLayer = new Neuron();

        IntStream.range(0, middleLayer.length)
                .parallel()
                .forEachOrdered(i -> middleLayer[i].input(toInputData(inputLayer, getColumn(inputWeight, i))));

        outputLayer.input(new ArrayList<>(Arrays.asList(new Input(middleLayer[0].getValue(), middleWeight[0]), new Input(middleLayer[1].getValue(), middleWeight[1]), new Input(middleLayerBias, middleWeight[2]))));

        return outputLayer.getValue();
    }

    /**
     * 学習。
     *
     * @param dataCollection データ。
     * @param count          学習回数。
     */
    public void learn(ArrayList<Triple<Double, Double, Double>> dataCollection, int count)
    {
        IntStream.range(0, count)
                .parallel()
                .forEachOrdered(i -> dataCollection.parallelStream()
                        .forEachOrdered(this::learn));
    }

    /**
     * さらに深い学習。
     *
     * @param data データ。
     */
    private void learn(Triple<Double, Double, Double> data)
    {
        final double outputData = commit(Pair.of(data.getLeft(), data.getMiddle()));

        final double learningRate = config.getDouble("npc.learn");

        final double deltaMO = (data.getRight() - outputData) * outputData * (1.0 - outputData);
        final double[] oldMiddleWeight = middleWeight.clone();

        IntStream.range(0, middleLayer.length).parallel().forEachOrdered(i -> middleWeight[i] += new Neuron().getValue() * deltaMO * learningRate);

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
