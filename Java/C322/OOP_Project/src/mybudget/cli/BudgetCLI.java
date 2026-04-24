package mybudget.cli;

import mybudget.command.*;
import mybudget.model.Account;
import mybudget.observer.*;
import mybudget.singleton.AppConfig;
import mybudget.singleton.UserSession;
import mybudget.strategy.*;

import java.util.Scanner;

/**
 * Interactive CLI for MyBudget.
 * Provides a menu-driven interface so users can manage accounts,
 * run commands (deposit/withdraw/set limit), undo/redo, and generate reports.
 */
public class BudgetCLI {

    private final Scanner scanner = new Scanner(System.in);
    private final UserSession session = UserSession.getInstance();
    private final AppConfig config = AppConfig.getInstance();
    private final BudgetEventManager eventManager = new BudgetEventManager();
    private final CommandHistory history = new CommandHistory();
    private final NotificationService notifService = new NotificationService();

    public void start() {
        setupObservers();
        printBanner();
        loginFlow();

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = prompt("> ").trim();
            switch (choice) {
                case "1" -> manageAccounts();
                case "2" -> transactionMenu();
                case "3" -> undoRedoMenu();
                case "4" -> generateReportMenu();
                case "5" -> viewNotifications();
                case "6" -> configMenu();
                case "0" -> running = false;
                default  -> System.out.println("  Invalid option. Please try again.");
            }
        }

