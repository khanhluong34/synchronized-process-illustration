class SellerThread extends Thread {
    private Stock stock;
    private int quantity;

    public SellerThread(String name, Stock stock, int quantity) {
        super(name);
        this.stock = stock;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        stock.sell(quantity);
    }
}