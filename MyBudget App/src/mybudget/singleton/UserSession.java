package mybudget.singleton;

import mybudget.model.Account;
import java.util.ArrayList;
import java.util.List;

/**
 * SINGLETON PATTERN
 * Holds the active user's accounts and session state.
 * Only one instance exists for the entire application lifecycle.
 */
public class UserSession {
    private static UserSession instance;

    private String username;
    private List<Account> accounts;

    // Private constructor prevents external instantiation
    private UserSession() {
        this.accounts = new ArrayList<>();
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void login(String username) {
        this.username = username;
        System.out.println("[Session] User logged in: " + username);
    }

    public void logout() {
        System.out.println("[Session] User logged out: " + username);
        this.username = null;
        this.accounts.clear();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account getAccount(String name) {
        return accounts.stream()
            .filter(a -> a.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    public List<Account> getAccounts() { return accounts; }
    public String getUsername()        { return username; }
    public boolean isLoggedIn()        { return username != null; }
}
