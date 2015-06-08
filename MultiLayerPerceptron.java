import java.io.*;
import java.util.*;

class MathHelper {

	public static double stepFunction(double input) {
		if (input > 0)
			return 1;

		return 0;
	}

	public static double sigmoidFunction(double input) {
		return Math.tanh(input);
	}
}

public class MultiLayerPerceptron {

	class Perceptron {

		public double bias;
		public double biasWeight;

		public int inputSize;
		public double[] inputWeight;

		public boolean isUsingSigmoid;

		public static final boolean SIGMOID = true;
		public static final boolean STEP = false;

		public static final double DEFAULT_WEIGHT = 0.345;
		public static final int DEFAULT_BIAS = -1;

		public Perceptron(int inputSize, boolean isUsingSigmoid) {
			this.bias = Perceptron.DEFAULT_BIAS;
			this.biasWeight = Perceptron.DEFAULT_WEIGHT;

			this.inputSize = inputSize;
			for (int i = 0; i < inputSize; i++)
				this.inputWeight[i] = Perceptron.DEFAULT_WEIGHT;

			this.isUsingSigmoid = isUsingSigmoid;
		}

		// public double calculate(double input) {
		// 	double sum = input * inputWeight + bias * biasWeight;

		// 	if (this.isUsingSigmoid)
		// 		return MathHelper.sigmoidFunction(sum);
		// 	else
		// 		return MathHelper.stepFunction(sum);
		// }
	}	

	public int hiddenLayerNum;
}