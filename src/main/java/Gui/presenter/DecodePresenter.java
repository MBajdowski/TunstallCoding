package Gui.presenter;

import Gui.presenter.events.DecodeFileEvent;
import Gui.presenter.events.EventTypes;
import Gui.view.common.FileOrDirectoryPrompter;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;

public class DecodePresenter implements Presenter {

    private static final int DEFAULT_SIZE_OF_CODE_WORD = 10;

    @FXML
    private AnchorPane container;

    @FXML
    private TextField fileToDecodePathField;

    @FXML
    private TextField outputFileFolderPathField;

    private Stage stage;
    private Stage primaryStage;
    private File fileToDecode;

    @FXML
    public void initialize() {
        container.setDisable(true);
        fileToDecode = FileOrDirectoryPrompter.askUserForFile(stage, "Wybierz plik do zdekodowania");
        if (fileToDecode == null) {
            return;
        }
        fileToDecodePathField.setText(fileToDecode.getPath());
        outputFileFolderPathField.setText(fileToDecode.getParent());

        container.setDisable(false);
    }

    @FXML
    public void handleDecodeButton() {
        Event event = new DecodeFileEvent(fileToDecode, outputFileFolderPathField.getText(), EventTypes.DECODE);
        primaryStage.fireEvent(event);
        stage.close();
    }

    @FXML
    public void handleChangeOutputFolderButton() {
        File outputDirectoryPath = FileOrDirectoryPrompter.askUserForDirectory(stage, "Wybierz folder wyjściowy",
                outputFileFolderPathField.getText());
        outputFileFolderPathField.setText(outputDirectoryPath.getPath());
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        if (fileToDecode == null) {
            Platform.runLater(stage::close);
        }
    }

    public void setPrimaryStage(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
