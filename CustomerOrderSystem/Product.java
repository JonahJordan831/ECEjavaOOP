/**
 * Represents a product available in the Customer Order System catalog.
 * Stores product name, description, regular price, and sale price.
 */
public class Product {

    private String productID;
    private String name;
    private String description;
    private double regularPrice;
    private double salePrice;

    /**
     * Constructs a Product with the given details.
     *
     * @param productID    the unique product identifier
     * @param name         the product name
     * @param description  a brief description of the product
     * @param regularPrice the standard price of the product
     * @param salePrice    the discounted sale price (same as regularPrice if not on sale)
     */
    public Product(String productID, String name, String description,
                   double regularPrice, double salePrice) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
    }

    /**
     * Returns the product's unique identifier.
     *
     * @return the product ID
     */
    public String getProductID() {
        return productID;
    }

    /**
     * Returns the product name.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the product description.
     *
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the regular (non-sale) price of the product.
     *
     * @return the regular price
     */
    public double getRegularPrice() {
        return regularPrice;
    }

    /**
     * Returns the sale price of the product.
     *
     * @return the sale price
     */
    public double getSalePrice() {
        return salePrice;
    }

    /**
     * Returns a formatted string displaying the product details.
     *
     * @return product info string
     */
    @Override
    public String toString() {
        return String.format("[%s] %s - %s | Regular: $%.2f | Sale: $%.2f",
                productID, name, description, regularPrice, salePrice);
    }
}