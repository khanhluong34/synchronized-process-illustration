package model;

import java.util.Objects;

public class TradeRequest {
    private final String nameTrader;
    private final int quantity;
    private final TradeRequestType type;

    private TradeRequest(String nameTrader, int quantity, TradeRequestType type) {
        this.nameTrader = nameTrader;
        this.quantity = quantity;
        this.type = type;
    }
    @Override
    public boolean equals(Object o) {
        if (! (o instanceof TradeRequest request)) {
            return false;
        } else {
            return Objects.equals(this.getNameTrader(), request.getNameTrader());
        }
    }

    public static TradeRequest createBuyRequest(String nameTrader, int quantity) {
        return new TradeRequest(nameTrader, quantity, TradeRequestType.BUY);
    }

    public static TradeRequest createSellRequest(String nameTrader, int quantity) {
        return new TradeRequest(nameTrader, quantity, TradeRequestType.SELL);
    }

    public String getNameTrader() {
        return nameTrader;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getType() {
        return type.name();
    }
}
