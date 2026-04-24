package mybudget.strategy;

import mybudget.model.Account;
import mybudget.model.Transaction;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * STRATEGY PATTERN - Concrete Strategy
 * Generates a real, valid PDF file using hand-crafted PDF syntax (no external libraries).
 * Output: reports/<AccountName>_report.pdf
 */
public class PDFStrategy implements ReportFormatter {

    @Override
    public void generateReport(Account account) {
        File dir = new File("reports");
        dir.mkdirs();
        String filename = "reports/" + account.getName().replaceAll("\\s+", "_") + "_report.pdf";

        try {
            byte[] pdfBytes = buildPdf(account);
            try (FileOutputStream fos = new FileOutputStream(filename)) {
                fos.write(pdfBytes);
            }
            System.out.println("[PDFStrategy] Report saved to: " + filename);
        } catch (IOException e) {
            System.err.println("[PDFStrategy] Failed to write PDF: " + e.getMessage());
        }
    }

    private byte[] buildPdf(Account account) throws IOException {
        List<Integer> offsets = new ArrayList<>();
        ByteArrayOutputStream body = new ByteArrayOutputStream();

        String content = buildContentStream(account);
        byte[] contentBytes = content.getBytes(StandardCharsets.ISO_8859_1);

        String obj1 = "1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n";
        String obj2 = "2 0 obj\n<< /Type /Pages /Kids [4 0 R] /Count 1 >>\nendobj\n";
        String obj3 = "3 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Courier >>\nendobj\n";
        String obj4 = "4 0 obj\n<< /Type /Page /Parent 2 0 R\n"
                + "   /MediaBox [0 0 595 842]\n"
                + "   /Contents 5 0 R\n"
                + "   /Resources << /Font << /F1 3 0 R >> >>\n"
                + ">>\nendobj\n";
        String obj5header = "5 0 obj\n<< /Length " + contentBytes.length + " >>\nstream\n";
        String obj5footer = "\nendstream\nendobj\n";

        byte[] header = "%PDF-1.4\n".getBytes(StandardCharsets.ISO_8859_1);
        body.write(header);
        int off = header.length;

        for (String obj : new String[]{ obj1, obj2, obj3, obj4 }) {
            offsets.add(off);
            byte[] b = obj.getBytes(StandardCharsets.ISO_8859_1);
            body.write(b);
            off += b.length;
        }

        offsets.add(off);
        byte[] h5 = obj5header.getBytes(StandardCharsets.ISO_8859_1);
        byte[] f5 = obj5footer.getBytes(StandardCharsets.ISO_8859_1);
        body.write(h5);
        body.write(contentBytes);
        body.write(f5);
        off += h5.length + contentBytes.length + f5.length;

        int xrefOffset = off;
        StringBuilder xref = new StringBuilder();
        xref.append("xref\n0 6\n");
        xref.append("0000000000 65535 f \n");
        for (int o : offsets) {
            xref.append(String.format("%010d 00000 n \n", o));
        }
        xref.append("trailer\n<< /Size 6 /Root 1 0 R >>\n");
        xref.append("startxref\n").append(xrefOffset).append("\n%%EOF\n");
        body.write(xref.toString().getBytes(StandardCharsets.ISO_8859_1));

        return body.toByteArray();
    }

    private String buildContentStream(Account account) {
        StringBuilder sb = new StringBuilder();
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        sb.append("BT\n");
        sb.append("/F1 16 Tf\n50 800 Td\n");
        sb.append("(MyBudget -- Account Report) Tj\n");

        sb.append("/F1 11 Tf\n0 -24 Td\n");
        sb.append("(Generated: ").append(escape(date)).append(") Tj\n");
        sb.append("0 -20 Td\n(------------------------------------------------) Tj\n");
        sb.append("0 -20 Td\n(Account      : ").append(escape(account.getName())).append(") Tj\n");
        sb.append("0 -18 Td\n");
        sb.append(String.format("(Balance      : $%.2f) Tj%n", account.getBalance()));
        sb.append("0 -18 Td\n");
        sb.append(String.format("(Budget Limit : $%.2f) Tj%n", account.getBudgetLimit()));
        double pct = account.getBudgetLimit() > 0 ? (account.getBalance() / account.getBudgetLimit()) * 100 : 0;
        sb.append("0 -18 Td\n");
        sb.append(String.format("(Balance %% of Limit: %.1f%%) Tj%n", pct));
        sb.append("0 -20 Td\n(------------------------------------------------) Tj\n");

        sb.append("/F1 13 Tf\n0 -20 Td\n(Transaction History) Tj\n");
        sb.append("/F1 10 Tf\n");

        List<Transaction> txns = account.getTransactions();
        if (txns.isEmpty()) {
            sb.append("0 -16 Td\n(  No transactions recorded.) Tj\n");
        } else {
            for (Transaction t : txns) {
                sb.append("0 -15 Td\n(  ").append(escape(t.toString())).append(") Tj\n");
            }
        }
        sb.append("0 -20 Td\n(------------------------------------------------) Tj\n");
        sb.append("ET\n");
        return sb.toString();
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)").replace("\n", " ");
    }
}
