import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI application for the Customer Order System (COS).
 * Uses Java Swing with CardLayout to navigate between screens.
 * Screens: Main Menu, Create Account, Log On, Customer Menu,
 *          Browse Catalog, View Cart, Make Order, View Orders.
 */
public class CosGUI extends JFrame {

    // ── Shared data ──────────────────────────────────────────────────────────
    private List<Account>  accounts     = new ArrayList<>();
    private List<Customer> customers    = new ArrayList<>();
    private Catalog        catalog      = new Catalog();
    private Customer currentCustomer    = null;
    private Account  currentAccount     = null;
    private Cart     cart               = new Cart();
    private List<Order> guiOrders       = new ArrayList<>();

    private static final String[] SECURITY_QUESTIONS = {
            "What is your mother's maiden name?",
            "What was the name of your first pet?",
            "What city were you born in?"
    };

    // ── Card names ────────────────────────────────────────────────────────────
    private static final String CARD_MAIN     = "MAIN";
    private static final String CARD_CREATE   = "CREATE";
    private static final String CARD_LOGIN    = "LOGIN";
    private static final String CARD_CUSTOMER = "CUSTOMER";
    private static final String CARD_CATALOG  = "CATALOG";
    private static final String CARD_CART     = "CART";
    private static final String CARD_ORDER    = "ORDER";
    private static final String CARD_ORDERS   = "ORDERS";

    // ── Layout ────────────────────────────────────────────────────────────────
    private CardLayout cardLayout = new CardLayout();
    private JPanel     cardPanel  = new JPanel(cardLayout);

