package mybudget.command;

import mybudget.model.Account;
import mybudget.observer.BudgetEventManager;

/**
 * COMMAND PATTERN
 * Encapsulates a deposit action — fully undoable.
 */
public class DepositCommand implements Command {
    private Account account;
    private double amount;
    private BudgetEventManager eventManager;

    public DepositCommand(Account account, double amount, BudgetEventManager eventManager) {
        this.account = account;
        this.amount = amount;
        this.eventManager = eventManager;
    }

    @Override
    public void execute() {
        account.deposit(amount);
        System.out.printf("[Command] Deposited $%.2f into %s. New balance: $%.2f%n",
            amount, account.getName(), account.getBalance());
        // Notify observers after state change
        eventManager.notifyObservers(account);
    }

    @Override
    public void undo() {
        account.undoLastTransaction();
        System.out.printf("[Command] Undo deposit of $%.2f from %s. Balance: $%.2f%n",
            amount, account.getName(), account.getBalance());
    }

    @Override
    public String getDescription() {
        return String.format("Deposit $%.2f to %s", amount, account.getName());
    }
}
