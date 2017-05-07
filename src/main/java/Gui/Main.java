package Gui;

import Gui.presenter.MainPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Kodek Tunstalla");
        primaryStage.setResizable(false);

        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> showAppError(throwable));

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/main.fxml"));
            BorderPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            MainPresenter mainPresenter = loader.getController();
            mainPresenter.setStage(primaryStage);
        } catch (IOException e) {
            showAppError(e);
            e.printStackTrace();
        }
    }

    private void showAppError(Throwable throwable) {
        System.out.println(throwable);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("");
        alert.setContentText("Wystąpił błąd aplikacji.");

        alert.showAndWait();
    }
}
