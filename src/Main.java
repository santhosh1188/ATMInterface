//package atm;
	// Main class to run the ATM application
	public class Main {
	    public static void main(String[] args) {
	        // Create a User with PIN 1234
	        User user = new User(1234);

	        // Create the ATM object using the user
	        ATM atm = new ATM(user);

	        // Start the ATM session
	        atm.start();
	    }
	}


