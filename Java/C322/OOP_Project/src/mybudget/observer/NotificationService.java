package mybudget.observer;

import mybudget.model.Account;
import java.util.ArrayList;
import java.util.List;

/**
 * OBSERVER PATTERN - Concrete Observer
 * Logs all budget alerts to an in-memory notification history.
 */
public class NotificationService implements BudgetObserver {
    private List<String> notificationLog = new ArrayList<>();

    @Override
    public void onBudgetEvent(Account account, String message) {
        notificationLog.add(message);
        System.out.println("[NotificationService] Alert logged: " + message);
    }

    public void printLog() {
        System.out.println("\n--- Notification Log ---");
        if (notificationLog.isEmpty()) {
            System.out.println("  No alerts recorded.");
        } else {
            notificationLog.forEach(n -> System.out.println("  • " + n));
        }
        System.out.println("------------------------");
    }

    public List<String> getLog() { return notificationLog; }
}
