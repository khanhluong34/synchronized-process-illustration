import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.BuyerThread;
import model.SellerThread;
import model.StockMonitor;

public class MonitorAppController {

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
    void btnAboutOnPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("This is an example of the case of Process synchronization using Monitor ");
        alert.setContentText("This display the case of Stock Trading ");
        alert.showAndWait();
    }

    @FXML
    void btnStartOnPressed(ActionEvent event) {

    }

}
