package mybudget;

import mybudget.command.*;
import mybudget.model.Account;
import mybudget.observer.*;
import mybudget.singleton.AppConfig;
import mybudget.singleton.UserSession;
import mybudget.strategy.*;

/**
 * Original demonstration of all four design patterns.
 * Invoked via: java mybudget.Main --demo
 */
public class MainDemo {
    public static void run() {
        System.out.println("════════════════════════════════════════");
        System.out.println("   MyBudget — Pattern Demo Mode");
        System.out.println("════════════════════════════════════════\n");

        AppConfig config = AppConfig.getInstance();
        System.out.println("[Config] " + config);

        UserSession session = UserSession.getInstance();
        session.login("john_doe");

        Account checking = new Account("Checking", 1500.00, 2000.00);
        Account savings  = new Account("Savings",   500.00, 1000.00);
        session.addAccount(checking);
        session.addAccount(savings);

        System.out.println("\n[Accounts Loaded]");
        session.getAccounts().forEach(System.out::println);

        System.out.println("\n--- Setting up Observers ---");
        BudgetEventManager eventManager = new BudgetEventManager();
        BudgetAlertObserver uiAlert = new BudgetAlertObserver("UI Alert");
        NotificationService notifService = new NotificationService();
        eventManager.subscribe(uiAlert);
        eventManager.subscribe(notifService);

        System.out.println("\n--- Executing Commands ---");
        CommandHistory history = new CommandHistory();
        history.executeCommand(new DepositCommand(checking, 200.00, eventManager));
        history.executeCommand(new WithdrawCommand(checking, 300.00, eventManager));
        history.executeCommand(new WithdrawCommand(checking, 1350.00, eventManager));
        history.executeCommand(new SetBudgetLimitCommand(savings, 800.00));
        history.executeCommand(new DepositCommand(savings, 50.00, eventManager));
        history.executeCommand(new WithdrawCommand(savings, 500.00, eventManager));

        System.out.println("\n--- Undo / Redo Demo ---");
        history.printHistory();
        history.undo();
        history.undo();
        history.redo();

        System.out.println("\n--- Report Generation (Strategy Pattern) ---");
        Account liveChecking = session.getAccount("Checking");
        Account liveSavings  = session.getAccount("Savings");

        ReportContext reportCtx = new ReportContext(new PDFStrategy());
        reportCtx.generateReport(liveChecking);

        reportCtx.setFormatter(new CSVStrategy());
        reportCtx.generateReport(liveSavings);

        reportCtx.setFormatter(new ChartStrategy());
        reportCtx.generateReport(liveChecking);

        notifService.printLog();

        System.out.println("\n--- Session End ---");
        session.logout();
    }
}
