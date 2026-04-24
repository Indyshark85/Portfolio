package mybudget.singleton;

/**
 * SINGLETON PATTERN
 * Holds global application configuration settings.
 */
public class AppConfig {
    private static AppConfig instance;

    private String currency;
    private boolean alertsEnabled;
    private double defaultBudgetLimit;

    private AppConfig() {
        this.currency = "USD";
        this.alertsEnabled = true;
        this.defaultBudgetLimit = 1000.0;
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String getCurrency()            { return currency; }
    public void setCurrency(String c)      { this.currency = c; }
    public boolean isAlertsEnabled()       { return alertsEnabled; }
    public void setAlertsEnabled(boolean e){ this.alertsEnabled = e; }
    public double getDefaultBudgetLimit()  { return defaultBudgetLimit; }
    public void setDefaultBudgetLimit(double l) { this.defaultBudgetLimit = l; }

    @Override
    public String toString() {
        return String.format("AppConfig[currency=%s, alerts=%b, defaultLimit=%.2f]",
            currency, alertsEnabled, defaultBudgetLimit);
    }
}
