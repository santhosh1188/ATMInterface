package atm;

import java.util.Scanner;

	// This class simulates the ATM interface: PIN entry, menu, deposit, withdraw
	public class ATM {
	    private User user;            // Reference to the User object
	    private Scanner scanner;      // For reading user input

	    // Constructor initializes the ATM with a user
	    public ATM(User user) {
	        this.user = user;
	        scanner = new Scanner(System.in);
	    }

	    // Start the ATM session
	    public void start() {
	        System.out.print("Enter your PIN: ");
	        int enteredPin = scanner.nextInt(); // Read user-entered PIN

	        // Validate PIN
	        if (!user.validatePin(enteredPin)) {
	            System.out.println("Invalid PIN. Exiting...");
	            return;
	        }

	        // Main ATM menu loop
	        while (true) {
	            System.out.println("\n--- ATM Menu ---");
	            System.out.println("1. Check Balance");
	            System.out.println("2. Deposit");
	            System.out.println("3. Withdraw");
	            System.out.println("4. Exit");
	            System.out.print("Choose an option: ");
	            int option = scanner.nextInt(); // Read user’s menu option

	            switch (option) {
	                case 1:
	                    // Show balance
	                    System.out.println("Your Balance: ₹ " + user.getBalance());
	                    break;
	                case 2:
	                    // Deposit money
	                    System.out.print("Enter deposit amount: ");
	                    double depositAmount = scanner.nextDouble();
	                    user.deposit(depositAmount);
	                    break;
	                case 3:
	                    // Withdraw money
	                    System.out.print("Enter withdrawal amount: ");
	                    double withdrawAmount = scanner.nextDouble();
	                    user.withdraw(withdrawAmount);
	                    break;
	                case 4:
	                    // Exit program
	                    System.out.println("Thank you for using the ATM. Goodbye!");
	                    return;
	                default:
	                    // Handle invalid menu options
	                    System.out.println("Invalid option. Try again.");
	            }
	        }
	    }
}
