package Gui.presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Base loader for opening windows in application.
 *
 * @param <Controller_>
 * @param <RootPane>
 */
public class WindowLoader<Controller_ extends Presenter, RootPane extends Pane> {

    public void load(final String viewPath, final Stage primaryStage) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(viewPath));
            RootPane pane = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(pane);

            stage.initModality(Modality.NONE);
            stage.initOwner(primaryStage);
            stage.setScene(scene);

            Controller_ controller = loader.getController();
            controller.setStage(stage);

            setUpController(controller);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Can be overrided to provide functionality
     */
    public void setUpController(Controller_ controller) {
    }
}