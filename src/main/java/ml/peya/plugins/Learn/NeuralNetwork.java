package ml.peya.plugins.Learn;

import com.fasterxml.jackson.databind.*;
import ml.peya.plugins.Parser.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.apache.commons.lang3.tuple.*;

import java.sql.*;
import java.util.*;
import java.util.stream.*;

public class NeuralNetwork
{
    private final static Random random = new Random();
    private final static double weightRange = 10.0;
    private static final double RandomWeight = (random.nextDouble() - 0.5) * weightRange;
    private final double middleLayerBias = 1.0;
    private double[] inputLayer;
    private Neuron[] middleLayer;
    private final double[][] inputWeight = new double[][]{{RandomWeight, RandomWeight, RandomWeight}, {RandomWeight, RandomWeight, RandomWeight}, {RandomWeight, RandomWeight, RandomWeight}};
    private final double[] middleWeight = new double[]{RandomWeight, RandomWeight, RandomWeight};

    public static double[] getColumn(double[][] array, int index)
    {
        double[] column = new double[array[0].length];
        Arrays.setAll(column, i -> array[i][index]);
        return column;
    }

    static ArrayList<Input> toInputData(double[] inputLayer, double[] inputWeight)
    {
        ArrayList<Input> it = new ArrayList<>();
        int count = 0;
        for (double layer : inputLayer) it.add(new Input(layer, inputWeight[count++] - 1));
        return it;
    }

    public double commit(Pair<Double, Double> data)
    {
        double inputLayerBias = 1.0;
        inputLayer = new double[]{data.getLeft(), data.getRight(), inputLayerBias};
        middleLayer = new Neuron[]{new Neuron(), new Neuron()};
        Neuron outputLayer = new Neuron();

        IntStream.range(0, middleLayer.length).forEachOrdered(i -> middleLayer[i].input(toInputData(inputLayer, getColumn(inputWeight, i))));

        outputLayer.input(new ArrayList<>(Arrays.asList(new Input(middleLayer[0].getValue(), middleWeight[0]), new Input(middleLayer[1].getValue(), middleWeight[1]), new Input(middleLayerBias, middleWeight[2]))));

        return outputLayer.getValue();
    }

    public void learn(ArrayList<Triple<Double, Double, Double>> dataCollection, int times, UUID id)
    {
        for (int i = 0; i < times; i++)
            for (Triple<Double, Double, Double> doubleDoubleDoubleTriple : dataCollection)
                this.learn(doubleDoubleDoubleTriple, id);
    }

    void learn(Triple<Double, Double, Double> data, UUID id)
    {
        double outputData = commit(Pair.of(data.getLeft(), data.getMiddle()));
        double correctValue = data.getRight();

        // 学習係数
        final double learningRate = PeyangSuperbAntiCheat.config.getDouble("npc.learn");

        double deltaMO = (correctValue - outputData) * outputData * (1.0 - outputData);
        double[] oldMiddleWeight = middleWeight.clone();

        int bound = middleLayer.length;
        IntStream.range(0, bound).forEachOrdered(i -> middleWeight[i] += new Neuron().getValue() * deltaMO * learningRate);

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


        try (Connection connection = PeyangSuperbAntiCheat.learn.getConnection();
             Statement statement = connection.createStatement())
        {
            ObjectMapper mapper = new ObjectMapper();
            LearnParser parser = new LearnParser(middleWeight, inputWeight);
            statement.execute("InSeRt iNtO wdlearn vAlUeS (" +
                    "-1, " +
                    "'" + id + "'," +
                    "'" + mapper.writeValueAsString(parser).replace("'", "\\'").replace("\"", "\\\"") + "'" +
                    ");");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }

    }
}
