package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StockMonitor {
    private MonitorStatusType status = MonitorStatusType.NORMAL;
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
    public MonitorStatusType getStatus() {return this.status;}
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
        // if the quantity of stock is not enough, then add the request into queue
        if (this.quantity < request.getQuantity()) {
            this.status = MonitorStatusType.NOT_ENOUGH;
            try {
                if (! this.queuedRequest.contains(request)) {
                    request.setIndex(queuedRequest.size() + 1);
                    this.queuedRequest.add(request);
                }
                // the thread is waiting for the notification from other thread
                wait();
            } catch (InterruptedException e) {
                System.out.print(e.getMessage());
            }
        } else {
            // if the quantity of stock is enough, then remove the buy request from queue and reindexing the remains
            this.queuedRequest.remove(request);
            this.reindexing();
            // simulate the latency of the transaction
            try {
                Thread.sleep(latency);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // the transaction is success, then change the quantity of stock
            this.quantity -= request.getQuantity();
            // the transaction is success, add the request into transaction history
            request.setIndex(transactionHistory.size() + 1);
            this.transactionHistory.add(request);
            // the transaction is success, then change the content of status
            this.status = MonitorStatusType.NORMAL;
            // notify the other thread to continue
            notify();
        }
    }

    public synchronized void sell(TradeRequest request) {
        this.handlingRequest = request;
        // if the quantity of stock is full, then add the request into queue
        if (this.maxQuantity - this.quantity < request.getQuantity()) {
            this.status = MonitorStatusType.FULL;
            try {
                if (! this.queuedRequest.contains(request)) {
                    request.setIndex(this.queuedRequest.size() + 1);
                    this.queuedRequest.add(request);
                }
                // the thread is waiting for the notification from other thread
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        } else {
            // if the quantity of stock is not full, then remove the sell request from queue and reindexing the remains
            this.queuedRequest.remove(request);
            this.reindexing();
            // simulate the latency of the transaction
            try {
                Thread.sleep(latency);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            this.quantity += request.getQuantity();
            // the transaction is success, add the request into the transaction history
            request.setIndex(transactionHistory.size() + 1);
            this.transactionHistory.add(request);
            // the transaction is success, then change the content of status
            this.status = MonitorStatusType.NORMAL;
            // notify the other thread to continue
            notify();
        }
    }
}
