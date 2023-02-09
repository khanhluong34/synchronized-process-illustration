package model;

public class StockMonitor {
    private int quantity;
    private TradeRequest handlingRequest;

    private TradeRequest[] queuedRequest;

    public int getQuantity() {
        return quantity;
    }

    public TradeRequest getHandlingRequest() {
        return handlingRequest;
    }

    public StockMonitor(int quantity) {
        this.quantity = quantity;
    }

    public synchronized void buy(TradeRequest request) {
//        handlingRequest = request;
//        if (this.quantity < request.getQuantity()) {
//            System.out.println("Not enough stocks available for purchase, " + request.getNameTrader() + " can not execute this transaction");
//        } else {
//            try {
//                Thread.sleep(2000);
//                System.out.println("Transaction time ...");
//            } catch (InterruptedException e) {
//                System.out.println("Thread interrupted: " + e.getMessage());
//            }
//            this.quantity -= request.getQuantity();
//            System.out.println(Thread.currentThread().getName() + " bought " + request.getQuantity() + " stocks successfully");
//        }
        if (this.quantity < request.getQuantity()) {
            {
                try { wait(); }
                catch (InterruptedException ignored) { }
            }

        }
    }

    public synchronized void sell(TradeRequest request) {
        handlingRequest = request;
        try {
            Thread.sleep(2000);
            System.out.println("Transaction time ...");
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }
        this.quantity += request.getQuantity();
        System.out.println(Thread.currentThread().getName() + " sold " + request.getQuantity() + " stocks successfully");
    }
}
