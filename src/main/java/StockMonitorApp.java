import controller.MonitorAppController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.application.Application;

public class StockMonitorApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("StockMonitorApp.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Stock Trading App");
            String css = this.getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(event -> showQuitConfirmation(event));
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("An error occurred while loading the FXML file: " + e);
            e.printStackTrace();
        }
    }

    public static void showQuitConfirmation(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Quit");
        alert.setContentText("Are you sure you want to quit?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                MonitorAppController controller = MonitorAppController.getController();
                controller.stopTask();
                System.exit(0);
            } else {
                event.consume();
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
