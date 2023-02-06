package model;

public class BuyerThread extends Thread {
    private StockMonitor stockMonitor;
    private int quantity;

    public BuyerThread(String name, StockMonitor stockMonitor, int quantity) {
        super(name);
        this.stockMonitor = stockMonitor;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        stockMonitor.buy(quantity);
    }
}
