import model.*;

public class Main {
    public static void main(String[] args) {
        StockMonitor stockMonitor = new StockMonitor("ABC", 100, 10.0);
        while (true) {
            BuyerThread client1 = new BuyerThread("Buyer 1", stockMonitor, 50);
            BuyerThread client2 = new BuyerThread("Buyer 2", stockMonitor, 75);
            SellerThread seller1 = new SellerThread("Seller 1", stockMonitor, 20);
            SellerThread seller2 = new SellerThread("Seller 2", stockMonitor, 100);
            client1.start();
            client2.start();
            seller1.start();
            seller2.start();
        }
    }
}