package mybudget;

import mybudget.cli.BudgetCLI;
import mybudget.gui.BudgetGUI;
import mybudget.singleton.AppConfig;

/**
 * MyBudget — Application Entry Point
 * Launches the interactive CLI.
 * Log in as "demo" to get pre-seeded accounts and transaction history.
 */
public class Main {
    public static void main(String[] args) {
        AppConfig.getInstance().setDefaultBudgetLimit(2000.0);
        new BudgetGUI().start();
    }
}
