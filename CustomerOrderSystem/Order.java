import java.util.Date;
import java.util.List;

/**
 * Represents a finalized customer order in the Customer Order System.
 * Stores order date, items, delivery method, total, and bank authorization.
 */
public class Order {

    private String orderID;
    private Date orderDate;
    private String customerID;
    private List<CartItem> items;
    private DeliveryMethod deliveryMethod;
    private double total;
    private String authorizationNumber;

    /**
     * Constructs an Order with all required order details.
     *
     * @param orderID             the unique order identifier
     * @param customerID          the ID of the customer placing the order
     * @param items               the list of items in the order
     * @param deliveryMethod      the chosen delivery method
     * @param total               the total cost including delivery and tax
     * @param authorizationNumber the 4-digit bank authorization number
     */
    public Order(String orderID, String customerID, List<CartItem> items,
                 DeliveryMethod deliveryMethod, double total, String authorizationNumber) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.items = items;
        this.deliveryMethod = deliveryMethod;
        this.total = total;
        this.authorizationNumber = authorizationNumber;
        this.orderDate = new Date();
    }

    /**
     * Returns the order ID.
     *
     * @return the order ID
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * Returns the date the order was placed.
     *
     * @return the order date
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * Returns the customer ID associated with this order.
     *
     * @return the customer ID
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * Returns the list of items in this order.
     *
     * @return list of cart items
     */
    public List<CartItem> getItems() {
        return items;
    }

    /**
     * Returns the total cost of this order.
     *
     * @return the total cost
     */
    public double getTotal() {
        return total;
    }

    /**
     * Returns the bank authorization number for this order.
     *
     * @return the authorization number
     */
    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    /**
     * Displays the full order summary to the console.
     */
    public void displayOrder() {
        System.out.println("\n===== Order Confirmation =====");
        System.out.println("Order ID:    " + orderID);
        System.out.println("Date:        " + orderDate);
        System.out.println("Customer ID: " + customerID);
        System.out.println("Delivery:    " + deliveryMethod);
        System.out.println("Items:");
        for (CartItem item : items) {
            System.out.println("  " + item);
        }
        System.out.printf("Total:       $%.2f%n", total);
        System.out.println("Auth #:      " + authorizationNumber);
        System.out.println("==============================");
    }
}