    // ── Colors & Fonts ────────────────────────────────────────────────────────
    private static final Color BG        = new Color(245, 245, 250);
    private static final Color PRIMARY   = new Color(25, 118, 210);
    private static final Color PRIMARY_D = new Color(13,  71, 161);
    private static final Color SUCCESS   = new Color(46, 125,  50);
    private static final Color DANGER    = new Color(198,  40,  40);
    private static final Color SURFACE   = Color.WHITE;
    private static final Color TEXT      = new Color(33,  33,  33);
    private static final Color MUTED     = new Color(117, 117, 117);

    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD,  20);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_BTN   = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);

    /**
     * Constructs and displays the COS GUI application.
     */
    public CosGUI() {
        setTitle("Customer Order System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(620, 520);
        setMinimumSize(new Dimension(500, 420));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);

        cardPanel.setBackground(BG);
        cardPanel.add(buildMainMenu(),      CARD_MAIN);
        cardPanel.add(buildCreateAccount(), CARD_CREATE);
        cardPanel.add(buildLogOn(),         CARD_LOGIN);
        cardPanel.add(buildCustomerMenu(),  CARD_CUSTOMER);
        cardPanel.add(buildCatalog(),       CARD_CATALOG);
        cardPanel.add(buildCart(),          CARD_CART);
        cardPanel.add(buildMakeOrder(),     CARD_ORDER);
        cardPanel.add(buildViewOrders(),    CARD_ORDERS);

        add(cardPanel);
        setVisible(true);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // HELPERS
    // ─────────────────────────────────────────────────────────────────────────

    /** Switches to the named card screen. */
    private void show(String card) { cardLayout.show(cardPanel, card); }

    /** Styled filled primary button. */
    private JButton primaryBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BTN);
        b.setBackground(PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(PRIMARY_D); }
            public void mouseExited (MouseEvent e) { b.setBackground(PRIMARY);   }
        });
        return b;
    }

    /** Styled outline secondary button. */
    private JButton secondaryBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BTN);
        b.setBackground(SURFACE);
        b.setForeground(PRIMARY);
        b.setBorder(BorderFactory.createLineBorder(PRIMARY, 1));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        return b;
    }

    /** Centered title label. */
    private JLabel titleLabel(String text) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(FONT_TITLE);
        l.setForeground(TEXT);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    /** Standard form label. */
    private JLabel lbl(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_LABEL);
        l.setForeground(TEXT);
        return l;
    }

    /** Styled single-line text field. */
    private JTextField field() {
        JTextField f = new JTextField();
        f.setFont(FONT_LABEL);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        return f;
    }

    /** Styled password field. */
    private JPasswordField passField() {
        JPasswordField f = new JPasswordField();
        f.setFont(FONT_LABEL);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        return f;
    }

    /** Small status label for success/error messages. */
    private JLabel statusLabel() {
        JLabel l = new JLabel(" ");
        l.setFont(FONT_SMALL);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    /** Fixed-height vertical spacer. */
    private Component gap(int h) { return Box.createVerticalStrut(h); }

    /** Finds an Account by customer ID, returns null if not found. */
    private Account findAccount(String id) {
        for (Account a : accounts) if (a.getCustomerID().equals(id)) return a;
        return null;
    }

    /** Finds a Customer by customer ID, returns null if not found. */
    private Customer findCustomer(String id) {
        for (Customer c : customers) if (c.getCustomerID().equals(id)) return c;
        return null;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SCREEN: MAIN MENU
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Builds the main menu screen.
     *
     * @return the main menu panel
     */
    private JPanel buildMainMenu() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(SURFACE);
        inner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));
        inner.setPreferredSize(new Dimension(4200, 280));

        JLabel title = titleLabel("Customer Order System");
        JLabel sub   = new JLabel("Please select an option to continue", SwingConstants.CENTER);
        sub.setFont(FONT_LABEL); sub.setForeground(MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnCreate = primaryBtn("Create Account");
        JButton btnLogin  = secondaryBtn("Log On");
        JButton btnExit   = new JButton("Exit");
        btnExit.setFont(FONT_BTN); btnExit.setForeground(MUTED);
        btnExit.setBorderPainted(false); btnExit.setContentAreaFilled(false);
        btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (JButton b : new JButton[]{btnCreate, btnLogin}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(220, 38));
        }

        btnCreate.addActionListener(e -> show(CARD_CREATE));
        btnLogin .addActionListener(e -> show(CARD_LOGIN));
        btnExit  .addActionListener(e -> System.exit(0));

        inner.add(title);     inner.add(gap(6));
        inner.add(sub);       inner.add(gap(28));
        inner.add(btnCreate); inner.add(gap(10));
        inner.add(btnLogin);  inner.add(gap(14));
        inner.add(btnExit);

        outer.add(inner);
        return outer;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SCREEN: CREATE ACCOUNT
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Builds the Create Account screen with validation.
     *
     * @return the create account panel
     */
    private JPanel buildCreateAccount() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(SURFACE);
        inner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 60, 20, 60),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        BorderFactory.createEmptyBorder(24, 30, 24, 30)
                )
        ));

        JTextField     tfID  = field();
        JPasswordField tfPwd = passField();
        JTextField     tfNm  = field();
        JTextField     tfAd  = field();
        JTextField     tfCC  = field();
        JComboBox<String> cbQ = new JComboBox<>(SECURITY_QUESTIONS);
        cbQ.setFont(FONT_LABEL);
        cbQ.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        JTextField tfAns  = field();
        JLabel     status = statusLabel();

        JButton btnSubmit = primaryBtn("Create Account");
        JButton btnBack   = secondaryBtn("Back");
        btnSubmit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnBack  .setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        btnBack.addActionListener(e -> {
            tfID.setText(""); tfPwd.setText(""); tfNm.setText("");
            tfAd.setText(""); tfCC.setText(""); tfAns.setText("");
            status.setText(" "); show(CARD_MAIN);
        });

        btnSubmit.addActionListener(e -> {
            String id  = tfID.getText().trim();
            String pwd = new String(tfPwd.getPassword()).trim();
            String nm  = tfNm.getText().trim();
            String ad  = tfAd.getText().trim();
            String cc  = tfCC.getText().trim();
            String ans = tfAns.getText().trim();
            String q   = (String) cbQ.getSelectedItem();

            if (id.isEmpty() || pwd.isEmpty() || nm.isEmpty() || ad.isEmpty() || cc.isEmpty() || ans.isEmpty()) {
                status.setForeground(DANGER); status.setText("All fields are required."); return;
            }
            if (findAccount(id) != null) {
                status.setForeground(DANGER); status.setText("ID already exists. Choose a different one."); return;
            }
            if (!Account.isPasswordValid(pwd)) {
                status.setForeground(DANGER);
                status.setText("Password needs 6+ chars, 1 uppercase, 1 digit, 1 special (!@#$%^&*).");
                return;
            }
            accounts.add(new Account(id, pwd, q, ans));
            customers.add(new Customer(id, nm, ad, cc));
            status.setForeground(SUCCESS);
            status.setText("Account created successfully! You can now log on.");
            tfID.setText(""); tfPwd.setText(""); tfNm.setText("");
            tfAd.setText(""); tfCC.setText(""); tfAns.setText("");
        });

        inner.add(titleLabel("Create Account")); inner.add(gap(16));
        String[] lbls    = {"Customer ID", "Password", "Full Name", "Address", "Credit Card #"};
        JComponent[] flds = {tfID, tfPwd, tfNm, tfAd, tfCC};
        for (int i = 0; i < lbls.length; i++) {
            inner.add(lbl(lbls[i])); inner.add(gap(3)); inner.add(flds[i]); inner.add(gap(8));
        }
        inner.add(lbl("Security Question")); inner.add(gap(3)); inner.add(cbQ);  inner.add(gap(8));
        inner.add(lbl("Answer"));            inner.add(gap(3)); inner.add(tfAns); inner.add(gap(14));
        inner.add(status);    inner.add(gap(8));
        inner.add(btnSubmit); inner.add(gap(8));
        inner.add(btnBack);

        JScrollPane scroll = new JScrollPane(inner);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        outer.add(scroll, BorderLayout.CENTER);
        return outer;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SCREEN: LOG ON
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Builds the Log On screen with 3-attempt limit and security question.
     *
     * @return the log on panel
     */
    private JPanel buildLogOn() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(SURFACE);
        inner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        inner.setPreferredSize(new Dimension(360, 390));

        JTextField     tfID  = field();
        JPasswordField tfPwd = passField();
        JLabel lblSecQ       = new JLabel(" ");
        lblSecQ.setFont(FONT_BOLD); lblSecQ.setForeground(PRIMARY);
        JLabel lblAnsLbl     = lbl("Your Answer");
        JTextField tfAns     = field();
        lblAnsLbl.setVisible(false); tfAns.setVisible(false);
        JLabel status        = statusLabel();

        final int[]     attempts = {0};
        final Account[] found    = {null};

        JButton btnNext = primaryBtn("Next");
        JButton btnBack = secondaryBtn("Back");
        btnNext.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnBack.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        Runnable reset = () -> {
            tfID.setText(""); tfPwd.setText(""); tfAns.setText("");
            lblSecQ.setText(" "); lblAnsLbl.setVisible(false);
            tfAns.setVisible(false); status.setText(" ");
            attempts[0] = 0; found[0] = null; btnNext.setText("Next");
        };

        btnBack.addActionListener(e -> { reset.run(); show(CARD_MAIN); });

        btnNext.addActionListener(e -> {
            if (found[0] == null) {
                // Phase 1: validate ID + password
                String id  = tfID.getText().trim();
                String pwd = new String(tfPwd.getPassword()).trim();
                Account acc = findAccount(id);
                if (acc == null) {
                    status.setForeground(DANGER); status.setText("No account found for that ID."); return;
                }
                if (!acc.validatePassword(pwd)) {
                    attempts[0]++;
                    if (attempts[0] >= 3) {
                        status.setForeground(DANGER); status.setText("Too many attempts. Returning to menu.");
                        Timer t = new Timer(1800, ev -> { reset.run(); show(CARD_MAIN); });
                        t.setRepeats(false); t.start();
                    } else {
                        status.setForeground(DANGER);
                        status.setText("Wrong password. " + (3 - attempts[0]) + " attempt(s) left.");
                    }
                    return;
                }
                found[0] = acc;
                lblSecQ.setText(acc.getSecurityQuestion());
                lblAnsLbl.setVisible(true); tfAns.setVisible(true);
                status.setForeground(MUTED); status.setText("Password correct. Answer your security question.");
                btnNext.setText("Log On");
                inner.revalidate(); inner.repaint();
                return;
            }
            // Phase 2: validate security answer
            if (!found[0].validateSecurityAnswer(tfAns.getText().trim())) {
                status.setForeground(DANGER); status.setText("Incorrect answer. Access denied.");
                Timer t = new Timer(1800, ev -> { reset.run(); show(CARD_MAIN); });
                t.setRepeats(false); t.start();
                return;
            }
            currentAccount  = found[0];
            currentCustomer = findCustomer(found[0].getCustomerID());
            currentCustomer.logIn();
            cart = new Cart();
            lblWelcome.setText("Welcome, " + currentCustomer.getName() + "!");
            reset.run();
            show(CARD_CUSTOMER);
        });

        inner.add(titleLabel("Log On")); inner.add(gap(16));
        inner.add(lbl("Customer ID"));  inner.add(gap(3)); inner.add(tfID);  inner.add(gap(10));
        inner.add(lbl("Password"));     inner.add(gap(3)); inner.add(tfPwd); inner.add(gap(12));
        inner.add(lblSecQ);             inner.add(gap(4));
        inner.add(lblAnsLbl);           inner.add(gap(3)); inner.add(tfAns); inner.add(gap(12));
        inner.add(status);              inner.add(gap(8));
        inner.add(btnNext);             inner.add(gap(8));
        inner.add(btnBack);

        outer.add(inner);
        return outer;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SCREEN: CUSTOMER MENU
    // ─────────────────────────────────────────────────────────────────────────

    private JLabel lblWelcome = new JLabel("Welcome!");

    /**
     * Builds the customer menu shown after a successful login.
     *
     * @return the customer menu panel
     */
    private JPanel buildCustomerMenu() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(SURFACE);
        inner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));
        inner.setPreferredSize(new Dimension(340, 360));

        lblWelcome.setFont(FONT_TITLE);
        lblWelcome.setForeground(TEXT);
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[]  labels = {"Browse Catalog", "View Cart", "Make Order", "View My Orders", "Log Out"};
        JButton[] btns   = new JButton[labels.length];
        for (int i = 0; i < labels.length; i++) {
            btns[i] = i < 4 ? primaryBtn(labels[i]) : secondaryBtn(labels[i]);
            btns[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            btns[i].setMaximumSize(new Dimension(230, 38));
        }

        btns[0].addActionListener(e -> { refreshCatalog(); show(CARD_CATALOG); });
        btns[1].addActionListener(e -> { refreshCart();    show(CARD_CART);    });
        btns[2].addActionListener(e -> { refreshOrder();   show(CARD_ORDER);   });
        btns[3].addActionListener(e -> { refreshOrders();  show(CARD_ORDERS);  });
        btns[4].addActionListener(e -> {
            currentCustomer.logOut();
            currentCustomer = null; currentAccount = null;
            show(CARD_MAIN);
        });

        inner.add(lblWelcome); inner.add(gap(20));
        for (JButton b : btns) { inner.add(b); inner.add(gap(10)); }

        outer.add(inner);
        return outer;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SCREEN: BROWSE CATALOG
    // ─────────────────────────────────────────────────────────────────────────

    private JPanel catalogItemsPanel = new JPanel();
    private JLabel catalogStatus     = statusLabel();

    /**
     * Builds the catalog screen where customers select items to add to cart.
     *
     * @return the catalog panel
     */
    private JPanel buildCatalog() {
        JPanel outer = new JPanel(new BorderLayout(0, 10));
        outer.setBackground(BG);
        outer.setBorder(BorderFactory.createEmptyBorder(16, 40, 16, 40));

        JLabel title = titleLabel("Product Catalog");
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        catalogItemsPanel.setLayout(new BoxLayout(catalogItemsPanel, BoxLayout.Y_AXIS));
        catalogItemsPanel.setBackground(BG);

        JScrollPane scroll = new JScrollPane(catalogItemsPanel);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scroll.getViewport().setBackground(BG);

        catalogStatus.setFont(FONT_LABEL);

        JButton btnBack = secondaryBtn("Back to Menu");
        btnBack.addActionListener(e -> show(CARD_CUSTOMER));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBackground(BG);
        bottom.add(catalogStatus);
        bottom.add(btnBack);

        outer.add(title,  BorderLayout.NORTH);
        outer.add(scroll, BorderLayout.CENTER);
        outer.add(bottom, BorderLayout.SOUTH);
        return outer;
    }

    /** Populates the catalog screen with product rows. */
    private void refreshCatalog() {
        catalogItemsPanel.removeAll();
        catalogStatus.setText(" ");
        for (Product p : catalog.getProducts()) {
            catalogItemsPanel.add(buildProductRow(p));
            catalogItemsPanel.add(new JSeparator());
        }
        catalogItemsPanel.revalidate();
        catalogItemsPanel.repaint();
    }

    /**
     * Builds a single product row with name, description, price, quantity spinner, and Add button.
     *
     * @param p the product to display
     * @return the product row panel
     */
    private JPanel buildProductRow(Product p) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(SURFACE);
        row.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(SURFACE);
        JLabel name  = new JLabel(p.getName());       name.setFont(FONT_BOLD);
        JLabel desc  = new JLabel(p.getDescription()); desc.setFont(FONT_SMALL); desc.setForeground(MUTED);
        JLabel price = new JLabel(String.format("Sale: $%.2f  (Reg: $%.2f)", p.getSalePrice(), p.getRegularPrice()));
        price.setFont(FONT_SMALL); price.setForeground(SUCCESS);
        info.add(name); info.add(desc); info.add(price);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        right.setBackground(SURFACE);
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        spinner.setPreferredSize(new Dimension(55, 28));
        JButton add = primaryBtn("Add");
        add.setPreferredSize(new Dimension(70, 28));
        add.addActionListener(e -> {
            int qty = (int) spinner.getValue();
            cart.addItem(p, qty);
            catalogStatus.setForeground(SUCCESS);
            catalogStatus.setText(p.getName() + " x" + qty + " added to cart.");
        });
        right.add(spinner); right.add(add);

        row.add(info,  BorderLayout.CENTER);
        row.add(right, BorderLayout.EAST);
        return row;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SCREEN: VIEW CART
    // ─────────────────────────────────────────────────────────────────────────

    private JPanel cartItemsPanel = new JPanel();
    private JLabel lblCartTotal   = new JLabel();

    /**
     * Builds the cart screen showing items, tax, and total.
     *
     * @return the cart panel
     */
    private JPanel buildCart() {
        JPanel outer = new JPanel(new BorderLayout(0, 10));
        outer.setBackground(BG);
        outer.setBorder(BorderFactory.createEmptyBorder(16, 40, 16, 40));

        JLabel title = titleLabel("Your Cart");
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        cartItemsPanel.setBackground(BG);

        JScrollPane scroll = new JScrollPane(cartItemsPanel);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scroll.getViewport().setBackground(BG);

        lblCartTotal.setFont(FONT_BOLD);
        lblCartTotal.setForeground(TEXT);

        JButton btnBack = secondaryBtn("Back to Menu");
        btnBack.addActionListener(e -> show(CARD_CUSTOMER));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(BG);
        bottom.add(lblCartTotal);
        bottom.add(Box.createHorizontalStrut(20));
        bottom.add(btnBack);

        outer.add(title,  BorderLayout.NORTH);
        outer.add(scroll, BorderLayout.CENTER);
        outer.add(bottom, BorderLayout.SOUTH);
        return outer;
    }

    /** Refreshes the cart items panel with current cart contents. */
    private void refreshCart() {
        cartItemsPanel.removeAll();
        if (cart.isEmpty()) {
            JLabel empty = new JLabel("Your cart is empty.", SwingConstants.CENTER);
            empty.setFont(FONT_LABEL); empty.setForeground(MUTED);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            cartItemsPanel.add(Box.createVerticalStrut(30));
            cartItemsPanel.add(empty);
        } else {
            for (CartItem item : cart.getItems()) {
                JPanel row = new JPanel(new BorderLayout());
                row.setBackground(SURFACE);
                row.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                JLabel name = new JLabel(item.getProduct().getName() + "  x" + item.getQuantity());
                name.setFont(FONT_BOLD);
                JLabel sub = new JLabel(String.format("$%.2f", item.getSubtotal()));
                sub.setFont(FONT_LABEL);
                row.add(name, BorderLayout.WEST);
                row.add(sub,  BorderLayout.EAST);
                cartItemsPanel.add(row);
                cartItemsPanel.add(new JSeparator());
            }
        }
        lblCartTotal.setText(String.format("Subtotal: $%.2f  |  Tax: $%.2f  |  Total: $%.2f",
                cart.getSubtotal(), cart.getTax(), cart.getTotal()));
        cartItemsPanel.revalidate();
        cartItemsPanel.repaint();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SCREEN: MAKE ORDER
    // ─────────────────────────────────────────────────────────────────────────

    private JLabel     lblOrderSummary = new JLabel();
    private JLabel     orderStatus     = statusLabel();
    private JTextField tfNewCard       = new JTextField();
    private JLabel     lblNewCard      = new JLabel("New Credit Card # (if denied):");

    /**
     * Builds the Make Order screen with delivery selection and payment processing.
     *
     * @return the make order panel
     */
    private JPanel buildMakeOrder() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(SURFACE);
        inner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(28, 36, 28, 36)
        ));
        inner.setPreferredSize(new Dimension(380, 380));

        lblOrderSummary.setFont(FONT_LABEL);
        lblOrderSummary.setAlignmentX(Component.CENTER_ALIGNMENT);

        JRadioButton rbMail   = new JRadioButton("Mail  (+$3.00)");
        JRadioButton rbPickup = new JRadioButton("In-store Pickup  (Free)");
        rbMail.setFont(FONT_LABEL);   rbMail.setBackground(SURFACE);   rbMail.setSelected(true);
        rbPickup.setFont(FONT_LABEL); rbPickup.setBackground(SURFACE);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbMail); bg.add(rbPickup);

        tfNewCard.setFont(FONT_LABEL);
        tfNewCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        tfNewCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        lblNewCard.setFont(FONT_LABEL); lblNewCard.setForeground(DANGER);
        tfNewCard.setVisible(false); lblNewCard.setVisible(false);

        orderStatus.setFont(FONT_LABEL);

        JButton btnPlace = primaryBtn("Place Order");
        JButton btnBack  = secondaryBtn("Back to Menu");
        btnPlace.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnBack .setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        btnBack.addActionListener(e -> {
            orderStatus.setText(" ");
            tfNewCard.setText(""); tfNewCard.setVisible(false); lblNewCard.setVisible(false);
            show(CARD_CUSTOMER);
        });

        btnPlace.addActionListener(e -> {
            if (cart.isEmpty()) {
                orderStatus.setForeground(DANGER); orderStatus.setText("Cart is empty. Add items first."); return;
            }
            DeliveryMethod method = rbMail.isSelected() ? DeliveryMethod.MAIL : DeliveryMethod.IN_STORE_PICKUP;
            double total = cart.getTotal() + method.getFee();
            String ccNum = tfNewCard.getText().trim().isEmpty()
                    ? currentCustomer.getCreditCardNumber()
                    : tfNewCard.getText().trim();

            Bank   bank = new Bank();
            String auth = bank.processPayment(ccNum, total);

            if (auth == null) {
                orderStatus.setForeground(DANGER);
                orderStatus.setText("Payment denied. Enter a new credit card number below.");
                lblNewCard.setVisible(true); tfNewCard.setVisible(true);
                inner.revalidate(); inner.repaint(); return;
            }

            if (!tfNewCard.getText().trim().isEmpty())
                currentCustomer.setCreditCardNumber(tfNewCard.getText().trim());

            String orderID = "ORD" + String.format("%03d", guiOrders.size() + 1);
            Order order = new Order(orderID, currentCustomer.getCustomerID(),
                    new ArrayList<>(cart.getItems()), method, total, auth);
            guiOrders.add(order);
            cart.clear();
            tfNewCard.setText(""); tfNewCard.setVisible(false); lblNewCard.setVisible(false);
            orderStatus.setForeground(SUCCESS);
            orderStatus.setText(String.format("Order placed!  Auth #: %s   Total: $%.2f", auth, total));
        });

        inner.add(titleLabel("Make Order")); inner.add(gap(10));
        inner.add(lblOrderSummary);          inner.add(gap(14));
        inner.add(lbl("Select Delivery Method:")); inner.add(gap(4));
        inner.add(rbMail); inner.add(rbPickup);    inner.add(gap(12));
        inner.add(lblNewCard); inner.add(gap(3));
        inner.add(tfNewCard);  inner.add(gap(10));
        inner.add(orderStatus); inner.add(gap(8));
        inner.add(btnPlace);    inner.add(gap(8));
        inner.add(btnBack);

        outer.add(inner);
        return outer;
    }

    /** Updates the order summary label before showing the Make Order screen. */
    private void refreshOrder() {
        orderStatus.setText(" ");
        lblOrderSummary.setText(cart.isEmpty()
                ? "Your cart is empty."
                : String.format("Cart Total: $%.2f  |  Items: %d", cart.getTotal(), cart.getItems().size()));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SCREEN: VIEW ORDERS
    // ─────────────────────────────────────────────────────────────────────────

    private JPanel ordersListPanel = new JPanel();

    /**
     * Builds the View Orders screen showing all past orders for the logged-in customer.
     *
     * @return the view orders panel
     */
    private JPanel buildViewOrders() {
        JPanel outer = new JPanel(new BorderLayout(0, 10));
        outer.setBackground(BG);
        outer.setBorder(BorderFactory.createEmptyBorder(16, 40, 16, 40));

        JLabel title = titleLabel("My Orders");
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        ordersListPanel.setLayout(new BoxLayout(ordersListPanel, BoxLayout.Y_AXIS));
        ordersListPanel.setBackground(BG);

        JScrollPane scroll = new JScrollPane(ordersListPanel);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scroll.getViewport().setBackground(BG);

        JButton btnBack = secondaryBtn("Back to Menu");
        btnBack.addActionListener(e -> show(CARD_CUSTOMER));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBackground(BG);
        bottom.add(btnBack);

        outer.add(title,  BorderLayout.NORTH);
        outer.add(scroll, BorderLayout.CENTER);
        outer.add(bottom, BorderLayout.SOUTH);
        return outer;
    }

    /** Refreshes the orders list with the current customer's order history. */
    private void refreshOrders() {
        ordersListPanel.removeAll();
        List<Order> mine = new ArrayList<>();
        for (Order o : guiOrders)
            if (o.getCustomerID().equals(currentCustomer.getCustomerID())) mine.add(o);

        if (mine.isEmpty()) {
            JLabel empty = new JLabel("No orders yet.", SwingConstants.CENTER);
            empty.setFont(FONT_LABEL); empty.setForeground(MUTED);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            ordersListPanel.add(Box.createVerticalStrut(30));
            ordersListPanel.add(empty);
        } else {
            for (Order o : mine) {
                JPanel card = new JPanel();
                card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                card.setBackground(SURFACE);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        BorderFactory.createEmptyBorder(10, 14, 10, 14)
                ));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
                JLabel hdr = new JLabel("Order: " + o.getOrderID() + "  |  " + o.getOrderDate());
                hdr.setFont(FONT_BOLD);
                JLabel tot = new JLabel(String.format("Total: $%.2f   Auth #: %s", o.getTotal(), o.getAuthorizationNumber()));
                tot.setFont(FONT_LABEL); tot.setForeground(SUCCESS);
                card.add(hdr); card.add(gap(4)); card.add(tot); card.add(gap(4));
                for (CartItem item : o.getItems()) {
                    JLabel il = new JLabel("  • " + item.getProduct().getName()
                            + " x" + item.getQuantity()
                            + String.format("  $%.2f", item.getSubtotal()));
                    il.setFont(FONT_SMALL); il.setForeground(MUTED);
                    card.add(il);
                }
                ordersListPanel.add(card);
                ordersListPanel.add(Box.createVerticalStrut(8));
            }
        }
        ordersListPanel.revalidate();
        ordersListPanel.repaint();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ENTRY POINT
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Launches the Customer Order System GUI application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CosGUI::new);
    }
}