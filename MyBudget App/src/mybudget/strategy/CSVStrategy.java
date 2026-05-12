package mybudget.strategy;

import mybudget.model.Account;
import mybudget.model.Transaction;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * STRATEGY PATTERN - Concrete Strategy
 * Writes a real CSV file to disk.
 * Output: reports/<AccountName>_report.csv
 */
public class CSVStrategy implements ReportFormatter {

    @Override
    public void generateReport(Account account) {
        File dir = new File("reports");
        dir.mkdirs();
        String filename = "reports/" + account.getName().replaceAll("\\s+", "_") + "_report.csv";

        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            // Metadata header rows
            pw.println("# MyBudget Account Report");
            pw.println("# Account," + account.getName());
            pw.println("# Generated," + date);
            pw.println("# Balance," + String.format("%.2f", account.getBalance()));
            pw.println("# BudgetLimit," + String.format("%.2f", account.getBudgetLimit()));
            pw.println();
            // Column headers
            pw.println("type,amount");
            for (Transaction t : account.getTransactions()) {
                pw.printf("%s,%.2f%n", t.getType(), t.getAmount());
            }
            // Summary row
            pw.println();
            pw.printf("BALANCE,%.2f%n", account.getBalance());

            System.out.println("[CSVStrategy] Report saved to: " + filename);
        } catch (IOException e) {
            System.err.println("[CSVStrategy] Failed to write CSV: " + e.getMessage());
        }
    }
}
