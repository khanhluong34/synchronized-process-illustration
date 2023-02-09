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
        while (true) {
            // TODO: Request to buy stocks
            TradeRequest request = TradeRequest.createBuyRequest(this.getName(), buyQuantity);
            stockMonitor.buy(request);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
