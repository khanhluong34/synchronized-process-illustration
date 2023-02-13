import controller.MonitorAppController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.WindowEvent;

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
            MonitorAppController controller = MonitorAppController.getController();
            primaryStage.setOnCloseRequest(event -> {
                // Custom code to handle the close request
                controller.stopTask();
                System.exit(0);
            });
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("An error occurred while loading the FXML file: " + e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
