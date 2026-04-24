package mybudget.strategy;

import mybudget.model.Account;

/**
 * STRATEGY PATTERN - Strategy interface
 * Defines a common interface for all report formats.
 */
public interface ReportFormatter {
    void generateReport(Account account);
}
