import java.util.ArrayList;

public class Tester {

	public static final int NEURON_INPUT = 784;
	public static final int NEURON_HIDDEN = 784 * 20;
	public static final int NEURON_OUTPUT = 10;
	public static final int NUMBER_OF_TEST = 10000;

	public static void main(String[] args) {

		MLP mlp = new MLP(NEURON_INPUT, NEURON_HIDDEN, NEURON_OUTPUT);
		mlp.setLearningRate(0.7);

		try {
			CSVReader cr = new CSVReader();
			ArrayList<double[]> dataset = cr.read("dataset/d5problem-original.dataset.csv", NUMBER_OF_TEST);


			for (int i=0;i<NUMBER_OF_TEST;i++) {
				double[] datai = dataset.get(i);
				double[] pattern = new double[datai.length-10];
				double[] desiredOutput = new double[10];

				for (int j=0;j<pattern.length;j++) {
					pattern[j] = datai[j];
				}

				for (int j=0;j<10;j++) {
					desiredOutput[j] = datai[datai.length-10+j];
				}
				//System.out.println(pattern.length + " " + desiredOutput.length);

				double[] output = mlp.train(pattern, desiredOutput);

				double err = 0;
				for (int j=1;j<output.length;j++) { 
					//System.out.print(output[j] + " "); 
					err += Math.abs(output[j] - desiredOutput[j-1]);
				}
				//System.out.println();
				//for (int j=0;j<desiredOutput.length;j++) System.out.print(desiredOutput[j] + " "); System.out.println();
				System.out.println(err);

			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


}