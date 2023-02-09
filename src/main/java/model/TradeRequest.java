package model;

public abstract class TradeRequest {
    private final String nameTrader;
    private final int quantity;

    public TradeRequest(String nameTrader, int quantity) {
        this.nameTrader = nameTrader;
        this.quantity = quantity;
    }

    public String getNameTrader() {
        return nameTrader;
    }

    public int getQuantity() {
        return quantity;
    }
}
