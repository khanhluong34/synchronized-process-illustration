package model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

public class StockMonitor {
    private String notification = "System works normally";
    private int quantity;
    private int maxQuantity;
    private int latency;
    private TradeRequest handlingRequest = null;
    private ObservableList<TradeRequest> queuedRequest;
    private ObservableList<TradeRequest> transactionHistory;

    public int getQuantity() {
        return quantity;
    }

    public TradeRequest getHandlingRequest() {
        return handlingRequest;
    }

    public ObservableList<TradeRequest> getQueuedRequest() {
        return queuedRequest;
    }
    public ObservableList<TradeRequest> getTransactionHistory() {return transactionHistory;};

    public StockMonitor(int quantity, int maxQuantity, int latency) {
        this.quantity = quantity;
        this.maxQuantity = maxQuantity;
        this.latency = latency;
        this.queuedRequest = FXCollections.observableArrayList();
        this.transactionHistory = FXCollections.observableArrayList();
    }
    public String getNotification() {return this.notification;}
    public String currentRequest() {
        StringBuilder requests = new StringBuilder();
        for (TradeRequest tradeRequest : queuedRequest) {
            requests.append(" ").append(tradeRequest.getNameTrader());
        }
        return requests.toString();
    }
    // when remove a request from queue, we need to reindex the remains.
    public void reindexing() {
        for (int i = 0; i < this.queuedRequest.size(); i ++) {
            this.queuedRequest.get(i).setIndex(i + 1);
        }
    }
    public synchronized void buy(TradeRequest request) {
        handlingRequest = request;
        if (this.quantity < request.getQuantity()) {
            // set the notification
            this.notification = "Not enough stocks available for purchase";
            System.out.println("Not enough stocks available for purchase, " + request.getNameTrader() + " can not execute this transaction");
            try {
                // wait until enough stock quantity for purchase
                if (! this.queuedRequest.contains(request)) {
                    request.setIndex(queuedRequest.size() + 1);
                    this.queuedRequest.add(request);
                }
                wait();
            } catch (InterruptedException e) {
                System.out.print(e.getMessage());
            }
        } else {
            this.queuedRequest.remove(request);
            this.reindexing();
            if (this.queuedRequest.size() > 0) {
                System.out.println("Waiting set: " + currentRequest());
            }
            try {
                Thread.sleep(latency);
                System.out.println("Transaction time ...");
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
            this.quantity -= request.getQuantity();
            // the transaction is success, add the request into transaction history
            request.setIndex(transactionHistory.size() + 1);
            this.transactionHistory.add(request);
            // the transaction is success, then change the content of notification
            this.notification = "System works normally";
            System.out.println(Thread.currentThread().getName() + " bought " + request.getQuantity() + " stocks successfully");
            // notifies anyone of threads waiting in the wait set to wake up arbitrarily.
            notify();
        }
    }

    public synchronized void sell(TradeRequest request) {
        this.handlingRequest = request;
        if (this.maxQuantity - this.quantity < request.getQuantity()) {
            // set the notification
            this.notification = "System holds enough stock, preventing selling";
            System.out.println("System holds enough stock quantity, preventing selling stock to protect the stock price, stop " + Thread.currentThread().getName());
            try {
                if (! this.queuedRequest.contains(request)) {
                    request.setIndex(this.queuedRequest.size() + 1);
                    this.queuedRequest.add(request);
                }
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        } else {
            this.queuedRequest.remove(request);
            this.reindexing();
            if (this.queuedRequest.size() > 0) {
                System.out.println("Waiting set: " + currentRequest());
            }
            try {
                Thread.sleep(latency);
                System.out.println("Transaction time ...");
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
            this.quantity += request.getQuantity();
            // the transaction is success, add the request into the transaction history
            request.setIndex(transactionHistory.size() + 1);
            this.transactionHistory.add(request);
            // the transaction is success, then change the content of notification
            this.notification = "System works normally";
            System.out.println(Thread.currentThread().getName() + " sold " + request.getQuantity() + " stocks successfully");
            // notifies anyone of threads waiting in the wait set to wake up arbitrarily.
            notify();
        }
    }
}
