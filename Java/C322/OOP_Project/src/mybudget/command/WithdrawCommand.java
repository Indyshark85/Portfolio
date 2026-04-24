package mybudget.command;

import mybudget.model.Account;
import mybudget.observer.BudgetEventManager;

/**
 * COMMAND PATTERN
 * Encapsulates a withdrawal action — fully undoable.
 */
public class WithdrawCommand implements Command {
    private Account account;
    private double amount;
    private BudgetEventManager eventManager;

    public WithdrawCommand(Account account, double amount, BudgetEventManager eventManager) {
        this.account = account;
        this.amount = amount;
        this.eventManager = eventManager;
    }

    @Override
    public void execute() {
        account.withdraw(amount);
        System.out.printf("[Command] Withdrew $%.2f from %s. New balance: $%.2f%n",
            amount, account.getName(), account.getBalance());
        // Notify observers after state change
        eventManager.notifyObservers(account);
    }

    @Override
    public void undo() {
        account.undoLastTransaction();
        System.out.printf("[Command] Undo withdrawal of $%.2f from %s. Balance: $%.2f%n",
            amount, account.getName(), account.getBalance());
    }

    @Override
    public String getDescription() {
        return String.format("Withdraw $%.2f from %s", amount, account.getName());
    }
}
