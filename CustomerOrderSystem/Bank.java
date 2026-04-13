import java.util.Random;

/**
 * Simulates a bank in the Customer Order System.
 * Processes credit card payments and returns authorization numbers.
 */
public class Bank {

    private static final Random random = new Random();

    /**
     * Processes a payment for the given credit card and amount.
     * A card number that starts with "0" simulates a denial.
     * All other cards are approved and return a 4-digit auth number.
     *
     * @param creditCardNumber the customer's credit card number
     * @param amount           the total amount to charge
     * @return a 4-digit authorization number string, or null if denied
     */
    public String processPayment(String creditCardNumber, double amount) {
        if (creditCardNumber == null || creditCardNumber.startsWith("0")) {
            System.out.println("Bank: Payment DENIED. Invalid credit card or over credit limit.");
            return null;
        }
        int authNumber = 1000 + random.nextInt(9000);
        System.out.printf("Bank: Payment of $%.2f APPROVED. Auth #: %04d%n", amount, authNumber);
        return String.valueOf(authNumber);
    }
}