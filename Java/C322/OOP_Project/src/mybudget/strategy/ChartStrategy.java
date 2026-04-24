package mybudget.strategy;

import mybudget.model.Account;
import mybudget.model.Transaction;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * STRATEGY PATTERN - Concrete Strategy
 * Generates an interactive HTML file with Chart.js bar + pie charts.
 * Output: reports/<AccountName>_chart.html
 */
public class ChartStrategy implements ReportFormatter {

    @Override
    public void generateReport(Account account) {
        File dir = new File("reports");
        dir.mkdirs();
        String filename = "reports/" + account.getName().replaceAll("\\s+", "_") + "_chart.html";

        double deposits = 0, withdrawals = 0;
        List<String> labels = new ArrayList<>();
        List<Double> amounts = new ArrayList<>();
        List<String> colors = new ArrayList<>();

        for (Transaction t : account.getTransactions()) {
            if (t.getType().equals("DEPOSIT")) {
                deposits += t.getAmount();
                labels.add("DEPOSIT");
                colors.add("rgba(72,199,142,0.85)");
            } else {
                withdrawals += t.getAmount();
                labels.add("WITHDRAWAL");
                colors.add("rgba(255,99,132,0.85)");
            }
            amounts.add(t.getAmount());
        }

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        double balance = account.getBalance();
        double limit = account.getBudgetLimit();
        double pct = limit > 0 ? (balance / limit) * 100 : 0;

        // Build JS arrays
        StringBuilder labelsJs = new StringBuilder("[");
        StringBuilder amtsJs = new StringBuilder("[");
        StringBuilder colorsJs = new StringBuilder("[");
        for (int i = 0; i < labels.size(); i++) {
            labelsJs.append("'").append(labels.get(i)).append(" #").append(i+1).append("'");
            amtsJs.append(String.format("%.2f", amounts.get(i)));
            colorsJs.append("'").append(colors.get(i)).append("'");
            if (i < labels.size()-1) { labelsJs.append(","); amtsJs.append(","); colorsJs.append(","); }
        }
        labelsJs.append("]"); amtsJs.append("]"); colorsJs.append("]");

        String html = buildHtml(account.getName(), date, balance, limit, pct,
                deposits, withdrawals, labelsJs.toString(), amtsJs.toString(), colorsJs.toString());

        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.print(html);
            System.out.println("[ChartStrategy] Chart saved to: " + filename);
        } catch (IOException e) {
            System.err.println("[ChartStrategy] Failed to write chart: " + e.getMessage());
        }
    }

    private String buildHtml(String name, String date, double balance, double limit, double pct,
                              double deposits, double withdrawals,
                              String labels, String amounts, String colors) {
        return "<!DOCTYPE html>\n<html lang='en'>\n<head>\n<meta charset='UTF-8'>\n"
            + "<title>MyBudget - " + name + " Report</title>\n"
            + "<script src='https://cdn.jsdelivr.net/npm/chart.js@4/dist/chart.umd.min.js'></script>\n"
            + "<style>\n"
            + "  body{font-family:'Segoe UI',sans-serif;background:#0f172a;color:#e2e8f0;margin:0;padding:24px;}\n"
            + "  h1{color:#38bdf8;margin-bottom:4px;} .sub{color:#94a3b8;font-size:13px;margin-bottom:24px;}\n"
            + "  .cards{display:flex;gap:16px;flex-wrap:wrap;margin-bottom:32px;}\n"
            + "  .card{background:#1e293b;border-radius:12px;padding:20px 28px;min-width:160px;}\n"
            + "  .card .label{font-size:11px;text-transform:uppercase;letter-spacing:1px;color:#64748b;margin-bottom:6px;}\n"
            + "  .card .val{font-size:26px;font-weight:700;}\n"
            + "  .green{color:#4ade80;} .red{color:#f87171;} .blue{color:#38bdf8;} .yellow{color:#fbbf24;}\n"
            + "  .charts{display:grid;grid-template-columns:1fr 1fr;gap:24px;}\n"
            + "  .chart-box{background:#1e293b;border-radius:12px;padding:20px;}\n"
            + "  .chart-box h2{font-size:14px;color:#94a3b8;margin:0 0 16px;}\n"
            + "  @media(max-width:640px){.charts{grid-template-columns:1fr;}}\n"
            + "</style>\n</head>\n<body>\n"
            + "<h1>MyBudget — " + name + " Account</h1>\n"
            + "<p class='sub'>Generated: " + date + "</p>\n"
            + "<div class='cards'>\n"
            + "  <div class='card'><div class='label'>Balance</div><div class='val blue'>$" + String.format("%.2f", balance) + "</div></div>\n"
            + "  <div class='card'><div class='label'>Budget Limit</div><div class='val yellow'>$" + String.format("%.2f", limit) + "</div></div>\n"
            + "  <div class='card'><div class='label'>% of Limit</div><div class='val " + (pct < 25 ? "red" : pct < 50 ? "yellow" : "green") + "'>" + String.format("%.1f", pct) + "%</div></div>\n"
            + "  <div class='card'><div class='label'>Total Deposited</div><div class='val green'>$" + String.format("%.2f", deposits) + "</div></div>\n"
            + "  <div class='card'><div class='label'>Total Withdrawn</div><div class='val red'>$" + String.format("%.2f", withdrawals) + "</div></div>\n"
            + "</div>\n"
            + "<div class='charts'>\n"
            + "  <div class='chart-box'><h2>Transaction Breakdown</h2><canvas id='barChart'></canvas></div>\n"
            + "  <div class='chart-box'><h2>Deposits vs Withdrawals</h2><canvas id='pieChart'></canvas></div>\n"
            + "</div>\n"
            + "<script>\n"
            + "const barCtx = document.getElementById('barChart').getContext('2d');\n"
            + "new Chart(barCtx, { type: 'bar', data: {\n"
            + "  labels: " + labels + ",\n"
            + "  datasets: [{ label: 'Amount ($)', data: " + amounts + ",\n"
            + "    backgroundColor: " + colors + ", borderRadius: 6 }]\n"
            + "}, options: { plugins: { legend: { labels: { color: '#e2e8f0' }}},\n"
            + "  scales: { x: { ticks: { color: '#94a3b8' }, grid: { color: '#1e293b' }},\n"
            + "            y: { ticks: { color: '#94a3b8' }, grid: { color: '#334155' }}}}});\n"
            + "const pieCtx = document.getElementById('pieChart').getContext('2d');\n"
            + "new Chart(pieCtx, { type: 'doughnut', data: {\n"
            + "  labels: ['Deposits','Withdrawals'],\n"
            + "  datasets: [{ data: [" + String.format("%.2f", deposits) + "," + String.format("%.2f", withdrawals) + "],\n"
            + "    backgroundColor: ['rgba(72,199,142,0.85)','rgba(255,99,132,0.85)'],\n"
            + "    borderColor: ['#0f172a','#0f172a'], borderWidth: 3 }]\n"
            + "}, options: { plugins: { legend: { labels: { color: '#e2e8f0' }}}}});\n"
            + "</script>\n</body>\n</html>\n";
    }
}
