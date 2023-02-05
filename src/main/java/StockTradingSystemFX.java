import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StockTradingSystemFX extends Application {
    private int price;
    private Lock lock;
    private Condition condition;

    public StockTradingSystemFX() {
        price = 100;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void buy() {
        lock.lock();
        try {
            while (price <= 0) {
                System.out.println("Buyer waiting as price is not set.");
                condition.await();
            }
            price--;
            System.out.println("Price decreased to " + price + " after buy operation.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void sell() {
        lock.lock();
        try {
            price++;
            System.out.println("Price increased to " + price + " after sell operation.");
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void start(Stage stage) {
        Label priceLabel = new Label("Price");
        TextField priceTextField = new TextField();
        priceTextField.setText(String.valueOf(price));
        priceTextField.setEditable(false);

        Button buyButton = new Button("Buy");
        buyButton.setOnAction(event -> buy());

        Button sellButton = new Button("Sell");
        sellButton.setOnAction(event -> sell());

        VBox root = new VBox();
        root.getChildren().addAll(priceLabel, priceTextField, buyButton, sellButton);

        Scene scene = new Scene(root, 200, 200);
        stage.setScene(scene);
        stage.setTitle("Stock Trading System");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
