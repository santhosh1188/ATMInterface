package atm;

import java.io.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ATM {
    private double balance;
    private int pin;
    private final String PIN_FILE = "pin.txt";
    private final String BALANCE_FILE = "balance.txt";
    Scanner scanner = new Scanner(System.in);

    public void start() {
        // Load PIN from file. If not found, exit
        if (!loadPin()) {
            System.out.println("PIN file not found or invalid. Access denied.");
            return;
        }

        System.out.print("Enter your PIN: ");
        int enteredPin = scanner.nextInt();

        if (enteredPin != pin) {
            System.out.println("Invalid PIN. Exiting...");
            return;
        }

        // Load balance
        loadBalance();

        while (true) {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Change PIN");
            System.out.println("5. Show Transaction History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    changePin();
                    break;
                case 5:
                	showTransactionHistory();
                	break;
                case 6:
                    saveBalance();
                    System.out.println("Thank you! Goodbye.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No transaction history found.");
        }
    }

    private boolean loadPin() {
        try (BufferedReader br = new BufferedReader(new FileReader(PIN_FILE))) {
            pin = Integer.parseInt(br.readLine());
            return true;
        } catch (IOException | NumberFormatException e) {
            return false;
        }
    }
    
    private void logTransaction(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            bw.write("[" + now.format(formatter) + "] " + message);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
        }
    }


    private void savePin() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PIN_FILE))) {
            bw.write(String.valueOf(pin));
        } catch (IOException e) {
            System.out.println("Error saving PIN: " + e.getMessage());
        }
    }

    private void loadBalance() {
        try (BufferedReader br = new BufferedReader(new FileReader(BALANCE_FILE))) {
            balance = Double.parseDouble(br.readLine());
        } catch (IOException | NumberFormatException e) {
            balance = 0.0;  // Default balance if file not found or invalid
        }
    }

    private void saveBalance() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BALANCE_FILE))) {
            bw.write(String.valueOf(balance));
        } catch (IOException e) {
            System.out.println("Error saving balance: " + e.getMessage());
        }
    }

    private void checkBalance() {
        System.out.println("Your Balance: ₹" + balance);
        logTransaction("Checked balance: ₹" + balance);
    }

    private void deposit() {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited ₹" + amount);
            logTransaction("Deposited ₹" + amount + " | New balance: ₹" + balance);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    private void withdraw() {
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn ₹" + amount);
            logTransaction("Withdrew ₹" + amount + " | New balance: ₹" + balance);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    private void changePin() {
        System.out.println("--- Change Your PIN ---");
        System.out.print("Enter current PIN: ");
        int currentPin = scanner.nextInt();

        if (currentPin != pin) {
            System.out.println("Incorrect current PIN.");
            return;
        }

        while (true) {
            System.out.print("Enter new 4-digit PIN: ");
            int newPin = scanner.nextInt();
            System.out.print("Confirm new PIN: ");
            int confirmPin = scanner.nextInt();

            if (newPin == confirmPin && String.valueOf(newPin).length() == 4) {
                pin = newPin;
                savePin();
                System.out.println("PIN changed successfully.");
                logTransaction("PIN changed successfully.");
                break;
            } else {
                System.out.println("PINs do not match or invalid. Try again.");
            }
        }
    }
}
