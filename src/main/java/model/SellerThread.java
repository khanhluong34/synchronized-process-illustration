package model;

public class SellerThread extends Thread {
    private StockMonitor stockMonitor;
    private int sellQuantity;

    public SellerThread(String name, StockMonitor stockMonitor, int sellQuantity) {
        super(name);
        this.stockMonitor = stockMonitor;
        this.sellQuantity = sellQuantity;
    }

    @Override
    public void run() {
        while (true) {
            // TODO: Request to sell stocks
            TradeRequest request = TradeRequest.createSellRequest(this.getName(), sellQuantity);
            stockMonitor.sell(request);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}