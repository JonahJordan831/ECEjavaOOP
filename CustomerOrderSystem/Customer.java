/**
 * Represents a customer in the Customer Order System.
 * Stores personal information and manages login state.
 */
public class Customer {

    private String customerID;
    private String name;
    private String address;
    private String creditCardNumber;
    private boolean loggedIn;

    /**
     * Constructs a Customer with the given personal details.
     *
     * @param customerID       the unique customer identifier
     * @param name             the customer's full name
     * @param address          the customer's mailing address
     * @param creditCardNumber the customer's credit card number
     */
    public Customer(String customerID, String name,
                    String address, String creditCardNumber) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.creditCardNumber = creditCardNumber;
        this.loggedIn = false;
    }

    /**
     * Logs the customer into the system by setting the logged-in flag.
     */
    public void logIn() {
        this.loggedIn = true;
    }

    /**
     * Logs the customer out of the system by clearing the logged-in flag.
     */
    public void logOut() {
        this.loggedIn = false;
        System.out.println("You have been logged out successfully.");
    }

    /**
     * Returns whether the customer is currently logged in.
     *
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Returns the customer's unique ID.
     *
     * @return the customer ID
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * Returns the customer's name.
     *
     * @return the customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the customer's address.
     *
     * @return the customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the customer's credit card number.
     *
     * @return the credit card number
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Updates the customer's credit card number.
     * Called when the bank denies a charge and the customer provides a new card.
     *
     * @param creditCardNumber the new credit card number
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}