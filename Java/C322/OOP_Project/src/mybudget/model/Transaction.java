package mybudget.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String type;
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public String getType()   { return type; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return String.format("[%s] %s: $%.2f",
            timestamp.format(DateTimeFormatter.ofPattern("MM-dd HH:mm")),
            type, amount);
    }
}
