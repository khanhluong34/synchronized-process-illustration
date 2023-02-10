package model;

public class BuyerThread extends Thread {
    private StockMonitor stockMonitor;
    private int buyQuantity;

    public BuyerThread(String name, StockMonitor stockMonitor, int buyQuantity) {
        super(name);
        this.stockMonitor = stockMonitor;
        this.buyQuantity = buyQuantity;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            // TODO: Request to buy stocks
            TradeRequest request = TradeRequest.createBuyRequest(this.getName(), buyQuantity);
            stockMonitor.buy(request);
        }
    }
}
