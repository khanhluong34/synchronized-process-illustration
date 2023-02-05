class Stock {
    private String name;
    private int quantity;
    private double price;

    public Stock(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    public synchronized void buy(int quantity) {
        if (this.quantity < quantity) {
            System.out.println("Not enough stocks available for purchase");
        } else {
            try {
                Thread.sleep(1000);
                System.out.println("Transaction time ...");
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
            this.quantity -= quantity;
            System.out.println(Thread.currentThread().getName() + " bought " + quantity + " stocks successfully");
        }
    }

    public synchronized void sell(int quantity) {
        try {
            Thread.sleep(1000);
            System.out.println("Transaction time ...");
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }
        this.quantity += quantity;
        System.out.println(Thread.currentThread().getName() + " sold " + quantity + " stocks successfully");
    }
}
