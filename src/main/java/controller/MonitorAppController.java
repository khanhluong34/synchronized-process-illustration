package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.concurrent.Task;
import model.BuyerThread;
import model.SellerThread;
import model.StockMonitor;
import model.TradeRequest;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

public class MonitorAppController implements Initializable {
    private static MonitorAppController controller;

    @FXML
    private HBox boxNotification;
    @FXML
    private Text notification;
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
    private Slider latencySlider;
    @FXML
    private Slider numberOfBuyers;
    @FXML
    private Slider numberOfSellers;
    @FXML
    private Slider initQtySlider;
    @FXML
    private Slider maxQtySlider;

    @FXML
    private TableView<TradeRequest> tableHistory;
    @FXML
    private TableColumn<TradeRequest, String> colClient;
    @FXML
    private TableColumn<TradeRequest, String> colClient1;
    @FXML
    private TableColumn<TradeRequest, Integer> colIdx;
    @FXML
    private TableColumn<TradeRequest, Integer> colIdx1;
    @FXML
    private TableColumn<TradeRequest, Integer> colQty;
    @FXML
    private TableColumn<TradeRequest, Integer> colQty1;
    @FXML
    private TableColumn<TradeRequest, String> colType;
    @FXML
    private TableColumn<TradeRequest, String> colType1;
    @FXML
    private TableView<TradeRequest> tableQueue;

    private TradingTask task;

    public void stopTask() {
        if (task != null) {
            task.cancelled();
        }
    }

    public MonitorAppController() {
        this.controller = this;
    }
    public static MonitorAppController getController() {
        if (controller == null) {
            controller = new MonitorAppController();
        }
        return controller;
    }

    @FXML
    void btnAboutOnPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About the app");
        alert.setHeaderText("About the app");
        alert.setContentText("This is an example of the case of process synchronization using monitor. This display the case of Stock Trading Monitor ");

