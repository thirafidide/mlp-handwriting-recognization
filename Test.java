import java.util.ArrayList;
class Test{	
	public static void main( String[] args) {
      ArrayList<ArrayList>[] output = MLPinput.read(args[0]);
      //MLPinput.printData(output[1]);
      System.out.println("banyak colom: "+output[1].get(0).size());
    }
}