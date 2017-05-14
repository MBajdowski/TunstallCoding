package Gui.view.common;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileOrDirectoryPrompter {

    public static File askUserForFile(final Stage stage, final String title) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        return chooser.showOpenDialog(stage);
    }

    public static File askUserForDirectory(final Stage stage, final String title, final String currentPath) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(
                new File(currentPath)
        );
        return chooser.showDialog(stage);
    }

}
