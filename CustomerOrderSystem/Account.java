import java.util.regex.Pattern;

/**
 * Represents a customer account storing credentials and security question data.
 * Handles validation of customer ID uniqueness and password strength rules.
 */
public class Account {

    /** Minimum password length. */
    private static final int MIN_PASSWORD_LENGTH = 6;

    /**
     * Password must contain at least one uppercase letter, one digit,
     * and one special character from: ! @ # $ % ^ & *
     */
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{" + MIN_PASSWORD_LENGTH + ",}$");

    private String customerID;
    private String password;
    private String securityQuestion;
    private String securityAnswer;

    /**
     * Constructs an Account with the given credentials and security info.
     *
     * @param customerID       the unique customer identifier
     * @param password         the customer's password
     * @param securityQuestion the security question selected by the customer
     * @param securityAnswer   the answer to the security question
     */
    public Account(String customerID, String password,
                   String securityQuestion, String securityAnswer) {
        this.customerID = customerID;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    /**
     * Validates whether the given password meets complexity requirements.
     * Must be at least 6 characters, contain one uppercase letter,
     * one digit, and one special character (!@#$%^&*).
     *
     * @param password the password string to validate
     * @return true if the password is valid, false otherwise
     */
    public static boolean isPasswordValid(String password) {
        if (password == null) return false;
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * Checks whether the provided password matches this account's stored password.
     *
     * @param password the password to check
     * @return true if passwords match, false otherwise
     */
    public boolean validatePassword(String password) {
        return this.password != null && this.password.equals(password);
    }

    /**
     * Checks whether the provided security answer matches the stored answer.
     *
     * @param answer the answer provided by the customer
     * @return true if the answer matches, false otherwise
     */
    public boolean validateSecurityAnswer(String answer) {
        return this.securityAnswer != null && this.securityAnswer.equalsIgnoreCase(answer);
    }

    /**
     * Returns the customer ID for this account.
     *
     * @return the customer ID
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * Returns the security question for this account.
     *
     * @return the security question string
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * Updates the security question and answer for this account.
     *
     * @param question the new security question
     * @param answer   the answer to the new security question
     */
    public void setSecurityQuestion(String question, String answer) {
        this.securityQuestion = question;
        this.securityAnswer = answer;
    }

    /**
     * Updates the password stored in this account.
     *
     * @param newPassword the new password to store
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}