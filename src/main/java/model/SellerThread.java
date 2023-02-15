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
        while (!Thread.interrupted()) {
            TradeRequest request = TradeRequest.createSellRequest(this.getName(), sellQuantity);
            stockMonitor.sell(request);
        }
    }
}