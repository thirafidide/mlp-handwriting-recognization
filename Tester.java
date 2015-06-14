import java.util.ArrayList;
import java.util.Random;

public class Tester {

	public static final int NUMBER_OF_TEST = 10;
	public static final int ITERATION_LIMIT = 500;
	public static final int NUMBER_OF_GROUP = 10;


	public static double LEARNING_RATE = 0.1;
	public static int N_HIDDEN_LAYER = 2 * 675 + 1;

	public static void main(String[] args) {

		try {
			System.out.println("Reading file...");
			ArrayList[] dataset = MLPinput.read("dataset/d5problem-original.dataset.csv");
			ArrayList<ArrayList> tempPattern = dataset[1];
			ArrayList<Integer> tempOutput = dataset[0];
			double[][] normalizedData = new double[tempPattern.size()][tempPattern.get(0).size()];
			double[][] normalizedDesiredOutput = new double[tempOutput.size()][10];

			final int N_HIDDEN_LAYER = Integer.parseInt(args[0]);
			final double LEARNING_RATE = Double.parseDouble(args[1]);

			for (int i=0;i<tempPattern.size();i++) {

				ArrayList<Integer> pattern = tempPattern.get(i);
				int output = tempOutput.get(i);

				normalizedDesiredOutput[i][output] = 1;
				for (int j=0;j<pattern.size();j++) {
					normalizedData[i][j] = pattern.get(j);
				}
			}
			
			System.out.println("Doing cross validation...");
			crossValidation(normalizedData, normalizedDesiredOutput);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void crossValidation(double[][] patterns, double[][] desiredOutput) {

		for (int i=0;i<NUMBER_OF_TEST;i++) {

			MLP mlp = new MLP(patterns[0].length, N_HIDDEN_LAYER, desiredOutput[0].length);
			mlp.setLearningRate(LEARNING_RATE);

			train(mlp, patterns, desiredOutput);

		}
	}

	public static void train(MLP mlp, double[][] patterns, double[][] desiredOutput) {

		int rand = (new Random()).nextInt(NUMBER_OF_GROUP);
		int patternPerGroup = patterns.length / NUMBER_OF_GROUP;

		int startGap = rand * patternPerGroup;

		int rand2 = (new Random()).nextInt(10);
		System.out.println(patternPerGroup + " " + startGap);

		for (int i=0;i<ITERATION_LIMIT;i++) {

			for (int j=startGap;j<startGap+patternPerGroup;j++) {
				if (((j % 1000) / 100) == rand2) continue;
				mlp.train(patterns[j], desiredOutput[j]);
			}

			int err = 0;
			for (int j=startGap;j<startGap+patternPerGroup;j++) {
				if (((j % 1000) / 100) != rand2) continue;

				double[] output = mlp.passNet(patterns[j]);
				err += countError(output, desiredOutput[j]);
			}
			System.out.println(i + "," + err);
		}
	}

	public static double evaluateError(double[] output, double[] desiredOutput) {
		double res = 0;
		for (int i=0;i<desiredOutput.length;i++) {
			res += (output[i+1] - desiredOutput[i]) * (output[i+1] - desiredOutput[i]);
		}

		return Math.sqrt(res);
	}

	public static int countError(double[] output, double[] desiredOutput) {
		int res = 0;
		for (int i=0;i<desiredOutput.length;i++) {
			if (stepFun(desiredOutput[i]) != stepFun(output[i+1])) res = 1;
		}
		return res;
	} 

	public static int stepFun(double num) {
		return (num >= 0.5) ? 1 : 0;
	}
}