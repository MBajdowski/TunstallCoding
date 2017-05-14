package Gui.presenter;

import FileAnalysis.FileAnalyzer;
import FileAnalysis.HistogramData;
import Gui.presenter.events.CodeFileEvent;
import Gui.presenter.events.DecodeFileEvent;
import Gui.presenter.events.EventTypes;
import Gui.view.HistogramGraphBuilder;
import Tunstall.Tunstall;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class MainPresenter implements Presenter {

    private Stage stage;

    @FXML
    private Label entropyLabel;

    @FXML
    private Label entropyValueLabel;

    @FXML
    private AnchorPane histogramGraphPane;

    @FXML
    public void initialize() {
        entropyLabel.setVisible(false);
    }

    @FXML
    public void handleCodeButton() {
        WindowLoader<CodePresenter, AnchorPane> configLoader = new WindowLoader<CodePresenter, AnchorPane>() {
            @Override
            public void setUpController(CodePresenter controller) {
                controller.setPrimaryStage(stage);
            }
        };
        configLoader.load("/Gui/view/code.fxml", stage);
    }

    @FXML
    public void handleDecodeButton() {
        WindowLoader<DecodePresenter, AnchorPane> configLoader = new WindowLoader<DecodePresenter, AnchorPane>() {
            @Override
            public void setUpController(DecodePresenter controller) {
                controller.setPrimaryStage(stage);
            }
        };
        configLoader.load("/Gui/view/decode.fxml", stage);
    }

    @FXML
    public void handleExitButton() {
        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        stage.addEventHandler(EventTypes.CODE, this::handleCodeEvent);
        stage.addEventHandler(EventTypes.DECODE, this::handleDecodeEvent);
    }

    private void handleCodeEvent(CodeFileEvent event) {
        try {
            byte[] inputFileByteArray = FileUtils.readFileToByteArray(event.getFileToCode());
            Tunstall tunstall = new Tunstall(inputFileByteArray, event.getCodeWordLength());

            writeFileStatistics(tunstall);

            String outputFileFullName = event.getFileToDecodeFolder() + File.separator + event.getFileToCode().getName() + ".tstl";
            FileUtils.writeByteArrayToFile(new File(outputFileFullName), tunstall.generateCodedFile());
        } catch (InvalidArgumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDecodeEvent(DecodeFileEvent event) {
        try {
            byte[] inputFileByteArray = FileUtils.readFileToByteArray(event.getFileToDecode());
            Tunstall tunstall = new Tunstall(inputFileByteArray);

            String outputFileFullName = event.getFileToDecodeFolder() + File.separator + event.getFileToDecode().getName().replace(".tstl", "");
            FileUtils.writeByteArrayToFile(new File(outputFileFullName), tunstall.decodeFile());

            writeSuccessInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSuccessInfo() {
        entropyLabel.setVisible(false);
        entropyValueLabel.setText("");

        histogramGraphPane.getChildren().clear();
        Label successLabel = new Label("Dekodowanie zakończone.");
        successLabel.setLayoutX(200);
        successLabel.setLayoutY(170);
        histogramGraphPane.getChildren().add(successLabel);
    }

    private void writeFileStatistics(Tunstall tunstall) {
        entropyLabel.setVisible(true);
        double entropy = FileAnalyzer.calculateEntropy(tunstall);
        entropyValueLabel.setText(String.valueOf(entropy));

        Collection<HistogramData> histogramData = FileAnalyzer.calculateHistogramData(tunstall);
        BarChart<String, Number> histogramGraph = HistogramGraphBuilder.createGraph(histogramData);
        histogramGraphPane.getChildren().clear();
        histogramGraphPane.getChildren().add(histogramGraph);
    }

}
