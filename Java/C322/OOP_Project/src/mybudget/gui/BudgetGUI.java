package mybudget.gui;

import mybudget.command.*;
import mybudget.model.Account;
import mybudget.observer.*;
import mybudget.singleton.AppConfig;
import mybudget.singleton.UserSession;
import mybudget.strategy.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Swing GUI replacement for BudgetCLI.
 * Drop-in equivalent: same features, same backend calls, no terminal required.
 *
 * Structure
 * ─────────
 *  LoginDialog  →  MainWindow
 *                    ├─ Sidebar  (nav buttons)
 *                    └─ CardPanel
 *                         ├─ AccountsPanel
 *                         ├─ TransactionsPanel
 *                         ├─ UndoRedoPanel
 *                         ├─ ReportsPanel
 *                         ├─ NotificationsPanel
 *                         └─ SettingsPanel
 */
public class BudgetGUI {

    // ── Palette ───────────────────────────────────────────────────────────────
    private static final Color BG = new Color(15, 17, 23);   // near-black
    private static final Color SURFACE = new Color(24, 27, 37);   // card bg
    private static final Color SURFACE2 = new Color(33, 37, 51);   // input / row
    private static final Color ACCENT = new Color(99, 179, 237);   // sky blue
    private static final Color ACCENT2 = new Color(72, 149, 239);   // slightly deeper
    private static final Color SUCCESS = new Color(72, 199, 142);   // green
    private static final Color DANGER = new Color(252, 110, 90);  // coral
    private static final Color MUTED = new Color(120, 130, 155);  // grey text
    private static final Color TEXT = new Color(220, 225, 240);  // primary text
    private static final Color BORDER = new Color(45, 50, 68);

