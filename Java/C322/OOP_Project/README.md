# MyBudget — Personal Finance Manager

## How to Build & Run

```bash
./build_and_run.sh
```

Or manually:
```bash
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out mybudget.Main
```

Requires Java 17+.

## Demo User

At the login prompt, type **`demo`** to get a pre-seeded session:
- **Checking** — $2,720 balance / $5,000 limit, 8 transactions (paychecks, rent, groceries, a large purchase)
- **Savings** — $9,750 balance / $10,000 limit, 3 transactions
- **Vacation Fund** — $70 balance / $2,000 limit (triggers a low-balance alert on load)

From there you can explore every feature: undo/redo the seeded transactions, generate reports in any format, toggle alerts, etc.

## Design Patterns

| Pattern   | Classes |
|-----------|---------|
| Singleton | `UserSession`, `AppConfig` |
| Command   | `DepositCommand`, `WithdrawCommand`, `SetBudgetLimitCommand`, `CommandHistory` |
| Observer  | `BudgetEventManager`, `BudgetAlertObserver`, `NotificationService` |
| Strategy  | `PDFStrategy`, `CSVStrategy`, `ChartStrategy`, `ReportContext` |

## Report Output

Reports are saved to the `reports/` folder at runtime:

| Strategy | File | Format |
|----------|------|--------|
| `PDFStrategy` | `reports/<Account>_report.pdf` | Real PDF (pure Java, no libraries) |
| `CSVStrategy` | `reports/<Account>_report.csv` | Standard CSV with metadata header |
| `ChartStrategy` | `reports/<Account>_chart.html` | Interactive HTML/Chart.js dashboard |
