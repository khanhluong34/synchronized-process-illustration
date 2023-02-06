import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import model.BuyerThread;
import model.SellerThread;
import model.StockMonitor;

public class StockMonitorApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("StockMonitorApp.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("An error occurred while loading the FXML file: " + e);
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