    // ── Typography ────────────────────────────────────────────────────────────
    private static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 20);
    private static final Font FONT_HEADER = new Font("SansSerif", Font.BOLD, 13);
    private static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font FONT_SMALL = new Font("SansSerif", Font.PLAIN, 11);
    private static final Font FONT_MONO = new Font("Monospaced", Font.PLAIN, 12);

    // ── App state ─────────────────────────────────────────────────────────────
    private final UserSession session = UserSession.getInstance();
    private final AppConfig config = AppConfig.getInstance();
    private final BudgetEventManager eventManager = new BudgetEventManager();
    private final CommandHistory history = new CommandHistory();
    private final NotificationService notifService = new NotificationService();

    // ── UI references (needed across panels) ─────────────────────────────────
    private JFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JLabel headerLabel;

    // Panel references for refreshing
    private AccountsPanel accountsPanel;
    private TransactionsPanel transactionsPanel;
    private UndoRedoPanel undoRedoPanel;
    private NotificationsPanel notificationsPanel;
    private SettingsPanel settingsPanel;

    // ═════════════════════════════════════════════════════════════════════════
    // Entry point
    // ═════════════════════════════════════════════════════════════════════════

    public void start() {
        // Set FlatLaf-friendly defaults without requiring extra deps
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            setupObservers();
            showLogin();
        });
    }

    private void setupObservers() {
        eventManager.subscribe(new BudgetAlertObserver("GUI Alert"));
        eventManager.subscribe(notifService);
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Login dialog
    // ═════════════════════════════════════════════════════════════════════════

    private void showLogin() {
        JDialog dialog = new JDialog((Frame) null, "MyBudget — Login", true);
        dialog.setUndecorated(false);
        dialog.setSize(420, 320);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Title
        JLabel title = label("MyBudget", FONT_TITLE, ACCENT);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel subtitle = label("Personal Finance Manager", FONT_SMALL, MUTED);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel top = new JPanel(new GridLayout(2, 1, 0, 4));
        top.setBackground(BG);
        top.add(title);
        top.add(subtitle);
        root.add(top, BorderLayout.NORTH);

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BG);
        form.setBorder(new EmptyBorder(24, 0, 0, 0));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 0, 6, 0);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        g.gridy = 0;
        form.add(label("Username", FONT_HEADER, TEXT), g);

        g.gridy = 1;
        JTextField userField = styledField();
        userField.setToolTipText("Press Enter for 'guest', type 'demo' for sample data");
        form.add(userField, g);

        g.gridy = 2;
        JLabel hint = label("Leave blank for guest  •  Type 'demo' for sample data", FONT_SMALL, MUTED);
        form.add(hint, g);

        g.gridy = 3;
        g.insets = new Insets(14, 0, 0, 0);
        JButton loginBtn = accentButton("Login →");
        form.add(loginBtn, g);

        root.add(form, BorderLayout.CENTER);
        dialog.setContentPane(root);

        Runnable doLogin = () -> {
            String username = userField.getText().trim();
            if (username.isEmpty()) username = "guest";
            session.login(username);
            dialog.dispose();
            buildMainWindow();
            if (username.equalsIgnoreCase("demo")) loadDemoData();
            mainFrame.setVisible(true);
        };

        loginBtn.addActionListener(e -> doLogin.run());
        userField.addActionListener(e -> doLogin.run());

        dialog.setVisible(true);
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Main window
    // ═════════════════════════════════════════════════════════════════════════

    private void buildMainWindow() {
        mainFrame = new JFrame("MyBudget — " + session.getUsername());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 620);
        mainFrame.setMinimumSize(new Dimension(780, 520));
        mainFrame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        // IMPORTANT: buildContent() must run first so all panel fields are
        // assigned before buildSidebar() wires up the nav-button listeners.
        JPanel content = buildContent();
        root.add(buildSidebar(), BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);

        mainFrame.setContentPane(root);
    }

    // ── Sidebar ───────────────────────────────────────────────────────────────

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SURFACE);
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new MatteBorder(0, 0, 0, 1, BORDER));

        // App name
        JLabel appName = label("💰 MyBudget", FONT_HEADER, ACCENT);
        appName.setBorder(new EmptyBorder(20, 20, 20, 20));
        appName.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(appName);

        sidebar.add(separator());

        // Nav items: (display label, card name)
        String[][] navItems = {
                {"Accounts", "accounts"},
                {"Transactions", "transactions"},
                {"Undo / Redo", "undoredo"},
                {"Reports", "reports"},
                {"Notifications", "notifications"},
                {"Settings", "settings"},
        };

        ButtonGroup group = new ButtonGroup();
        boolean first = true;
        for (String[] item : navItems) {
            JToggleButton btn = navButton(item[0], item[1]);
            group.add(btn);
            if (first) {
                btn.setSelected(true);
                first = false;
            }
            sidebar.add(btn);
        }

        sidebar.add(Box.createVerticalGlue());

        // User badge
        JLabel userLabel = label("⏺ " + session.getUsername(), FONT_SMALL, MUTED);
        userLabel.setBorder(new EmptyBorder(12, 20, 6, 20));
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(userLabel);

        // Exit button
        JButton exitBtn = new JButton("Exit");
        styleSecondaryButton(exitBtn);
        exitBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        exitBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        exitBtn.setBorder(new EmptyBorder(8, 20, 14, 20));
        exitBtn.addActionListener(e -> {
            notifService.printLog();
            session.logout();
            System.exit(0);
        });
        sidebar.add(exitBtn);

        return sidebar;
    }

    private JToggleButton navButton(String label, String card) {
        JToggleButton btn = new JToggleButton(label);
        btn.setFont(FONT_BODY);
        btn.setForeground(MUTED);
        btn.setBackground(SURFACE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(11, 20, 11, 20));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                btn.setForeground(ACCENT);
                btn.setBackground(new Color(30, 40, 60));
                navigate(card, label);
            } else {
                btn.setForeground(MUTED);
                btn.setBackground(SURFACE);
            }
        });
        return btn;
    }

    // ── Content area ──────────────────────────────────────────────────────────

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BG);

        // Top header bar
        JPanel headerBar = new JPanel(new BorderLayout());
        headerBar.setBackground(BG);
        headerBar.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(16, 28, 16, 28)
        ));
        headerLabel = label("Accounts", FONT_TITLE, TEXT);
        headerBar.add(headerLabel, BorderLayout.WEST);
        content.add(headerBar, BorderLayout.NORTH);

        // Card panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(BG);

        accountsPanel = new AccountsPanel();
        transactionsPanel = new TransactionsPanel();
        undoRedoPanel = new UndoRedoPanel();
        notificationsPanel = new NotificationsPanel();
        settingsPanel = new SettingsPanel();

        cardPanel.add(accountsPanel, "accounts");
        cardPanel.add(transactionsPanel, "transactions");
        cardPanel.add(undoRedoPanel, "undoredo");
        cardPanel.add(new ReportsPanel(), "reports");
        cardPanel.add(notificationsPanel, "notifications");
        cardPanel.add(settingsPanel, "settings");

        content.add(cardPanel, BorderLayout.CENTER);
        return content;
    }

    private void navigate(String card, String title) {
        // Refresh live panels before showing
        switch (card) {
            case "accounts" -> accountsPanel.refresh();
            case "transactions" -> transactionsPanel.refresh();
            case "undoredo" -> undoRedoPanel.refresh();
            case "notifications" -> notificationsPanel.refresh();
            case "settings" -> settingsPanel.refresh();
        }
        headerLabel.setText(title);
        cardLayout.show(cardPanel, card);
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Panels
    // ═════════════════════════════════════════════════════════════════════════

    // ── Accounts ──────────────────────────────────────────────────────────────

    class AccountsPanel extends JPanel {
        private final DefaultTableModel tableModel;
        private final JTable table;
        private final JPanel centerCard;   // swaps between emptyState and tableScroll
        private final CardLayout centerLayout = new CardLayout();
        private final JScrollPane tableScroll;

        AccountsPanel() {
            super(new BorderLayout(0, 16));
            setBackground(BG);
            setBorder(new EmptyBorder(24, 28, 24, 28));

            // ── Table ────────────────────────────────────────────────────────
            String[] cols = {"Account", "Balance", "Limit", "Balance Health"};
            tableModel = new DefaultTableModel(cols, 0) {
                @Override
                public boolean isCellEditable(int r, int c) {
                    return false;
                }
            };
            table = styledTable(tableModel);

            // Balance Health column — balance as % of limit.
            // BudgetEventManager fires alerts at <10% (critical) and <25% (warning),
            // so LOW % = danger, HIGH % = healthy. Colors match those thresholds exactly.
            table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable t, Object val,
                                                               boolean sel, boolean foc, int row, int col) {
                    JProgressBar bar = new JProgressBar(0, 100);
                    bar.setBackground(SURFACE2);
                    if (val instanceof Double pct) {
                        // Clamp to [0,100]. Negative balance (overdrawn) shows as 0%.
                        int v = (int) Math.min(100, Math.max(0, pct));
                        bar.setValue(v);
                        // Mirror BudgetEventManager thresholds:
                        //   < 10% → red (DANGER / overdrawn zone)
                        //   < 25% → yellow (WARNING zone)
                        //   >= 25% → green (healthy)
                        bar.setForeground(v < 10 ? DANGER : v < 25 ? new Color(250, 176, 5) : SUCCESS);
                        bar.setString(v + "% of limit");
                        bar.setStringPainted(true);
                    }
                    return bar;
                }
            });

            tableScroll = new JScrollPane(table);
            styleScrollPane(tableScroll);

            // ── Empty state ──────────────────────────────────────────────────
            JPanel emptyState = new JPanel(new GridBagLayout());
            emptyState.setBackground(BG);
            GridBagConstraints eg = new GridBagConstraints();
            eg.gridx = 0;
            eg.gridy = GridBagConstraints.RELATIVE;
            eg.insets = new Insets(6, 0, 6, 0);

            JLabel icon = label("🏦", new Font("SansSerif", Font.PLAIN, 48), TEXT);
            icon.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel msg = label("No accounts yet", FONT_TITLE, MUTED);
            JLabel hint = label("Create your first account to get started.", FONT_BODY, MUTED);
            JButton emptyCreateBtn = accentButton("+ Create Account");
            emptyCreateBtn.addActionListener(e -> showCreateAccountDialog());

            emptyState.add(icon, eg);
            emptyState.add(msg, eg);
            emptyState.add(hint, eg);
            eg.insets = new Insets(16, 0, 6, 0);
            emptyState.add(emptyCreateBtn, eg);

            // ── Center card (switches between states) ─────────────────────
            centerCard = new JPanel(centerLayout);
            centerCard.setBackground(BG);
            centerCard.add(emptyState, "empty");
            centerCard.add(tableScroll, "table");
            add(centerCard, BorderLayout.CENTER);

            // ── Toolbar (always visible) ──────────────────────────────────
            JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
            toolbar.setBackground(BG);
            JButton createBtn = accentButton("+ New Account");
            createBtn.addActionListener(e -> showCreateAccountDialog());
            toolbar.add(createBtn);
            add(toolbar, BorderLayout.SOUTH);

            refresh();
        }

        void refresh() {
            tableModel.setRowCount(0);
            List<Account> accounts = session.getAccounts();
            for (Account a : accounts) {
                double pct = a.getBudgetLimit() > 0
                        ? (a.getBalance() / a.getBudgetLimit()) * 100 : 0;
                tableModel.addRow(new Object[]{
                        a.getName(),
                        String.format("$%.2f", a.getBalance()),
                        String.format("$%.2f", a.getBudgetLimit()),
                        pct
                });
            }
            // Show empty state or table depending on whether accounts exist
            centerLayout.show(centerCard, accounts.isEmpty() ? "empty" : "table");
        }

        private void showCreateAccountDialog() {
            JDialog d = inputDialog(mainFrame, "New Account");
            JTextField nameF = styledField();
            JTextField balF = styledField();
            JTextField limF = styledField();

            JPanel form = formPanel(
                    "Account Name", nameF,
                    "Initial Balance ($)", balF,
                    "Budget Limit ($)", limF
            );
            d.add(form, BorderLayout.CENTER);

            JButton ok = accentButton("Create");
            ok.addActionListener(e -> {
                String name = nameF.getText().trim();
                if (name.isEmpty()) {
                    showError(d, "Name cannot be empty.");
                    return;
                }
                if (session.getAccount(name) != null) {
                    showError(d, "Account already exists.");
                    return;
                }
                double bal, lim;
                try {
                    bal = Double.parseDouble(balF.getText().trim());
                } catch (NumberFormatException ex) {
                    showError(d, "Invalid balance.");
                    return;
                }
                try {
                    lim = Double.parseDouble(limF.getText().trim());
                } catch (NumberFormatException ex) {
                    showError(d, "Invalid limit.");
                    return;
                }
                session.addAccount(new Account(name, bal, lim));
                refresh();
                d.dispose();
            });
            d.add(buttonBar(ok, cancelButton(d)), BorderLayout.SOUTH);
            d.pack();
            d.setVisible(true);
        }
    }

    // ── Transactions ──────────────────────────────────────────────────────────

    class TransactionsPanel extends JPanel {
        private final JComboBox<String> accountCombo = styledCombo();
        private final JTextField amountField = styledField();
        private final JLabel balanceLabel = label("", FONT_BODY, SUCCESS);
        private final JLabel limitLabel = label("", FONT_SMALL, MUTED);

        TransactionsPanel() {
            super(new BorderLayout(0, 0));
            setBackground(BG);
            setBorder(new EmptyBorder(24, 28, 24, 28));

            // Account selector
            JPanel top = new JPanel(new GridBagLayout());
            top.setBackground(BG);
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(6, 0, 6, 12);
            g.fill = GridBagConstraints.HORIZONTAL;

            g.gridx = 0;
            g.gridy = 0;
            g.weightx = 0;
            top.add(label("Account:", FONT_HEADER, TEXT), g);
            g.gridx = 1;
            g.weightx = 1;
            top.add(accountCombo, g);
            g.gridx = 2;
            g.weightx = 0;
            top.add(balanceLabel, g);

            g.gridx = 0;
            g.gridy = 1;
            g.weightx = 0;
            top.add(label("Amount ($):", FONT_HEADER, TEXT), g);
            g.gridx = 1;
            g.weightx = 1;
            top.add(amountField, g);
            g.gridx = 2;
            top.add(limitLabel, g);

            add(top, BorderLayout.NORTH);

            accountCombo.addActionListener(e -> updateAccountInfo());

            // Action buttons
            JPanel actions = new JPanel(new GridLayout(1, 3, 12, 0));
            actions.setBackground(BG);
            actions.setBorder(new EmptyBorder(20, 0, 0, 0));

            JButton depositBtn = accentButton("⬆ Deposit");
            depositBtn.setBackground(SUCCESS);
            JButton withdrawBtn = accentButton("⬇ Withdraw");
            withdrawBtn.setBackground(DANGER);
            JButton limitBtn = accentButton("✎ Set Limit");

            depositBtn.addActionListener(e -> doDeposit());
            withdrawBtn.addActionListener(e -> doWithdraw());
            limitBtn.addActionListener(e -> doSetLimit());

            actions.add(depositBtn);
            actions.add(withdrawBtn);
            actions.add(limitBtn);
            add(actions, BorderLayout.CENTER);

            refresh();
        }

        void refresh() {
            String prev = (String) accountCombo.getSelectedItem();
            accountCombo.removeAllItems();
            for (Account a : session.getAccounts()) accountCombo.addItem(a.getName());
            if (prev != null) accountCombo.setSelectedItem(prev);
            updateAccountInfo();
        }

        private void updateAccountInfo() {
            String sel = (String) accountCombo.getSelectedItem();
            if (sel == null) return;
            Account a = session.getAccount(sel);
            if (a == null) return;
            balanceLabel.setText(String.format("Balance: $%.2f", a.getBalance()));
            limitLabel.setText(String.format("Limit: $%.2f", a.getBudgetLimit()));
        }

        private Account selectedAccount() {
            String sel = (String) accountCombo.getSelectedItem();
            if (sel == null) {
                showError(mainFrame, "No account selected.");
                return null;
            }
            return session.getAccount(sel);
        }

        private double readAmount() {
            try {
                double v = Double.parseDouble(amountField.getText().trim());
                if (v <= 0) throw new NumberFormatException();
                return v;
            } catch (NumberFormatException ex) {
                showError(mainFrame, "Enter a positive number.");
                return -1;
            }
        }

        private void doDeposit() {
            Account a = selectedAccount();
            if (a == null) return;
            double amt = readAmount();
            if (amt < 0) return;
            history.executeCommand(new DepositCommand(a, amt, eventManager));
            updateAccountInfo();
            accountsPanel.refresh();
            undoRedoPanel.refresh();
            notificationsPanel.refresh();
            amountField.setText("");
            showInfo("Deposited $" + String.format("%.2f", amt) + " into " + a.getName());
        }

        private void doWithdraw() {
            Account a = selectedAccount();
            if (a == null) return;
            double amt = readAmount();
            if (amt < 0) return;
            history.executeCommand(new WithdrawCommand(a, amt, eventManager));
            updateAccountInfo();
            accountsPanel.refresh();
            undoRedoPanel.refresh();
            notificationsPanel.refresh();
            amountField.setText("");
            showInfo("Withdrew $" + String.format("%.2f", amt) + " from " + a.getName());
        }

        private void doSetLimit() {
            Account a = selectedAccount();
            if (a == null) return;
            double lim = readAmount();
            if (lim < 0) return;
            history.executeCommand(new SetBudgetLimitCommand(a, lim));
            updateAccountInfo();
            accountsPanel.refresh();
            amountField.setText("");
            showInfo("Budget limit for " + a.getName() + " set to $" + String.format("%.2f", lim));
        }
    }

    // ── Undo / Redo ───────────────────────────────────────────────────────────

    class UndoRedoPanel extends JPanel {
        private final JTextArea historyArea = new JTextArea();
        private final JButton undoBtn = accentButton("↩ Undo");
        private final JButton redoBtn = accentButton("↪ Redo");

        UndoRedoPanel() {
            super(new BorderLayout(0, 16));
            setBackground(BG);
            setBorder(new EmptyBorder(24, 28, 24, 28));

            historyArea.setEditable(false);
            historyArea.setBackground(SURFACE2);
            historyArea.setForeground(TEXT);
            historyArea.setFont(FONT_MONO);
            historyArea.setBorder(new EmptyBorder(10, 12, 10, 12));

            JScrollPane scroll = new JScrollPane(historyArea);
            styleScrollPane(scroll);
            scroll.setBorder(BorderFactory.createTitledBorder(
                    new LineBorder(BORDER), " Command History (most recent first) ",
                    TitledBorder.LEFT, TitledBorder.TOP, FONT_SMALL, MUTED));
            add(scroll, BorderLayout.CENTER);

            JPanel buttons = new JPanel(new GridLayout(1, 2, 12, 0));
            buttons.setBackground(BG);
            buttons.setBorder(new EmptyBorder(16, 0, 0, 0));

            undoBtn.addActionListener(e -> {
                history.undo();
                refresh();
                accountsPanel.refresh();
                transactionsPanel.refresh();
                notificationsPanel.refresh();
            });
            redoBtn.addActionListener(e -> {
                history.redo();
                refresh();
                accountsPanel.refresh();
                transactionsPanel.refresh();
                notificationsPanel.refresh();
            });

            buttons.add(undoBtn);
            buttons.add(redoBtn);
            add(buttons, BorderLayout.SOUTH);

            refresh();
        }

        void refresh() {
            // Enable/disable based on actual stack state — no silent no-ops
            undoBtn.setEnabled(history.canUndo());
            redoBtn.setEnabled(history.canRedo());
            undoBtn.setBackground(history.canUndo() ? ACCENT2 : SURFACE2);
            redoBtn.setBackground(history.canRedo() ? ACCENT2 : SURFACE2);

            // Build history text by capturing printHistory() output.
            // CommandHistory only exposes printHistory() for display, so we
            // capture its stdout output rather than duplicating its logic here.
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream ps = new java.io.PrintStream(baos);
            java.io.PrintStream old = System.out;
            System.setOut(ps);
            history.printHistory();
            System.setOut(old);
            historyArea.setText(baos.toString());
        }
    }

    // ── Reports ───────────────────────────────────────────────────────────────

    class ReportsPanel extends JPanel {
        ReportsPanel() {
            super(new BorderLayout(0, 16));
            setBackground(BG);
            setBorder(new EmptyBorder(24, 28, 24, 28));

            JComboBox<String> fmtCombo = styledCombo();
            fmtCombo.addItem("PDF  — reports/<name>_report.pdf");
            fmtCombo.addItem("CSV  — reports/<name>_report.csv");
            fmtCombo.addItem("HTML Chart — reports/<name>_chart.html");

            JComboBox<String> accCombo = styledCombo();
            session.getAccounts().forEach(a -> accCombo.addItem(a.getName()));

            JPanel form = new JPanel(new GridBagLayout());
            form.setBackground(BG);
            GridBagConstraints g = new GridBagConstraints();
            g.fill = GridBagConstraints.HORIZONTAL;
            g.insets = new Insets(8, 0, 8, 12);

            g.gridy = 0;
            g.gridx = 0;
            g.weightx = 0;
            form.add(label("Format:", FONT_HEADER, TEXT), g);
            g.gridx = 1;
            g.weightx = 1;
            form.add(fmtCombo, g);

            g.gridy = 1;
            g.gridx = 0;
            g.weightx = 0;
            form.add(label("Account:", FONT_HEADER, TEXT), g);
            g.gridx = 1;
            g.weightx = 1;
            form.add(accCombo, g);

            add(form, BorderLayout.NORTH);

            JButton genBtn = accentButton("Generate Report");
            genBtn.setPreferredSize(new Dimension(200, 40));
            genBtn.addActionListener(e -> {
                if (session.getAccounts().isEmpty()) {
                    showError(mainFrame, "No accounts available.");
                    return;
                }
                String accName = (String) accCombo.getSelectedItem();
                Account account = accName != null ? session.getAccount(accName) : null;
                if (account == null) {
                    showError(mainFrame, "Select an account.");
                    return;
                }

                ReportFormatter fmt = switch (fmtCombo.getSelectedIndex()) {
                    case 0 -> new PDFStrategy();
                    case 1 -> new CSVStrategy();
                    case 2 -> new ChartStrategy();
                    default -> null;
                };
                if (fmt != null) {
                    new ReportContext(fmt).generateReport(account);
                    showInfo("Report generated successfully.");
                }
            });

            JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
            center.setBackground(BG);
            center.add(genBtn);
            add(center, BorderLayout.CENTER);
        }
    }

    // ── Notifications ─────────────────────────────────────────────────────────

    class NotificationsPanel extends JPanel {
        private final JTextArea area = new JTextArea();

        NotificationsPanel() {
            super(new BorderLayout());
            setBackground(BG);
            setBorder(new EmptyBorder(24, 28, 24, 28));

            area.setEditable(false);
            area.setBackground(SURFACE2);
            area.setForeground(TEXT);
            area.setFont(FONT_MONO);
            area.setBorder(new EmptyBorder(12, 14, 12, 14));

            JScrollPane scroll = new JScrollPane(area);
            styleScrollPane(scroll);
            add(scroll, BorderLayout.CENTER);

            JButton clearBtn = new JButton("Clear");
            styleSecondaryButton(clearBtn);
            clearBtn.addActionListener(e -> area.setText(""));
            JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bar.setBackground(BG);
            bar.add(clearBtn);
            add(bar, BorderLayout.SOUTH);

            refresh();
        }

        void refresh() {
            List<String> log = notifService.getLog();
            if (log.isEmpty()) {
                area.setText("(no notifications yet)");
            } else {
                StringBuilder sb = new StringBuilder();
                for (String entry : log) sb.append("• ").append(entry).append("\n");
                area.setText(sb.toString().trim());
            }
        }
    }

    // ── Settings ──────────────────────────────────────────────────────────────

    class SettingsPanel extends JPanel {
        private final JToggleButton alertToggle;
        private final JTextField currencyField;

        SettingsPanel() {
            super(new GridBagLayout());
            setBackground(BG);
            setBorder(new EmptyBorder(24, 28, 24, 28));
            GridBagConstraints g = new GridBagConstraints();
            g.fill = GridBagConstraints.HORIZONTAL;
            g.insets = new Insets(10, 0, 10, 16);
            g.weightx = 0;

            // Alerts toggle
            g.gridy = 0;
            g.gridx = 0;
            add(label("Alerts enabled:", FONT_HEADER, TEXT), g);
            alertToggle = new JToggleButton(config.isAlertsEnabled() ? "ON" : "OFF");
            alertToggle.setSelected(config.isAlertsEnabled());
            alertToggle.setFont(FONT_HEADER);
            alertToggle.setForeground(TEXT);
            alertToggle.setBackground(config.isAlertsEnabled() ? SUCCESS : SURFACE2);
            alertToggle.setFocusPainted(false);
            alertToggle.addItemListener(e -> {
                boolean on = e.getStateChange() == ItemEvent.SELECTED;
                config.setAlertsEnabled(on);
                alertToggle.setText(on ? "ON" : "OFF");
                alertToggle.setBackground(on ? SUCCESS : SURFACE2);
            });
            g.gridx = 1;
            g.weightx = 1;
            add(alertToggle, g);

            // Currency
            g.gridy = 1;
            g.gridx = 0;
            g.weightx = 0;
            add(label("Currency:", FONT_HEADER, TEXT), g);
            currencyField = styledField();
            currencyField.setText(config.getCurrency());
            g.gridx = 1;
            g.weightx = 1;
            add(currencyField, g);

            // Default limit (read-only display)
            g.gridy = 2;
            g.gridx = 0;
            g.weightx = 0;
            add(label("Default limit:", FONT_HEADER, TEXT), g);
            g.gridx = 1;
            g.weightx = 1;
            add(label(String.format("$%.2f", config.getDefaultBudgetLimit()), FONT_BODY, MUTED), g);

            // Save button
            g.gridy = 3;
            g.gridx = 0;
            g.gridwidth = 2;
            g.weightx = 1;
            JButton save = accentButton("Save Settings");
            save.addActionListener(e -> {
                String cur = currencyField.getText().trim().toUpperCase();
                if (!cur.isEmpty()) {
                    config.setCurrency(cur);
                }
                showInfo("Settings saved.");
            });
            add(save, g);

            // filler
            g.gridy = 4;
            g.weighty = 1;
            add(Box.createVerticalGlue(), g);
        }

        void refresh() {
            alertToggle.setSelected(config.isAlertsEnabled());
            currencyField.setText(config.getCurrency());
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Demo data  (mirrors CLI loadDemoData)
    // ═════════════════════════════════════════════════════════════════════════

    private void loadDemoData() {
        Account checking = new Account("Checking", 3200.00, 5000.00);
        Account savings = new Account("Savings", 8500.00, 10000.00);
        Account vacation = new Account("Vacation Fund", 420.00, 2000.00);
        session.addAccount(checking);
        session.addAccount(savings);
        session.addAccount(vacation);

        silentExecute(new DepositCommand(checking, 2500.00, eventManager));
        silentExecute(new WithdrawCommand(checking, 850.00, eventManager));
        silentExecute(new WithdrawCommand(checking, 120.00, eventManager));
        silentExecute(new WithdrawCommand(checking, 65.00, eventManager));
        silentExecute(new DepositCommand(checking, 2500.00, eventManager));
        silentExecute(new WithdrawCommand(checking, 200.00, eventManager));
        silentExecute(new WithdrawCommand(checking, 45.00, eventManager));
        silentExecute(new WithdrawCommand(checking, 4200.00, eventManager));

        silentExecute(new DepositCommand(savings, 1000.00, eventManager));
        silentExecute(new DepositCommand(savings, 500.00, eventManager));
        silentExecute(new WithdrawCommand(savings, 250.00, eventManager));

        silentExecute(new DepositCommand(vacation, 300.00, eventManager));
        silentExecute(new WithdrawCommand(vacation, 650.00, eventManager));

        // Refresh all panels now that data is loaded
        accountsPanel.refresh();
        transactionsPanel.refresh();
        undoRedoPanel.refresh();
        notificationsPanel.refresh();

        JOptionPane.showMessageDialog(mainFrame,
                "Demo mode loaded!\n\nAccounts: Checking, Savings, Vacation Fund\nTransaction history seeded. Check Notifications for alerts.",
                "Demo Mode", JOptionPane.INFORMATION_MESSAGE);
    }

    private void silentExecute(Command cmd) {
        java.io.PrintStream original = System.out;
        System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
            public void write(int b) {
            }
        }));
        try {
            history.executeCommand(cmd);
        } finally {
            System.setOut(original);
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Widget factory helpers
    // ═════════════════════════════════════════════════════════════════════════

    private JLabel label(String text, Font font, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(font);
        l.setForeground(color);
        return l;
    }

    private JTextField styledField() {
        JTextField f = new JTextField();
        f.setBackground(SURFACE2);
        f.setForeground(TEXT);
        f.setCaretColor(ACCENT);
        f.setFont(FONT_BODY);
        f.setBorder(new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        return f;
    }

    @SuppressWarnings("unchecked")
    private <T> JComboBox<T> styledCombo() {
        JComboBox<T> c = new JComboBox<>();
        c.setBackground(SURFACE2);
        c.setForeground(TEXT);
        c.setFont(FONT_BODY);
        ((JLabel) c.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        return c;
    }

    private JButton accentButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_HEADER);
        b.setForeground(Color.WHITE);
        b.setBackground(ACCENT2);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(9, 18, 9, 18));
        return b;
    }

    private JButton cancelButton(JDialog d) {
        JButton b = new JButton("Cancel");
        styleSecondaryButton(b);
        b.addActionListener(e -> d.dispose());
        return b;
    }

    private void styleSecondaryButton(JButton b) {
        b.setFont(FONT_BODY);
        b.setForeground(MUTED);
        b.setBackground(SURFACE2);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private JTable styledTable(DefaultTableModel model) {
        JTable t = new JTable(model);
        t.setBackground(SURFACE);
        t.setForeground(TEXT);
        t.setFont(FONT_BODY);
        t.setRowHeight(32);
        t.setGridColor(BORDER);
        t.setShowHorizontalLines(true);
        t.setShowVerticalLines(false);
        t.setSelectionBackground(new Color(40, 60, 90));
        t.setSelectionForeground(TEXT);
        t.getTableHeader().setBackground(SURFACE2);
        t.getTableHeader().setForeground(MUTED);
        t.getTableHeader().setFont(FONT_SMALL);
        t.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, BORDER));
        return t;
    }

    private void styleScrollPane(JScrollPane sp) {
        sp.getViewport().setBackground(SURFACE);
        sp.setBorder(new LineBorder(BORDER));
        sp.getVerticalScrollBar().setBackground(SURFACE2);
        sp.getHorizontalScrollBar().setBackground(SURFACE2);
    }

    private JSeparator separator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        sep.setBackground(BORDER);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    private JDialog inputDialog(Frame parent, String title) {
        JDialog d = new JDialog(parent, title, true);
        d.setSize(380, 260);
        d.setLocationRelativeTo(parent);
        d.setLayout(new BorderLayout(0, 0));
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(20, 24, 16, 24));
        d.setContentPane(root);
        return d;
    }

    private JPanel formPanel(String l1, JComponent c1, String l2, JComponent c2,
                             String l3, JComponent c3) {
        JPanel p = new JPanel(new GridLayout(6, 1, 0, 6));
        p.setBackground(BG);
        p.add(label(l1, FONT_HEADER, TEXT));
        p.add(c1);
        p.add(label(l2, FONT_HEADER, TEXT));
        p.add(c2);
        p.add(label(l3, FONT_HEADER, TEXT));
        p.add(c3);
        return p;
    }

    private JPanel buttonBar(JButton... buttons) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        p.setBackground(BG);
        for (JButton b : buttons) p.add(b);
        return p;
    }

    private void showError(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(mainFrame, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}