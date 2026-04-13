import java.util.List;
import java.util.Scanner;

/**
 * Main console application for the Customer Order System (COS).
 * Demonstrates all use cases: Create Account, Log On, Log Out,
 * Select Items, Make Order, and View Orders.
 */
public class Main {

    private static List<Account> accounts = new java.util.ArrayList<>();
    private static List<Customer> customers = new java.util.ArrayList<>();
    private static Catalog catalog = new Catalog();
    private static OrderManager orderManager = new OrderManager();
    private static Scanner scanner = new Scanner(System.in);

    /** Security questions available to the customer during account creation. */
    private static final String[] SECURITY_QUESTIONS = {
            "1. What is your mother's maiden name?",
            "2. What was the name of your first pet?",
            "3. What city were you born in?"
    };

    /**
     * Entry point for the Customer Order System console application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("  Welcome to the Customer Order System");
        System.out.println("======================================");

        boolean running = true;
        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("  1. Create Account");
            System.out.println("  2. Log On");
            System.out.println("  3. Exit");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": createAccount(); break;
                case "2": logOn();         break;
                case "3": running = false; System.out.println("Goodbye!"); break;
                default:  System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    /**
     * Handles the Create Account use case.
     * Validates ID uniqueness, password strength, required fields,
     * and security question selection.
     */
    private static void createAccount() {
        System.out.println("\n--- Create Account ---");

        // Step 1-3: Get and validate customer ID
        String customerID;
        while (true) {
            System.out.print("Enter a customer ID: ");
            customerID = scanner.nextLine().trim();
            if (findAccount(customerID) != null) {
                System.out.println("Error: That ID already exists. Please choose a different ID.");
            } else {
                break;
            }
        }

        // Step 4-5: Get and validate password
        String password;
        while (true) {
            System.out.print("Enter a password (min 6 chars, 1 uppercase, 1 digit, 1 special !@#$%^&*): ");
            password = scanner.nextLine().trim();
            if (Account.isPasswordValid(password)) {
                break;
            } else {
                System.out.println("Invalid password. Please try again.");
            }
        }

        // Step 6-7: Get name, address, credit card
        String name, address, creditCard;
        while (true) {
            System.out.print("Enter your full name: ");
            name = scanner.nextLine().trim();
            System.out.print("Enter your address: ");
            address = scanner.nextLine().trim();
            System.out.print("Enter your credit card number: ");
            creditCard = scanner.nextLine().trim();
            if (name.isEmpty() || address.isEmpty() || creditCard.isEmpty()) {
                System.out.println("Name, address, and credit card cannot be empty. Please re-enter.");
            } else {
                break;
            }
        }

        // Step 8-10: Security question
        System.out.println("\nSelect a security question:");
        for (String q : SECURITY_QUESTIONS) {
            System.out.println("  " + q);
        }
        String questionChoice;
        String selectedQuestion;
        while (true) {
            System.out.print("Enter 1, 2, or 3: ");
            questionChoice = scanner.nextLine().trim();
            if (questionChoice.equals("1") || questionChoice.equals("2") || questionChoice.equals("3")) {
                selectedQuestion = SECURITY_QUESTIONS[Integer.parseInt(questionChoice) - 1];
                break;
            }
            System.out.println("Invalid choice. Please enter 1, 2, or 3.");
        }
        System.out.print("Enter your answer: ");
        String securityAnswer = scanner.nextLine().trim();

        // Store account and customer
        accounts.add(new Account(customerID, password, selectedQuestion, securityAnswer));
        customers.add(new Customer(customerID, name, address, creditCard));
        System.out.println("\nAccount created successfully! Welcome, " + name + "!");
    }

    /**
     * Handles the Log On use case.
     * Allows up to 3 attempts for ID/password, then verifies security answer.
     */
    private static void logOn() {
        System.out.println("\n--- Log On ---");

        // Step 1-3: Validate ID and password (max 3 attempts)
        Account account = null;
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter customer ID: ");
            String id = scanner.nextLine().trim();
            account = findAccount(id);

            if (account == null) {
                System.out.println("No account found. Returning to main menu.");
                return;
            }

            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();

            if (account.validatePassword(password)) {
                break;
            } else {
                attempts++;
                if (attempts < 3) {
                    System.out.println("Incorrect password. " + (3 - attempts) + " attempt(s) remaining.");
                } else {
                    System.out.println("Too many failed attempts. Returning to main menu.");
                    return;
                }
            }
        }

