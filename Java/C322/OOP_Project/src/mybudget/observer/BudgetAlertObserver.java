package mybudget.observer;

import mybudget.model.Account;

/**
 * OBSERVER PATTERN - Concrete Observer
 * Receives and displays budget threshold alerts.
 */
public class BudgetAlertObserver implements BudgetObserver {
    private String observerName;

    public BudgetAlertObserver(String name) {
        this.observerName = name;
    }

    @Override
    public void onBudgetEvent(Account account, String message) {
        System.out.println("[" + observerName + "] " + message);
    }
}
