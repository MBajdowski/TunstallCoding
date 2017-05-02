import Tunstall.Tunstall;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Main {

    public static String path="test.jpg";
    public static int k=3;

    public static void main(String[] args) {

        System.out.println("Opening the file");
        try {
            byte[] byteArray = FileUtils.readFileToByteArray(new File(path));
            System.out.println("Analysing the file");
            double[] probability = Tunstall.getProbability(byteArray);
            System.out.println("Building tree using k="+k);

        } catch (IOException e) {
            System.out.println("Exception in opening the file");
        }


    }
}
