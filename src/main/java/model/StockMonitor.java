package model;

import javafx.beans.property.StringProperty;
import javafx.scene.text.Text;

public class StockMonitor {
    private String name;
    private int quantity;
    private double price;
    private StringProperty nameTrader;

    public int getQuantity() {
        return quantity;
    }

    public StockMonitor(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public void setContentNameTrader(String nameTrader) {
        this.nameTrader.set(nameTrader);
    }
    public void setNameTrader(StringProperty textProperty) {
        this.nameTrader = textProperty;
    }
    public synchronized void buy(int quantity, String nameTrader) {
        this.setContentNameTrader(nameTrader);
        if (this.quantity < quantity) {
            System.out.println("Not enough stocks available for purchase, " + nameTrader + " can not execute this transaction");
        } else {
            try {
                Thread.sleep(4000);
                System.out.println("Transaction time ...");
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
            this.quantity -= quantity;
            System.out.println(Thread.currentThread().getName() + " bought " + quantity + " stocks successfully");
        }
    }

    public synchronized void sell(int quantity, String nameTrader) {
        this.setContentNameTrader(nameTrader);
        try {
            Thread.sleep(4000);
            System.out.println("Transaction time ...");
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }
        this.quantity += quantity;
        System.out.println(Thread.currentThread().getName() + " sold " + quantity + " stocks successfully");
    }
}
