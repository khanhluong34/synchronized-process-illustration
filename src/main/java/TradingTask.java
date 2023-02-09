import javafx.application.Platform;
import javafx.concurrent.Task;
import model.BuyerThread;
import model.SellerThread;
import model.StockMonitor;

public class TradingTask extends Task<Void> {
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
                // Update UI
            });
            Thread.sleep(1000);
        }
        return null;
    }
}
