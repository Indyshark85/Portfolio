package mybudget.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private double balance;
    private double budgetLimit;
    private List<Transaction> transactions;

    public Account(String name, double initialBalance, double budgetLimit) {
        this.name = name;
        this.balance = initialBalance;
        this.budgetLimit = budgetLimit;
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("DEPOSIT", amount));
    }

    public void withdraw(double amount) {
        balance -= amount;
        transactions.add(new Transaction("WITHDRAWAL", amount));
    }

    public void undoLastTransaction() {
        if (!transactions.isEmpty()) {
            Transaction last = transactions.remove(transactions.size() - 1);
            if (last.getType().equals("DEPOSIT")) {
                balance -= last.getAmount();
            } else {
                balance += last.getAmount();
            }
        }
    }

    public String getName()            { return name; }
    public double getBalance()         { return balance; }
    public double getBudgetLimit()     { return budgetLimit; }
    public void setBudgetLimit(double l) { this.budgetLimit = l; }
    public List<Transaction> getTransactions() { return transactions; }

    @Override
    public String toString() {
        return String.format("Account[%s | Balance: $%.2f | Limit: $%.2f]", name, balance, budgetLimit);
    }
}