        alert.showAndWait();

    }
    boolean checkQuantityCondition(int initQty, int maxQty){
        if (initQty > maxQty) {
            raiseAlert();
            return false;
        } else {
            return true;
        }
    }
    void raiseAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Reset the settings");
        alert.setHeaderText("Invalid configuration");
        alert.setContentText("The initial quantity must not exceed the maximum quantity");
        alert.showAndWait();
    }
    @FXML
    void btnStartOnPressed(ActionEvent event) {
        if (btnStart.getText().equals("Start Session") && task == null) {
            btnStart.setText("Stop Session");
            btnStart.setStyle("-fx-background-color: #ff5757");

            int noBuyers = (int) numberOfBuyers.getValue();
            int noSellers = (int) numberOfSellers.getValue();
            int latency = (int) latencySlider.getValue();
            int initQty = (int) initQtySlider.getValue();
            int maxQty = (int) maxQtySlider.getValue();

            if (checkQuantityCondition(initQty, maxQty)) {
                StockMonitor monitor = new StockMonitor(initQty, maxQty, latency);
                task = new TradingTask(monitor, noBuyers, noSellers);
                Thread thread = new Thread(task);
                thread.start();
            } else {
                btnStart.setText("Start Session");
                textQty.setText("...");
                textTrader.setText("...");
                textBuy.setText("...");
                textSell.setText("...");
                notification.setText("System status");
                boxNotification.setStyle("-fx-background-color: #ff5757");
                btnStart.setStyle("-fx-background-color: #3f51b5");
            }

        } else {
            task.cancel();
            task = null;
            btnStart.setText("Start Session");
            textQty.setText("...");
            textTrader.setText("...");
            textBuy.setText("...");
            textSell.setText("...");
            notification.setText("Session stopped");
            boxNotification.setStyle("-fx-background-color: #ff5757");
            btnStart.setStyle("-fx-background-color: #3f51b5");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
    }

    private void initializeTable() {
        tableHistory.setPlaceholder(new Label("No transaction history"));
        tableQueue.setPlaceholder(new Label("No transaction queue"));

        colIdx.setCellValueFactory(new PropertyValueFactory<TradeRequest, Integer>("index"));
        colIdx1.setCellValueFactory(new PropertyValueFactory<TradeRequest, Integer>("index"));

        colClient.setCellValueFactory(new PropertyValueFactory<TradeRequest, String>("nameTrader"));
        colClient1.setCellValueFactory(new PropertyValueFactory<TradeRequest, String>("nameTrader"));

        colQty.setCellValueFactory(new PropertyValueFactory<TradeRequest, Integer>("quantity"));
        colQty1.setCellValueFactory(new PropertyValueFactory<TradeRequest, Integer>("quantity"));

        // return string value of TradeRequestType
        colType.setCellValueFactory(new PropertyValueFactory<TradeRequest, String>("type"));
        colType1.setCellValueFactory(new PropertyValueFactory<TradeRequest, String>("type"));

        // Change text color of row based on type
        colType.setCellFactory(column -> {
            return new TableCell<TradeRequest, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        if (item.equals("BUY")) {
                            setStyle("-fx-text-fill: #157936; -fx-font-weight: bold");
                        } else {
                            setStyle("-fx-text-fill: #ff0000; -fx-font-weight: bold");
                        }
                    }
                }
            };
        });

        colType1.setCellFactory(column -> {
            return new TableCell<TradeRequest, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        if (item.equals("BUY")) {
                            setStyle("-fx-text-fill: #157936; -fx-font-weight: bold");
                        } else {
                            setStyle("-fx-text-fill: #ff0000; -fx-font-weight: bold");
                        }
                    }
                }
            };
        });

        // Change the color of quantity column based on type
        colQty.setCellFactory(column -> {
            return new TableCell<TradeRequest, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item.toString());
                        if (getTableRow().getItem() != null) {
                            TradeRequest request = (TradeRequest) getTableRow().getItem();
                            if (request.getType().equals("BUY")) {
                                setStyle("-fx-text-fill: #157936; -fx-font-weight: bold");
                            } else {
                                setStyle("-fx-text-fill: #ff0000; -fx-font-weight: bold");
                            }
                        }
                    }
                }
            };
        });

        colQty1.setCellFactory(column -> {
            return new TableCell<TradeRequest, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item.toString());
                        if (getTableRow().getItem() != null) {
                            TradeRequest request = (TradeRequest) getTableRow().getItem();
                            if (request.getType().equals("BUY")) {
                                setStyle("-fx-text-fill: #157936; -fx-font-weight: bold");
                            } else {
                                setStyle("-fx-text-fill: #ff0000; -fx-font-weight: bold");
                            }
                        }
                    }
                }
            };
        });
    }

    class TradingTask extends Task<Void> {
        private final StockMonitor monitor;
        private final String[] buyerNames = {"Luong", "Son", "Khanh", "Binh", "Jack"};
        private BuyerThread[] buyers;
        private final String[] sellerNames = {"Quyet", "David", "Zed", "Yasou", "Garen"};
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
        private String getBuyersNameString() {
            String s = "";
            for (BuyerThread buyer: this.buyers) {
                s += buyer.getName() + "\n";
            }
            return s;
        }
        private String getSellersNameString() {
            String s = "";
            for (SellerThread seller: this.sellers) {
                s += seller.getName() + "\n";
            }
            return s;
        }
        private void initializeClient() {
            System.out.println("\n");
            this.buyers = new BuyerThread[noBuyers];
            int[] stockBuying = {30, 50, 40, 35, 20};
            for (int i = 0; i < noBuyers; i++) {
                this.buyers[i] = new BuyerThread(this.buyerNames[i], this.monitor, stockBuying[i]);
            }

            this.sellers = new SellerThread[noSellers];
            int[] stockSelling = {100, 50, 70, 55, 25};
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

                    // Update the content of notification
                    notification.setText(monitor.getStatus().getContent());
                    boxNotification.setStyle(monitor.getStatus().getStyle());

                    if (monitor.getHandlingRequest() != null) {
                        textTrader.setText(monitor.getHandlingRequest().getNameTrader());
                    }

                    // Update the content of the client
                    textSell.setText(getSellersNameString());
                    textBuy.setText(getBuyersNameString());

                    // Update the table of queue
                    tableQueue.setItems(monitor.getQueuedRequest());
                    // Update the table of transaction history
                    tableHistory.setItems(monitor.getTransactionHistory());
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
