package ml.peya.plugins.Learn;

import ml.peya.plugins.*;
import org.apache.commons.lang3.tuple.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class NeuralNetwork
{
    double[] inputLayer;
    Neuron[] middleLayer;
    Neuron outputLayer;

    final double inputLayerBias = 1.0;
    final double middleLayerBias = 1.0;

    final static Random random = new Random();
    final static double weightRange = 10.0;
    static double RandomWeight = (random.nextDouble() - 0.5) * weightRange;

    double[][] inputWeight = new double[][]{{RandomWeight, RandomWeight, RandomWeight}, {RandomWeight, RandomWeight, RandomWeight}, {RandomWeight, RandomWeight, RandomWeight}};
    double[] middleWeight = new double[]{RandomWeight, RandomWeight, RandomWeight};

    public static double[] getColumn(double[][] array, int index)
    {
        double[] column = new double[array[0].length];
        Arrays.setAll(column, i -> array[i][index]);
        return column;
    }

    public double commit(Pair<Double, Double> data)
    {
        inputLayer = new double[]{data.getLeft(), data.getRight(), inputLayerBias};
        middleLayer = new Neuron[]{new Neuron(), new Neuron()};
        outputLayer = new Neuron();

        for (int i = 0; i < middleLayer.length; i++)
        {
            middleLayer[i].input(toInputData(inputLayer, getColumn(inputWeight, i)));
        }

        outputLayer.input((ArrayList<Input>) Arrays.asList(new Input(middleLayer[0].getValue(), middleWeight[0]), new Input(middleLayer[1].getValue(), middleWeight[1]), new Input(middleLayerBias, middleWeight[2])));

        return outputLayer.getValue();
    }

    public void learn(ArrayList<Triple<Double, Double, Double>> dataCollection, int times)
    {
        IntStream.range(0, times).<Consumer<? super Triple<Double, Double, Double>>>mapToObj(i -> this::learn).forEach(dataCollection::forEach);
    }

    void learn(Triple<Double, Double, Double> data)
    {
        double outputData = commit(Pair.of(data.getLeft(), data.getMiddle()));
        double correctValue = data.getRight();

        // 学習係数
        final double learningRate = PeyangSuperbAntiCheat.config.getDouble("npc.learn");

        double deltaMO = (correctValue - outputData) * outputData * (1.0 - outputData);
        double[] oldMiddleWeight = middleWeight.clone();

        IntStream.range(0, middleLayer.length).forEach(i -> middleWeight[i] += new Neuron().getValue() * deltaMO * learningRate);

        middleWeight[2] += middleLayerBias * deltaMO * learningRate;

        double[] deltaIM = new double[]{
                deltaMO * oldMiddleWeight[0] * middleLayer[0].getValue() * (1.0 - middleLayer[0].getValue()),
                deltaMO * oldMiddleWeight[1] * middleLayer[1].getValue() * (1.0 - middleLayer[1].getValue())
        };

        inputWeight[0][0] += inputLayer[0] * deltaIM[0] * learningRate;
        inputWeight[0][1] += inputLayer[0] * deltaIM[1] * learningRate;
        inputWeight[1][0] += inputLayer[1] * deltaIM[0] * learningRate;
        inputWeight[1][1] += inputLayer[1] * deltaIM[1] * learningRate;
        inputWeight[2][0] += inputLayer[2] * deltaIM[0] * learningRate;
        inputWeight[2][1] += inputLayer[2] * deltaIM[1] * learningRate;

        // ここからデータベース更新処理
        // データベースに何もなかったら全ての重みを追加する

    }

    static ArrayList<Input> toInputData(double[] inputLayer, double[] inputWeight)
    {
        ArrayList<Input> it = new ArrayList<>();
        int count = 0;
        for (double layer : inputLayer)
        {
            Input input = new Input(layer, inputWeight[count] - 1);
            it.add(input);
            count++;
        }
        return it;
    }
}
