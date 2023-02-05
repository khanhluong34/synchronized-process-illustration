class BuyerThread extends Thread {
    private Stock stock;
    private int quantity;

    public BuyerThread(String name, Stock stock, int quantity) {
        super(name);
        this.stock = stock;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        stock.buy(quantity);
    }
}
