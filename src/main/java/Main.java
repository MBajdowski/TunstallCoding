import Tunstall.Tunstall;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Main {

    public static String path = "test/lena.pgm";
    public static int k = 16;

    public static void main(String[] args) {

        try {
            Tunstall coder = new Tunstall(FileUtils.readFileToByteArray(new File(path)), k);
            FileUtils.writeByteArrayToFile(new File(path + ".tstl"), coder.generateCodedFile());
            Tunstall decoder = new Tunstall(FileUtils.readFileToByteArray(new File(path + ".tstl")));
            String[] splitedPath = path.split("\\.");
            FileUtils.writeByteArrayToFile(new File(splitedPath[0] + "_decoded." + splitedPath[1]), decoder.decodeFile());
        } catch (InvalidArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception while reading/saving the file");
        }

    }


}

