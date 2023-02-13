import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

public class StockMonitorApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("StockMonitorApp.fxml"));
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);

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
