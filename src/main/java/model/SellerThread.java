package model;

public class SellerThread extends Thread {
    private StockMonitor stockMonitor;
    private int quantity;

    public SellerThread(String name, StockMonitor stockMonitor, int quantity) {
        super(name);
        this.stockMonitor = stockMonitor;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        stockMonitor.sell(quantity);
    }
}