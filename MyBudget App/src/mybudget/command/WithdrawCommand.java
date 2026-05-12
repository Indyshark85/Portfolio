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
        boolean ok = account.withdraw(amount); //added as guard for excessive withdraw
        if (!ok) {
            System.out.printf("[Command] Withdrawal of $%.2f from %s REJECTED — insufficient funds.%n",
                    amount, account.getName());
            return;
        }
        // Notify observers after state change
        eventManager.notifyObservers(account);
    }

    @Override
    public void undo() {
        account.undoLastTransaction(); //added for user protection
        System.out.printf("[Command] Undo withdrawal of $%.2f from %s. Balance: $%.2f%n",
                amount, account.getName(), account.getBalance());
        eventManager.notifyObservers(account);
    }

    @Override
    public String getDescription() {
        return String.format("Withdraw $%.2f from %s", amount, account.getName());
    }
}
