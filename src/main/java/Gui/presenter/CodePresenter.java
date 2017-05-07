package Gui.presenter;

import Gui.presenter.events.CodeFileEvent;
import Gui.presenter.events.EventTypes;
import Gui.view.common.ComponentsUtil;
import Gui.view.common.FileOrDirectoryPrompter;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;

public class CodePresenter implements Presenter {

    private static final int DEFAULT_SIZE_OF_CODE_WORD = 10;

    @FXML
    private AnchorPane container;

    @FXML
    private TextField fileToCodePathField;

    @FXML
    private TextField outputFileFolderPathField;

    @FXML
    private TextField sizeOfCodeWord;

    private Stage stage;
    private Stage primaryStage;
    private File fileToCode;
    private String outputFolderPath;

    @FXML
    public void initialize() {
        container.setDisable(true);
        ComponentsUtil.convertToGTZeroIntegerField(sizeOfCodeWord, DEFAULT_SIZE_OF_CODE_WORD);
        fileToCode = FileOrDirectoryPrompter.askUserForFile(stage, "Wybierz plik do zakodowania");
        fileToCodePathField.setText(fileToCode.getPath());
        outputFolderPath = fileToCode.getParent();
        outputFileFolderPathField.setText(outputFolderPath);

        container.setDisable(false);
    }

    @FXML
    public void handleCodeButton() {
        Integer codeWordLength = Integer.valueOf(sizeOfCodeWord.getText());
        Event event = new CodeFileEvent(fileToCode, outputFolderPath, codeWordLength, EventTypes.CODE);
        primaryStage.fireEvent(event);
        stage.close();
    }

    @FXML
    public void handleChangeOutputFolderButton() {
        File outputDirectoryPath = FileOrDirectoryPrompter.askUserForDirectory(stage, "Wybierz folder wyjściowy");
        outputFolderPath = outputDirectoryPath.getPath();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPrimaryStage(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
