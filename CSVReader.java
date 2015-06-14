import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CSVReader {

	/**
	 *
	 * @return 784 input + 4 bit desired output
	 *
	 */
	public ArrayList<double[]> read(String fileLoc, int totalData) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(fileLoc));
		String line = "";

		ArrayList<double[]> res = new ArrayList<double[]>();

		boolean firstLine = true;
		int countData = 0;
		while ((line = br.readLine()) != null) {
			if (firstLine) { firstLine = false; continue; }

			String[] temp = line.split(",");
			double[] in = new double[temp.length + 9];

			for (int i=1;i<temp.length;i++) {
				in[i-1] = (Double.parseDouble(temp[i]) / 255);
				//System.out.print(in[i-1] + " ");
			}
			//System.out.println("\n");

			int out = Integer.parseInt(temp[0]);
			for (int i=0;i<10;i++) if (i==out) in[temp.length-1+i] = 1; else in[temp.length-1+i] = 0;

			//System.out.println(in[temp.length-1] + " " + in[temp.length] + " " + in[temp.length+1] + " " + in[temp.length+2]);
			res.add(in);

			if (++countData == totalData) break;
		}

		return res;
	}
}