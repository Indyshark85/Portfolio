package mybudget.command;

import mybudget.model.Account;

/**
 * COMMAND PATTERN
 * Allows setting (and undoing) a budget limit change.
 */
public class SetBudgetLimitCommand implements Command {
    private Account account;
    private double newLimit;
    private double previousLimit;

    public SetBudgetLimitCommand(Account account, double newLimit) {
        this.account = account;
        this.newLimit = newLimit;
        this.previousLimit = account.getBudgetLimit();
    }

    @Override
    public void execute() {
        account.setBudgetLimit(newLimit);
        System.out.printf("[Command] Budget limit for %s set to $%.2f%n",
            account.getName(), newLimit);
    }

    @Override
    public void undo() {
        account.setBudgetLimit(previousLimit);
        System.out.printf("[Command] Undo: Budget limit for %s restored to $%.2f%n",
            account.getName(), previousLimit);
    }

    @Override
    public String getDescription() {
        return String.format("Set budget limit of %s to $%.2f", account.getName(), newLimit);
    }
}
