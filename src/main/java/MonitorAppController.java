import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import model.BuyerThread;
import model.SellerThread;
import model.StockMonitor;

public class MonitorAppController {
    private StockMonitor monitor;
    private String[] buyerNames = {"Luong", "Son", "Khanh", "Binh", "Jack"};
    private BuyerThread[] buyers;
    private String[] sellerNames = {"Bob", "David", "Zed", "Yasou", "Garen"};
    private SellerThread[] sellers;
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
    void btnAboutOnPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("This is an example of the case of Process synchronization using Monitor ");
        alert.setContentText("This display the case of Stock Trading ");
        alert.showAndWait();
    }

    @FXML
    void btnStartOnPressed(ActionEvent event) {
        System.out.println("-----Start session-----");
        // initialize the stock monitor, buyers and sellers every time that user clicks the start button
        this.monitor = new StockMonitor("FLC-HOSE", 100, 10);
        while (true) {
            initializeClient();
            observable();
            startTrading();
            try {
                Thread.sleep(3000); // Sleep for 1000 milliseconds (1 second)
            } catch (InterruptedException e) {
                // Handle the InterruptedException if it occurs
            }
        }


    }
    void startTrading() {
            for (int i = 0; i < this.buyers.length; i ++){
                this.buyers[i].start();
            }
            for (int i = 0; i < this.sellers.length; i ++) {
                this.sellers[i].start();
            }
    }
    void initializeClient() {

        // get the number of buyers from the slider
        int noBuyers = (int) this.numberOfBuyers.getValue();
        this.buyers = new BuyerThread[noBuyers];
        int[] stockBuying = {30, 50, 40, 35, 20};
        for (int i = 0; i < noBuyers; i ++) {
            this.buyers[i] = new BuyerThread(this.buyerNames[i],this.monitor, stockBuying[i]);
        }
        // get the number of sellers from the slider
        int noSellers = (int) this.numberOfSellers.getValue();
        this.sellers = new SellerThread[noSellers];
        int[] stockSelling = {60, 50, 70, 55, 25};
        for (int i = 0; i < noSellers; i ++) {
            this.sellers[i] = new SellerThread(this.sellerNames[i], this.monitor, stockSelling[i]);
        }
    }
    void observable() {
        StringProperty nameTextProperty = new SimpleStringProperty("None");
        this.textTrader.textProperty().bind(nameTextProperty);
        this.monitor.setNameTrader(nameTextProperty);

    }
}
