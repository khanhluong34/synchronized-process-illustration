package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.BuyerThread;
import model.SellerThread;
import model.StockMonitor;

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

    private TradingTask task;

    @FXML
    void btnAboutOnPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("This is an example of the case of Process synchronization using Monitor ");
        alert.setContentText("This display the case of Stock Trading ");
        alert.showAndWait();
    }

    @FXML
    void btnStartOnPressed(ActionEvent event) {
        if (btnStart.getText().equals("Start Session") && task == null) {
            btnStart.setText("Stop Session");
            int noBuyers = (int) numberOfBuyers.getValue();
            int noSellers = (int) numberOfSellers.getValue();
            int speed = (int) speedSlider.getValue();
            StockMonitor monitor = new StockMonitor(200);
            task = new TradingTask(monitor, noBuyers, noSellers);
            Thread thread = new Thread(task);
            thread.start();
        } else {
            task.cancel();
            task = null;
            btnStart.setText("Start Session");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader historyLoader = new FXMLLoader(getClass().getResource("/TransactionTable.fxml"));
        try {
            Parent historyRoot = historyLoader.load();
            transHistoryPane.setContent(historyRoot);
        } catch (Exception e) {
            System.out.println("An error occurred while loading the FXML file: " + e);
            e.printStackTrace();
        }

        FXMLLoader queueLoader = new FXMLLoader(getClass().getResource("/TransactionTable.fxml"));
        try {
            Parent queueRoot = queueLoader.load();
            transQueuePane.setContent(queueRoot);
        } catch (Exception e) {
            System.out.println("An error occurred while loading the FXML file: " + e);
            e.printStackTrace();
        }
    }

    class TradingTask extends Task<Void> {
        private final StockMonitor monitor;
        private final String[] buyerNames = {"Luong", "Son", "Khanh", "Binh", "Jack"};
        private BuyerThread[] buyers;
        private final String[] sellerNames = {"Bob", "David", "Zed", "Yasou", "Garen"};
        private SellerThread[] sellers;
        private final int noBuyers;
        private final int noSellers;

        public TradingTask(StockMonitor monitor, int noBuyers, int noSellers) {
            this.monitor = monitor;
            this.noBuyers = noBuyers;
            this.noSellers = noSellers;
        }

        private void startTrading() {
            for (BuyerThread buyer : this.buyers) {
                buyer.start();
            }

            for (SellerThread seller : this.sellers) {
                seller.start();
            }
        }

        private void initializeClient() {
            System.out.println("\n");
            this.buyers = new BuyerThread[noBuyers];
            int[] stockBuying = {30, 50, 40, 35, 20};
            for (int i = 0; i < noBuyers; i++) {
                this.buyers[i] = new BuyerThread(this.buyerNames[i], this.monitor, stockBuying[i]);
            }

            this.sellers = new SellerThread[noSellers];
            int[] stockSelling = {60, 50, 70, 55, 25};
            for (int i = 0; i < noSellers; i++) {
                this.sellers[i] = new SellerThread(this.sellerNames[i], this.monitor, stockSelling[i]);
            }
        }

        @Override
        protected Void call() throws Exception {
            initializeClient();
            startTrading();
            while (!isCancelled()) {
                Platform.runLater(() -> {
                    // Update the value of quantity
                    textQty.setText(String.valueOf(monitor.getQuantity()));

                });
                Thread.sleep(1000);
            }
            return null;
        }

        @Override
        protected void cancelled() {
            super.cancelled();
            for (BuyerThread buyer : this.buyers) {
                buyer.stop();
            }

            for (SellerThread seller : this.sellers) {
                seller.stop();
            }
        }
    }
}
