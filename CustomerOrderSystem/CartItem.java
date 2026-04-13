/**
 * Represents a single item in a shopping cart.
 * Associates a Product with a specified quantity.
 */
public class CartItem {

    private Product product;
    private int quantity;

    /**
     * Constructs a CartItem with the given product and quantity.
     *
     * @param product  the product being added to the cart
     * @param quantity the number of units of the product
     */
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Returns the product associated with this cart item.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Returns the quantity of this item in the cart.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets a new quantity for this cart item.
     *
     * @param quantity the updated quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Calculates and returns the subtotal for this item (sale price * quantity).
     *
     * @return the subtotal cost for this cart item
     */
    public double getSubtotal() {
        return product.getSalePrice() * quantity;
    }

    /**
     * Returns a formatted string displaying this cart item's details.
     *
     * @return cart item info string
     */
    @Override
    public String toString() {
        return String.format("%-20s x%d  @ $%.2f each = $%.2f",
                product.getName(), quantity, product.getSalePrice(), getSubtotal());
    }
}