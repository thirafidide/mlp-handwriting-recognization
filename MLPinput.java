import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/*
    how to use:
    java MLPinput <file destination>
*/
public class MLPinput{
    public static ArrayList<Integer> arrLabel = new ArrayList<Integer>(); //data label
    public static ArrayList<ArrayList> arrData = new ArrayList<ArrayList>(); //data pixel array 2D

    public static void main( String[] args) {
      
      BufferedReader br = null;
        String[] input = new String[2];
        try {    
            br = new BufferedReader(new FileReader(args[0]));
            br.readLine(); // buang line pertama
            String[] arrInput;
            String currentLine; 
            while((currentLine = br.readLine()) != null){
                System.out.println(currentLine);
                ArrayList<Integer> arrInnerData = new ArrayList<Integer>();
                arrInput = currentLine.split(",");
                for(int ii =0; ii<arrInput.length; ii++){
                    if(ii==0)
                        arrLabel.add(Integer.parseInt(arrInput[ii]));
                    else
                        arrInnerData.add(Integer.parseInt(arrInput[ii]));
                }
                arrData.add(arrInnerData);
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    } 
    //buat debug print array label
    private static void printLabel(ArrayList<Integer> arr){
        for(Integer item : arr){
            System.out.print(item+" ");
        }
    }

    //buat debug print array data
    private static void printData(ArrayList<ArrayList> arr){
        for(Integer row : arr){
            for(Integer col: row)
                System.out.print(col+" ");
            System.out.println();
        }
    }

}