import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages order placement and retrieval in the Customer Order System.
 * Coordinates between the Cart, Bank, and Order storage.
 */
public class OrderManager {

    private List<Order> orders;
    private Bank bank;
    private int orderCounter;

    /**
     * Constructs an OrderManager with an empty order list and a Bank instance.
     */
    public OrderManager() {
        this.orders = new ArrayList<>();
        this.bank = new Bank();
        this.orderCounter = 1;
    }

    /**
     * Places an order for the given customer using the items in their cart.
     * Handles bank denial by prompting for a new credit card (alternative sequence).
     * Returns null if the customer exits without completing payment.
     *
     * @param customer       the customer placing the order
     * @param cart           the cart containing selected items
     * @param deliveryMethod the chosen delivery method
     * @param scanner        the Scanner for reading user input
     * @return the completed Order, or null if the customer exited
     */
    public Order placeOrder(Customer customer, Cart cart,
                            DeliveryMethod deliveryMethod, Scanner scanner) {
        double total = cart.getTotal() + deliveryMethod.getFee();
        System.out.printf("%nDelivery: %s (Fee: $%.2f)%n", deliveryMethod, deliveryMethod.getFee());
        System.out.printf("Grand Total: $%.2f%n", total);

        String authNumber = bank.processPayment(customer.getCreditCardNumber(), total);

        // Alternative sequence: bank denies charge
        while (authNumber == null) {
            System.out.println("Enter a new credit card number (or type 'exit' to cancel):");
            String newCard = scanner.nextLine().trim();
            if (newCard.equalsIgnoreCase("exit")) {
                System.out.println("Order cancelled.");
                return null;
            }
            customer.setCreditCardNumber(newCard);
            authNumber = bank.processPayment(newCard, total);
        }

        String orderID = "ORD" + String.format("%03d", orderCounter++);
        Order order = new Order(orderID, customer.getCustomerID(),
                new ArrayList<>(cart.getItems()), deliveryMethod, total, authNumber);
        orders.add(order);
        cart.clear();
        order.displayOrder();
        return order;
    }

    /**
     * Returns all orders placed by a specific customer.
     *
     * @param customerID the customer ID to search for
     * @return list of orders belonging to that customer
     */
    public List<Order> getOrdersByCustomer(String customerID) {
        List<Order> result = new ArrayList<>();
        for (Order o : orders) {
            if (o.getCustomerID().equals(customerID)) {
                result.add(o);
            }
        }
        return result;
    }

    /**
     * Displays all orders for a given customer.
     * If no orders exist, informs the customer.
     *
     * @param customerID the customer whose orders to display
     */
    public void displayOrders(String customerID) {
        List<Order> customerOrders = getOrdersByCustomer(customerID);
        if (customerOrders.isEmpty()) {
            System.out.println("No orders found for customer: " + customerID);
            return;
        }
        for (Order o : customerOrders) {
            o.displayOrder();
        }
    }
}