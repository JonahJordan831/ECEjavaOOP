import java.util.ArrayList;
import java.util.List;

/**
 * Represents the product catalog in the Customer Order System.
 * Stores all available products and provides browsing and search functionality.
 */
public class Catalog {

    private List<Product> products;

    /**
     * Constructs an empty Catalog and pre-loads sample products.
     */
    public Catalog() {
        this.products = new ArrayList<>();
        loadSampleProducts();
    }

    /**
     * Pre-loads a set of sample products into the catalog.
     */
    private void loadSampleProducts() {
        products.add(new Product("P001", "Laptop",     "15-inch laptop, 16GB RAM",    999.99, 849.99));
        products.add(new Product("P002", "Headphones", "Noise-cancelling headphones", 199.99, 149.99));
        products.add(new Product("P003", "USB-C Hub",  "7-in-1 USB-C hub",             49.99,  39.99));
        products.add(new Product("P004", "Webcam",     "1080p HD webcam",              79.99,  59.99));
        products.add(new Product("P005", "Keyboard",   "Mechanical keyboard, RGB",    129.99, 109.99));
    }

    /**
     * Returns all products in the catalog.
     *
     * @return list of all products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Finds and returns a product by its product ID.
     *
     * @param productID the ID of the product to find
     * @return the matching Product, or null if not found
     */
    public Product findProduct(String productID) {
        for (Product p : products) {
            if (p.getProductID().equalsIgnoreCase(productID)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Displays all products in the catalog with their details.
     */
    public void displayCatalog() {
        System.out.println("\n===== Product Catalog =====");
        for (Product p : products) {
            System.out.println(p);
        }
        System.out.println("===========================");
    }
}