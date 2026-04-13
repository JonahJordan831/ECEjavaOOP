import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shopping cart in the Customer Order System.
 * Manages cart items, calculates tax, and computes order totals.
 */
public class Cart {

    /** Sales tax rate (8%). */
    private static final double TAX_RATE = 0.08;

    private List<CartItem> items;

    /**
     * Constructs an empty Cart.
     */
    public Cart() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds a product and quantity to the cart.
     * If the product already exists, its quantity is updated.
     *
     * @param product  the product to add
     * @param quantity the number of units to add
     */
    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getProductID().equals(product.getProductID())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    /**
     * Returns all items currently in the cart.
     *
     * @return list of cart items
     */
    public List<CartItem> getItems() {
        return items;
    }

    /**
     * Returns whether the cart is empty.
     *
     * @return true if no items are in the cart
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Calculates the subtotal of all items before tax.
     *
     * @return the pre-tax subtotal
     */
    public double getSubtotal() {
        double subtotal = 0;
        for (CartItem item : items) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    /**
     * Calculates the tax amount based on the subtotal.
     *
     * @return the tax amount
     */
    public double getTax() {
        return getSubtotal() * TAX_RATE;
    }

    /**
     * Calculates the total cost including tax.
     *
     * @return the total cost
     */
    public double getTotal() {
        return getSubtotal() + getTax();
    }

    /**
     * Displays all items in the cart along with tax and total.
     */
    public void displayCart() {
        System.out.println("\n===== Your Cart =====");
        for (CartItem item : items) {
            System.out.println(item);
        }
        System.out.printf("Subtotal: $%.2f%n", getSubtotal());
        System.out.printf("Tax (8%%): $%.2f%n", getTax());
        System.out.printf("Total:    $%.2f%n", getTotal());
        System.out.println("=====================");
    }

    /**
     * Clears all items from the cart.
     */
    public void clear() {
        items.clear();
    }
}