        // Step 4-5: Verify security answer
        System.out.println("Security Question: " + account.getSecurityQuestion());
        System.out.print("Your answer: ");
        String answer = scanner.nextLine().trim();
        if (!account.validateSecurityAnswer(answer)) {
            System.out.println("Incorrect answer. Access denied.");
            return;
        }

        // Logged in — find the customer object
        Customer customer = findCustomer(account.getCustomerID());
        customer.logIn();
        System.out.println("\nWelcome, " + customer.getName() + "!");

        customerMenu(customer);
    }

    /**
     * Displays the logged-in customer menu and routes to use cases.
     *
     * @param customer the currently logged-in customer
     */
    private static void customerMenu(Customer customer) {
        Cart cart = new Cart();
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\nCustomer Menu:");
            System.out.println("  1. Browse Catalog / Select Items");
            System.out.println("  2. View Cart");
            System.out.println("  3. Make Order");
            System.out.println("  4. View My Orders");
            System.out.println("  5. Log Out");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": selectItems(cart);               break;
                case "2": cart.displayCart();              break;
                case "3": makeOrder(customer, cart);       break;
                case "4": orderManager.displayOrders(customer.getCustomerID()); break;
                case "5":
                    customer.logOut();
                    loggedIn = false;
                    break;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Handles the Select Items use case.
     * Displays catalog and adds chosen products to the cart.
     *
     * @param cart the customer's current shopping cart
     */
    private static void selectItems(Cart cart) {
        catalog.displayCatalog();

        while (true) {
            System.out.print("\nEnter product ID to add (or 'done' to finish): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;

            Product product = catalog.findProduct(input);
            if (product == null) {
                System.out.println("Product not found. Please try again.");
                continue;
            }

            System.out.print("Enter quantity: ");
            String qtyInput = scanner.nextLine().trim();
            try {
                int qty = Integer.parseInt(qtyInput);
                if (qty <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                    continue;
                }
                cart.addItem(product, qty);
                System.out.println(product.getName() + " x" + qty + " added to cart.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Please enter a number.");
            }
        }
        cart.displayCart();
    }

    /**
     * Handles the Make Order use case.
     * Selects delivery method and processes payment through the bank.
     *
     * @param customer the customer placing the order
     * @param cart     the cart with selected items
     */
    private static void makeOrder(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Please select items first.");
            return;
        }

        // Step 2-3: Choose delivery method
        System.out.println("\nSelect a delivery method:");
        System.out.println("  1. Mail ($3.00 fee)");
        System.out.println("  2. In-store pickup (free)");
        System.out.print("Enter 1 or 2 (or 'exit' to cancel): ");
        String choice = scanner.nextLine().trim();

        if (choice.equalsIgnoreCase("exit")) {
            System.out.println("Order cancelled.");
            return;
        }

        DeliveryMethod method;
        if (choice.equals("1")) {
            method = DeliveryMethod.MAIL;
        } else if (choice.equals("2")) {
            method = DeliveryMethod.IN_STORE_PICKUP;
        } else {
            System.out.println("Invalid choice. Returning to menu.");
            return;
        }

        orderManager.placeOrder(customer, cart, method, scanner);
    }

    /**
     * Finds an Account by customer ID.
     *
     * @param customerID the ID to search for
     * @return the matching Account, or null if not found
     */
    private static Account findAccount(String customerID) {
        for (Account a : accounts) {
            if (a.getCustomerID().equals(customerID)) return a;
        }
        return null;
    }

    /**
     * Finds a Customer by customer ID.
     *
     * @param customerID the ID to search for
     * @return the matching Customer, or null if not found
     */
    private static Customer findCustomer(String customerID) {
        for (Customer c : customers) {
            if (c.getCustomerID().equals(customerID)) return c;
        }
        return null;
    }
}