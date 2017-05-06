import Tunstall.Tunstall;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Main {

    public static String path="test.txt";
    public static int k=9;

    public static void main(String[] args) {

        try {
            Tunstall tunstall = new Tunstall(FileUtils.readFileToByteArray(new File(path)),k);
            FileUtils.writeByteArrayToFile(new File(path+".tstl"), tunstall.generateCodedFile());
        } catch (InvalidArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception while reading/saving the file");
        }

    }
}

