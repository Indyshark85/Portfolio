package mybudget.observer;

import mybudget.model.Account;

/**
 * OBSERVER PATTERN - Observer interface
 * Any class that wants budget alerts must implement this.
 */
public interface BudgetObserver {
    void onBudgetEvent(Account account, String message);
}
