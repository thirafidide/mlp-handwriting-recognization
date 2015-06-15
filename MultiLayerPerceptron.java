import java.io.*;
import java.util.*;

interface ActivatorFunction {
	public double evaluate(double input);
}

class StepFunction implements ActivatorFunction {
	public double evaluate(double input) {
		if (input > 0)
			return 1;

		return 0;
	}
}

class LogisticFunction implements ActivatorFunction {
	public double evaluate(double input) {
		return 1.0 / (1.0 + Math.pow(Math.E, -1 * input));
	}
}

class Perceptron {

	public double bias;
	public double biasWeight;

	public double[] inputWeight;

	public ActivatorFunction activatorFunction;

	public static final ActivatorFunction STEP_FUNCTION = new StepFunction();
	public static final ActivatorFunction SIGMOID_FUNCTION = new LogisticFunction();

	public static final double DEFAULT_WEIGHT = 0.345;
	public static final int DEFAULT_BIAS = -1;

	public Perceptron(int inputSize, ActivatorFunction activatorFunction) {
		this.bias = Perceptron.DEFAULT_BIAS;
		this.biasWeight = Perceptron.DEFAULT_WEIGHT;

		this.inputWeight = new double[inputSize];
		for (int i = 0; i < inputSize; i++)
			this.inputWeight[i] = Perceptron.DEFAULT_WEIGHT;

		this.activatorFunction = activatorFunction;
	}

	public double evaluate(double[] input) {
		assert input.length == inputWeight.length;

		double sum = bias * biasWeight;
		for (int i = 0; i < inputWeight.length; i++)
			sum += input[i] * inputWeight[i];

		return activatorFunction.evaluate(sum);
	}
}	

class PerceptronLayer {

	private int inputSize;
	private Perceptron[] perceptrons;

	public PerceptronLayer(int size, int inputSize, ActivatorFunction activatorFunction) {
		this.inputSize = inputSize;
		this.perceptrons = new Perceptron[size];
		for (int i = 0; i < size; i++)
			perceptrons[i] = new Perceptron(inputSize, activatorFunction);
	}

	public double[] evaluate(double[] input) {

		double[] output = new double[perceptrons.length];
		for (int i = 0; i < perceptrons.length; i++)
			output[i] = perceptrons[i].evaluate(input);

		return output;
	}

	public int getSize() {
		return perceptrons.length;
	}
}

public class MultiLayerPerceptron {

	private int inputSize;
	private PerceptronLayer[] layers;

	public MultiLayerPerceptron(int inputSize, int[] layerSize, ActivatorFunction activatorFunction) {
		layers = new PerceptronLayer[layerSize.length];
		layers[0] = new PerceptronLayer(layerSize[0], inputSize, activatorFunction);
		for (int i = 1; i < layerSize.length; i++)
			layers[i] = new PerceptronLayer(layerSize[i], layerSize[i-1], activatorFunction);
	}

	public double[] evaluate(double[] input) {

		double[] output = input;
		for (PerceptronLayer layer : layers) 
			output = layer.evaluate(output);

		return output;
	}

	public static void main(String argv[]) {
		// Perceptron perceptron = new Perceptron(3, Perceptron.SIGMOID_FUNCTION);
		// System.out.println(perceptron.evaluate(new double[]{0, 0, 0}));

		// PerceptronLayer layer = new PerceptronLayer(3, 4, Perceptron.SIGMOID_FUNCTION);
		// double[] result = layer.evaluate(new double[]{1, 1, 0.4, 0.3});
		// for (double hasil : result) {
		// 	System.out.println(hasil);
		// }

		// PerceptronLayer layer2 = new PerceptronLayer(1, 3, Perceptron.SIGMOID_FUNCTION);
		// result = layer2.evaluate(layer.evaluate(new double[]{1, 1, 0.4, 0.3}));
		// for (double hasil : result) {
		// 	System.out.println(hasil);
		// }

		int[] layerSize = new int[]{3, 1};
		int inputSize = 4;

		MultiLayerPerceptron mlp = new MultiLayerPerceptron(inputSize, layerSize, Perceptron.SIGMOID_FUNCTION);
		double[] result = mlp.evaluate(new double[]{1, 1, 0.4, 0.3});
		for (double hasil : result) {
			System.out.println(hasil);
		}
	}
}