//package atm;
import java.io.*;
// This class stores and manages user data such as PIN and balance
public class User {
	    private int pin;
	    private double balance;
	    private final String BALANCE_FILE = "balance.txt"; // File to store balance persistently

	    // Constructor to initialize user with a PIN and load saved balance
	    public User(int pin) {
	        this.pin = pin;
	        this.balance = loadBalanceFromFile(); // Load saved balance or default
	    }

	    // Check if entered PIN is correct
	    public boolean validatePin(int inputPin) {
	        return this.pin == inputPin;
	    }

	    // Return current balance
	    public double getBalance() {
	        return balance;
	    }

	    // Deposit money into account
	    public void deposit(double amount) {
	        if (amount > 0) {
	            balance += amount;
	            saveBalanceToFile(); // Save updated balance to file
	            System.out.println("Amount deposited successfully!");
	        } else {
	            System.out.println("Invalid deposit amount.");
	        }
	    }

	    // Withdraw money from account
	    public void withdraw(double amount) {
	        if (amount > 0 && amount <= balance) {
	            balance -= amount;
	            saveBalanceToFile(); // Save updated balance to file
	            System.out.println("Withdrawal successful!");
	        } else {
	            System.out.println("Insufficient balance or invalid amount.");
	        }
	    }

	    // Load balance from a file, return 1000 if file doesn't exist or error occurs
	    private double loadBalanceFromFile() {
	        try (BufferedReader reader = new BufferedReader(new FileReader(BALANCE_FILE))) {
	            String line = reader.readLine();
	            return Double.parseDouble(line);
	        } catch (IOException | NumberFormatException e) {
	            return 1000.0; // Default balance if file not found or error occurs
	        }
	    }

	    // Save the current balance to the file
	    private void saveBalanceToFile() {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BALANCE_FILE))) {
	            writer.write(String.valueOf(balance));
	        } catch (IOException e) {
	            System.out.println("Error saving balance.");
	        }
	    }
}
