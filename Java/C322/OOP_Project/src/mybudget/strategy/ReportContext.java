package mybudget.strategy;

import mybudget.model.Account;

/**
 * STRATEGY PATTERN - Context
 * Holds a reference to the current ReportFormatter strategy and delegates to it.
 * The strategy can be swapped at runtime (e.g., user selects PDF, CSV, or Chart).
 */
public class ReportContext {
    private ReportFormatter formatter;

    public ReportContext(ReportFormatter formatter) {
        this.formatter = formatter;
    }

    public void setFormatter(ReportFormatter formatter) {
        this.formatter = formatter;
        System.out.println("[Strategy] Report format switched to: " + formatter.getClass().getSimpleName());
    }

    public void generateReport(Account account) {
        formatter.generateReport(account);
    }
}
