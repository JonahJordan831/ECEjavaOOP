Customer Order System (COS)
============================

HOW TO RUN
----------
1. Open the project in IntelliJ IDEA
2. Ensure all .java files are in the src/ folder with no package declaration
3. Run Main.java (right-click Main.java -> Run 'Main.main()')

USE CASES DEMONSTRATED
----------------------
1. Create Account
   - Enter a unique customer ID
   - Password must be 6+ chars, 1 uppercase, 1 digit, 1 special character (!@#$%^&*)
   - Enter name, address, and credit card number (none can be empty)
   - Select a security question and provide an answer

2. Log On
   - Enter customer ID and password (max 3 attempts)
   - If ID does not exist, system returns to main menu
   - Answer the security question to complete login

3. Log Out
   - Select option 5 from the customer menu

4. Select Items
   - Browse the product catalog
   - Enter product ID and quantity to add items to the cart
   - Type 'done' when finished selecting

5. Make Order
   - Choose delivery method: Mail ($3.00) or In-store pickup (free)
   - Payment is processed through the bank
   - A card starting with '0' simulates a bank denial
   - On denial, enter a new card number or type 'exit' to cancel

6. View Orders
   - Displays all past orders for the logged-in customer

CLASSES
-------
- Main.java         : Console application entry point
- Account.java      : Stores credentials and security question
- Customer.java     : Stores customer personal info and login state
- Product.java      : Represents a catalog product
- Catalog.java      : Holds all available products
- CartItem.java     : Product + quantity pair
- Cart.java         : Manages cart items, tax, and totals
- DeliveryMethod.java: Enum for MAIL or IN_STORE_PICKUP
- Bank.java         : Simulates payment processing
- Order.java        : Stores finalized order data
- OrderManager.java : Places and retrieves orders

ASSUMPTIONS
-----------
- Data is stored in memory and resets when the program exits
- Tax rate is 8%
- A credit card number starting with '0' is treated as denied by the bank
- Security answer comparison is case-insensitive