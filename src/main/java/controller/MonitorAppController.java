package controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.URL;
import java.util.ResourceBundle;

public class MonitorAppController implements Initializable {
    @FXML
    private Button btnAbout;

    @FXML
    private Button btnStart;

    @FXML
    private Text textBuy;

    @FXML
    private Text textQty;

    @FXML
    private Text textSell;

    @FXML
    private Text textTrader;
    @FXML
    private Slider speedSlider;
    @FXML
    private Slider numberOfBuyers;
    @FXML
    private Slider numberOfSellers;

    @FXML
    private TitledPane transHistoryPane;

    @FXML
    private TitledPane transQueuePane;

    @FXML
    void btnAboutOnPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("This is an example of the case of Process synchronization using Monitor ");
        alert.setContentText("This display the case of Stock Trading ");
        alert.showAndWait();
    }

    private Service<Void> backgroundThread;
    private boolean isRunning = false;

    @FXML
    void btnStartOnPressed(ActionEvent event) {
        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        return null;
                    }
                };
            }
        };

        textTrader.textProperty().bind(backgroundThread.messageProperty());

        backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                System.out.println("Done!");
            }
        });

        backgroundThread.restart();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TransactionTable.fxml"));
        try {
            Parent root = loader.load();
            transHistoryPane.setContent(root);
        } catch (Exception e) {
            System.out.println("An error occurred while loading the FXML file: " + e);
            e.printStackTrace();
        }
    }
}
