package mybudget.observer;

import mybudget.model.Account;
import mybudget.singleton.AppConfig;
import java.util.ArrayList;
import java.util.List;

/**
 * OBSERVER PATTERN - Subject / Event Publisher
 * Tracks registered observers and fires events when account state changes.
 * Called from within Command.execute() so patterns interlock correctly.
 */
public class BudgetEventManager {
    private List<BudgetObserver> observers = new ArrayList<>();

    public void subscribe(BudgetObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(BudgetObserver observer) {
        observers.remove(observer);
    }

    /**
     * Called after every Command execution.
     * Checks thresholds and notifies all registered observers.
     */
    public void notifyObservers(Account account) {
        if (!AppConfig.getInstance().isAlertsEnabled()) return;

        double balance = account.getBalance();
        double limit   = account.getBudgetLimit();

        String message = null;

        if (balance < 0) {
            message = "⚠️  ALERT: Account \"" + account.getName() + "\" is OVERDRAWN! Balance: $" + String.format("%.2f", balance);
        } else if (balance < limit * 0.10) {
            message = "⚠️  ALERT: Account \"" + account.getName() + "\" is below 10% of budget limit! Balance: $" + String.format("%.2f", balance);
        } else if (balance < limit * 0.25) {
            message = "⚠️  WARNING: Account \"" + account.getName() + "\" is below 25% of budget limit. Balance: $" + String.format("%.2f", balance);
        }

        if (message != null) {
            for (BudgetObserver observer : observers) {
                observer.onBudgetEvent(account, message);
            }
        }
    }
}
