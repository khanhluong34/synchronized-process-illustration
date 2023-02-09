package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.BuyRequest;
import model.TradeRequest;

import java.net.URL;
import java.util.ResourceBundle;

public class TransTableController implements Initializable {

    @FXML
    private TableColumn<TradeRequest, String> colClient;

    @FXML
    private TableColumn<TradeRequest, Integer> colIdx;

    @FXML
    private TableColumn<TradeRequest, Integer> colQty;

    @FXML
    private TableColumn<TradeRequest, String> colType;

    @FXML
    private TableView<TradeRequest> tableTrans;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colClient.setCellValueFactory(new PropertyValueFactory<TradeRequest, String>("nameTrader"));
        colQty.setCellValueFactory(new PropertyValueFactory<TradeRequest, Integer>("quantity"));

        // colType is 'Buy' if tradeRequest is an instance of BuyRequest, 'Sell' otherwise
        colType.setCellValueFactory(cellData -> {
            StringProperty property = new SimpleStringProperty();
            if (cellData.getValue() instanceof BuyRequest) {
                property.setValue("Buy");
            } else {
                property.setValue("Sell");
            }
            return property;
        });
    }
}
