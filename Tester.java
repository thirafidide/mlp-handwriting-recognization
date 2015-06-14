import java.util.ArrayList;

public class Tester {

	public static final int NUMBER_OF_TEST = 10;
	public static final double TOL = 0.01;
	public static final int ITERATION_LIMIT = 500;


	public static final double LEARNING_RATE = 0.1;
	public static final int N_HIDDEN_LAYER = 2 * 675 + 1;

	public static void main(String[] args) {

		try {
			ArrayList[] dataset = MLPinput.read("dataset/d5problem-original.dataset.csv");
			ArrayList<ArrayList> tempPattern = dataset[1];
			ArrayList<Integer> tempOutput = dataset[0];
			double[][] normalizedData = new double[tempPattern.size()][tempPattern.get(0).size()];
			double[][] normalizedDesiredOutput = new double[tempOutput.size()][10];


			for (int i=0;i<tempPattern.size();i++) {

				ArrayList<Integer> pattern = tempPattern.get(i);
				int output = tempOutput.get(i);

				normalizedDesiredOutput[i][output] = 1;
				for (int j=0;j<pattern.size();j++) {
					normalizedData[i][j] = pattern.get(j);
				}
			}
			
			crossValidation(normalizedData, normalizedDesiredOutput, NUMBER_OF_TEST);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void crossValidation(double[][] patterns, double[][] desiredOutput, int numberOfTest) {

		int numberOfDataEachTest = patterns.length / numberOfTest;

		for (int i=0;i<numberOfTest;i++) {

			MLP mlp = new MLP(patterns[0].length, N_HIDDEN_LAYER, desiredOutput[0].length);
			mlp.setLearningRate(LEARNING_RATE);


			// training
			train(mlp, patterns, desiredOutput, numberOfDataEachTest, i, true);

			// validation
			double validationError = 0;
			for (int j=numberOfDataEachTest*i;j<numberOfDataEachTest*i+numberOfDataEachTest;j++) {
				double[] output = mlp.passNet(patterns[j]);

				validationError += evaluateError(output, desiredOutput[j]);
			}
			validationError /= numberOfDataEachTest;
			System.out.println("Validation error: " + validationError);

		}
	}

	public static void train(MLP mlp, double[][] patterns, double[][] desiredOutput, int numberOfDataEachTest, int iteration, boolean isUsingIteration) {
		if (isUsingIteration) {
			for (int i=0;i<ITERATION_LIMIT;i++) {
				double err = 0;
				for (int j=0;j<patterns.length;j++) {
					if (j/numberOfDataEachTest == iteration) continue;
					
					double[] output = mlp.train(patterns[j], desiredOutput[j]);

					//System.out.println(output[1] + " " + desiredOutput[j][0]);
					err += evaluateError(output, desiredOutput[j]);
				}
				err /= patterns.length;
				System.out.println(err);
			}
		}
		else {
			double err = Double.POSITIVE_INFINITY;
			while (err > TOL) {
				err = 0;
				for (int j=0;j<patterns.length;j++) {
					if (j/numberOfDataEachTest == iteration) continue;
					
					double[] output = mlp.train(patterns[j], desiredOutput[j]);
					err += evaluateError(output, desiredOutput[j]);
				}
				err /= patterns.length;
				System.out.println(err);
			}
		}
	}

	public static double evaluateError(double[] output, double[] desiredOutput) {
		double res = 0;
		for (int i=0;i<desiredOutput.length;i++) {
			res += (output[i+1] - desiredOutput[i]) * (output[i+1] - desiredOutput[i]);
		}

		return Math.sqrt(res);
	}


}