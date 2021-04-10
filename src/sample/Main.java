package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root=loader.load();
        Controller controller=loader.getController();
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 701, 250));
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Zapisywanie do pliku.");
                alert.setHeaderText("Czy chcesz zapisac dane");
                alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.YES) {
                    controller.writeToCSV();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}