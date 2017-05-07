package Gui.presenter;

import FileAnalysis.FileAnalyzer;
import FileAnalysis.HistogramData;
import Gui.presenter.events.CodeFileEvent;
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

    }

    @FXML
    public void handleExitButton() {
        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        stage.addEventHandler(EventTypes.CODE, this::handleCodeEvent);
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
