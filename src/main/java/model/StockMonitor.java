package model;

import java.util.LinkedList;

public class StockMonitor {
    private int quantity;
    private int maxQuantity = 100;
    private int lock = 1;
    private TradeRequest handlingRequest;

    private LinkedList<TradeRequest> queuedRequest;
    private LinkedList<String> nameQueuedRequest;

    public int getQuantity() {
        return quantity;
    }

    public TradeRequest getHandlingRequest() {
        return handlingRequest;
    }

    public StockMonitor(int quantity) {
        this.quantity = quantity;
        this.queuedRequest = new LinkedList<TradeRequest>();
        this.nameQueuedRequest = new LinkedList<String>();
    }
    public String currentRequest() {
        String requests = "";
        for(int i = 0; i < queuedRequest.size(); i++) {
            requests += " " + queuedRequest.get(i).getNameTrader();
        }
        return requests;
    }
    public synchronized void buy(TradeRequest request) {
        handlingRequest = request;
        if (this.quantity < request.getQuantity()) {
            System.out.println("Not enough stocks available for purchase, " + request.getNameTrader() + " can not execute this transaction");
            try {
                // wait until enough stock quantity for purchase
                if (! this.nameQueuedRequest.contains(request.getNameTrader())) {
                    this.queuedRequest.add(request);
                    this.nameQueuedRequest.add(request.getNameTrader());
                }
                wait();
            } catch (InterruptedException e) {
                System.out.print(e.getMessage());
            }
        } else {
            if (this.nameQueuedRequest.contains(request.getNameTrader())) {
                this.queuedRequest.remove(this.nameQueuedRequest.indexOf(request.getNameTrader()));
                this.nameQueuedRequest.remove(request.getNameTrader());
            }
            if (this.queuedRequest.size() > 0) {
                System.out.println("Waiting set: " + currentRequest());
            }
            try {
                Thread.sleep(2000);
                System.out.println("Transaction time ...");
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
            this.quantity -= request.getQuantity();
            System.out.println(Thread.currentThread().getName() + " bought " + request.getQuantity() + " stocks successfully");
            // notifies anyone of threads waiting in the wait set to wake up arbitrarily.
            notify();
            }
    }

    public synchronized void sell(TradeRequest request) {
        this.handlingRequest = request;
        if (this.maxQuantity - this.quantity < request.getQuantity()) {
            System.out.println("System holds enough stock quantity, preventing selling stock to protect the stock price, stop " + Thread.currentThread().getName());
            try {
                if (! this.nameQueuedRequest.contains(request.getNameTrader())) {
                    this.nameQueuedRequest.add(request.getNameTrader());
                    this.queuedRequest.add(request);
                }
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        } else {
            if (this.nameQueuedRequest.contains(request.getNameTrader())) {
                this.queuedRequest.remove(this.nameQueuedRequest.indexOf(request.getNameTrader()));
                this.nameQueuedRequest.remove(request.getNameTrader());
            }
            if (this.queuedRequest.size() > 0) {
                System.out.println("Waiting set: " + currentRequest());
            }
            try {
                Thread.sleep(2000);
                System.out.println("Transaction time ...");
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
            this.quantity += request.getQuantity();
            System.out.println(Thread.currentThread().getName() + " sold " + request.getQuantity() + " stocks successfully");
            // notifies anyone of threads waiting in the wait set to wake up arbitrarily.
            notify();
        }
    }
}