        notifService.printLog();
        session.logout();
        System.out.println("\nThank you for using MyBudget. Goodbye!");
    }

    // ────────────────────────────────────────────────────────
    // Setup
    // ────────────────────────────────────────────────────────

    private void setupObservers() {
        eventManager.subscribe(new BudgetAlertObserver("UI Alert"));
        eventManager.subscribe(notifService);
    }

    private void loginFlow() {
        System.out.println("Enter your username (or press Enter for 'guest', type 'demo' for sample data):");
        String username = prompt("> ").trim();
        if (username.isEmpty()) username = "guest";
        session.login(username);
        System.out.println();

        if (username.equalsIgnoreCase("demo")) {
            loadDemoData();
        }
    }

    /**
     * Seeds the demo user with realistic accounts and transaction history
     * so they can immediately explore every feature of the app.
     */
    private void loadDemoData() {
        System.out.println("  ┌─────────────────────────────────────────┐");
        System.out.println("  │  Demo mode — sample data loaded!        │");
        System.out.println("  │  Explore the menus, undo transactions,  │");
        System.out.println("  │  and generate PDF / CSV / Chart reports.│");
        System.out.println("  └─────────────────────────────────────────┘");
        System.out.println();

        // Create accounts
        Account checking = new Account("Checking", 3200.00, 5000.00);
        Account savings  = new Account("Savings",  8500.00, 10000.00);
        Account vacation = new Account("Vacation Fund", 420.00, 2000.00);
        session.addAccount(checking);
        session.addAccount(savings);
        session.addAccount(vacation);

        // Seed Checking with a realistic month of transactions (silent — no console noise)
        silentExecute(new DepositCommand(checking,  2500.00, eventManager));  // paycheck
        silentExecute(new WithdrawCommand(checking,  850.00, eventManager));  // rent
        silentExecute(new WithdrawCommand(checking,  120.00, eventManager));  // groceries
        silentExecute(new WithdrawCommand(checking,   65.00, eventManager));  // utilities
        silentExecute(new DepositCommand(checking,  2500.00, eventManager));  // paycheck
        silentExecute(new WithdrawCommand(checking,  200.00, eventManager));  // dining
        silentExecute(new WithdrawCommand(checking,   45.00, eventManager));  // subscriptions
        silentExecute(new WithdrawCommand(checking, 4200.00, eventManager));  // large purchase — triggers alert

        // Seed Savings
        silentExecute(new DepositCommand(savings,   1000.00, eventManager));
        silentExecute(new DepositCommand(savings,    500.00, eventManager));
        silentExecute(new WithdrawCommand(savings,   250.00, eventManager));

        // Seed Vacation Fund — nearly empty, will trigger alert
        silentExecute(new DepositCommand(vacation,   300.00, eventManager));
        silentExecute(new WithdrawCommand(vacation,  650.00, eventManager));  // triggers warning

        System.out.println("  Accounts ready: Checking, Savings, Vacation Fund");
        System.out.println("  Transaction history seeded. Notifications may have fired above.\n");
    }

    /** Execute a command and record it in history without printing the normal output line. */
    private void silentExecute(Command cmd) {
        // Redirect stdout briefly so demo seeding doesn't flood the screen
        java.io.PrintStream original = System.out;
        System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
            public void write(int b) {}
        }));
        try {
            history.executeCommand(cmd);
        } finally {
            System.setOut(original);
        }
    }

    // ────────────────────────────────────────────────────────
    // Menus
    // ────────────────────────────────────────────────────────

    private void printBanner() {
        System.out.println();
        System.out.println("════════════════════════════════════════");
        System.out.println("   MyBudget Personal Finance Manager    ");
        System.out.println("════════════════════════════════════════");
        System.out.println();
    }

    private void printMainMenu() {
        System.out.println("\n╔═══ MAIN MENU ═══════════════════════╗");
        System.out.println("║  1. Manage Accounts                 ║");
        System.out.println("║  2. Transactions (Deposit/Withdraw) ║");
        System.out.println("║  3. Undo / Redo                     ║");
        System.out.println("║  4. Generate Report                 ║");
        System.out.println("║  5. View Notifications              ║");
        System.out.println("║  6. Settings                        ║");
        System.out.println("║  0. Exit                            ║");
        System.out.println("╚═════════════════════════════════════╝");
    }

    // ── Accounts ──────────────────────────────────────────

    private void manageAccounts() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Accounts ---");
            if (session.getAccounts().isEmpty()) {
                System.out.println("  (no accounts yet)");
            } else {
                session.getAccounts().forEach(a -> System.out.println("  • " + a));
            }
            System.out.println("\n  1. Create new account");
            System.out.println("  2. List accounts");
            System.out.println("  0. Back");
            String choice = prompt("  > ").trim();
            switch (choice) {
                case "1" -> createAccount();
                case "2" -> session.getAccounts().forEach(a -> System.out.println("  " + a));
                case "0" -> back = true;
                default  -> System.out.println("  Invalid option.");
            }
        }
    }

    private void createAccount() {
        String name = prompt("  Account name: ").trim();
        if (name.isEmpty()) { System.out.println("  Name cannot be empty."); return; }
        if (session.getAccount(name) != null) { System.out.println("  Account already exists."); return; }

        double balance = readDouble("  Initial balance ($): ");
        double limit   = readDouble("  Budget limit ($): ");

        Account account = new Account(name, balance, limit);
        session.addAccount(account);
        System.out.println("  ✓ Account '" + name + "' created.");
    }

    // ── Transactions ──────────────────────────────────────

    private void transactionMenu() {
        if (session.getAccounts().isEmpty()) {
            System.out.println("  No accounts available. Please create one first.");
            return;
        }
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Transactions ---");
            System.out.println("  1. Deposit");
            System.out.println("  2. Withdraw");
            System.out.println("  3. Set Budget Limit");
            System.out.println("  0. Back");
            String choice = prompt("  > ").trim();
            switch (choice) {
                case "1" -> doDeposit();
                case "2" -> doWithdraw();
                case "3" -> doSetBudgetLimit();
                case "0" -> back = true;
                default  -> System.out.println("  Invalid option.");
            }
        }
    }

    private void doDeposit() {
        Account account = selectAccount();
        if (account == null) return;
        double amount = readDouble("  Amount to deposit ($): ");
        if (amount <= 0) { System.out.println("  Amount must be positive."); return; }
        history.executeCommand(new DepositCommand(account, amount, eventManager));
    }

    private void doWithdraw() {
        Account account = selectAccount();
        if (account == null) return;
        double amount = readDouble("  Amount to withdraw ($): ");
        if (amount <= 0) { System.out.println("  Amount must be positive."); return; }
        history.executeCommand(new WithdrawCommand(account, amount, eventManager));
    }

    private void doSetBudgetLimit() {
        Account account = selectAccount();
        if (account == null) return;
        System.out.printf("  Current limit: $%.2f%n", account.getBudgetLimit());
        double newLimit = readDouble("  New budget limit ($): ");
        if (newLimit <= 0) { System.out.println("  Limit must be positive."); return; }
        history.executeCommand(new SetBudgetLimitCommand(account, newLimit));
    }

    // ── Undo / Redo ───────────────────────────────────────

    private void undoRedoMenu() {
        System.out.println("\n--- Undo / Redo ---");
        System.out.println("  1. Undo last action");
        System.out.println("  2. Redo last undone action");
        System.out.println("  3. View command history");
        System.out.println("  0. Back");
        String choice = prompt("  > ").trim();
        switch (choice) {
            case "1" -> history.undo();
            case "2" -> history.redo();
            case "3" -> history.printHistory();
            case "0" -> {}
            default  -> System.out.println("  Invalid option.");
        }
    }

    // ── Reports ───────────────────────────────────────────

    private void generateReportMenu() {
        if (session.getAccounts().isEmpty()) {
            System.out.println("  No accounts available.");
            return;
        }
        System.out.println("\n--- Generate Report ---");
        System.out.println("  Format:");
        System.out.println("  1. PDF  (saved to reports/<name>_report.pdf)");
        System.out.println("  2. CSV  (saved to reports/<name>_report.csv)");
        System.out.println("  3. HTML Chart (saved to reports/<name>_chart.html)");
        System.out.println("  0. Back");
        String choice = prompt("  > ").trim();
        if (choice.equals("0")) return;

        Account account = selectAccount();
        if (account == null) return;

        ReportFormatter fmt = switch (choice) {
            case "1" -> new PDFStrategy();
            case "2" -> new CSVStrategy();
            case "3" -> new ChartStrategy();
            default  -> { System.out.println("  Invalid format."); yield null; }
        };
        if (fmt != null) {
            ReportContext ctx = new ReportContext(fmt);
            ctx.generateReport(account);
        }
    }

    // ── Notifications ─────────────────────────────────────

    private void viewNotifications() {
        notifService.printLog();
    }

    // ── Config ────────────────────────────────────────────

    private void configMenu() {
        System.out.println("\n--- Settings ---");
        System.out.printf("  Alerts enabled : %b%n", config.isAlertsEnabled());
        System.out.printf("  Currency       : %s%n", config.getCurrency());
        System.out.printf("  Default limit  : $%.2f%n", config.getDefaultBudgetLimit());
        System.out.println("\n  1. Toggle alerts");
        System.out.println("  2. Set currency");
        System.out.println("  0. Back");
        String choice = prompt("  > ").trim();
        switch (choice) {
            case "1" -> {
                config.setAlertsEnabled(!config.isAlertsEnabled());
                System.out.println("  Alerts are now " + (config.isAlertsEnabled() ? "ENABLED" : "DISABLED") + ".");
            }
            case "2" -> {
                String cur = prompt("  Enter currency code (e.g. USD, EUR): ").trim().toUpperCase();
                if (!cur.isEmpty()) { config.setCurrency(cur); System.out.println("  Currency set to " + cur); }
            }
            case "0" -> {}
            default  -> System.out.println("  Invalid option.");
        }
    }

    // ────────────────────────────────────────────────────────
    // Helpers
    // ────────────────────────────────────────────────────────

    private Account selectAccount() {
        var accounts = session.getAccounts();
        if (accounts.isEmpty()) { System.out.println("  No accounts available."); return null; }
        System.out.println("  Select account:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("    %d. %s%n", i + 1, accounts.get(i).getName());
        }
        String input = prompt("  > ").trim();
        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < accounts.size()) return accounts.get(idx);
        } catch (NumberFormatException ignored) {}
        System.out.println("  Invalid selection.");
        return null;
    }

    private double readDouble(String promptText) {
        while (true) {
            String input = prompt(promptText).trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }

    private String prompt(String text) {
        System.out.print(text);
        return scanner.nextLine();
    }
}
