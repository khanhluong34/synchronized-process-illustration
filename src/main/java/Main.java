public class Main {
    public static void main(String[] args) {
        Stock stock = new Stock("ABC", 100, 10.0);
        while (true) {
            BuyerThread client1 = new BuyerThread("Buyer 1", stock, 50);
            BuyerThread client2 = new BuyerThread("Buyer 2", stock, 75);
            SellerThread seller1 = new SellerThread("Seller 1", stock, 20);
            SellerThread seller2 = new SellerThread("Seller 2", stock, 100);
            client1.start();
            client2.start();
            seller1.start();
            seller2.start();
        }
    }
}