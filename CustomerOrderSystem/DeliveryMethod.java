/**
 * Enum representing the available delivery methods for an order.
 * MAIL charges a flat fee; IN_STORE_PICKUP is free.
 */
public enum DeliveryMethod {

    /** Mail delivery with a $3.00 fee. */
    MAIL(3.00),

    /** In-store pickup, no charge. */
    IN_STORE_PICKUP(0.00);

    private final double fee;

    /**
     * Constructs a DeliveryMethod with the given fee.
     *
     * @param fee the delivery fee in dollars
     */
    DeliveryMethod(double fee) {
        this.fee = fee;
    }

    /**
     * Returns the delivery fee for this method.
     *
     * @return fee in dollars
     */
    public double getFee() {
        return fee;
    }
}