package ExceptionProblems;

import java.util.Scanner;
import java.util.InputMismatchException;

// Checked Exception: The user can "fix" this by depositing more money.
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Unchecked (Runtime) Exception: Represents an invalid operation or security violation.
class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String message) {
        super(message);
    }
}

class BankSystem {
    private double balance = 0;

    // In professional Java, we avoid printing inside logic classes.
    // We let the calling method handle the output.
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidTransactionException("Transaction Failed: Amount must be positive.");
        }
        this.balance += amount;
        System.out.println("Successfully deposited.");
        System.out.println("CURRENT BALANCE: " + getBalance());
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        // Order of checks: Fraud/Limits -> Input Integrity -> Business Logic
        if (amount > 10000) {
            throw new InvalidTransactionException("Security Alert: Single withdrawal limit is 10,000 EGP.");
        }
        if (amount <= 0) {
            throw new InvalidTransactionException("Transaction Failed: Amount must be positive.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Balance Error: Insufficient funds. Available: " + balance);
        }
        this.balance -= amount;
        System.out.println("Successfully withdrawn.");
        System.out.println("CURRENT BALANCE: " + getBalance());
    }

    public double getBalance() {
        return balance;
    }
}

public class ResilientBankProblem {
    // CORRECTED: Added 'public' and 'String[] args' so the program can actually run.
    public static void main(String[] args) {
        BankSystem myAccount = new BankSystem();
        Scanner in = new Scanner(System.in);
        String answer = "y";

        System.out.println("--- Welcome to the Resilient Bank System ---");

        do {
            try {
                System.out.println("\nSelect Operation: 1-Deposit | 2-Withdraw | 3-Check Balance");
                int option = in.nextInt();

                switch (option) {
                    case 1:
                        System.out.print("Enter deposit amount: ");
                        myAccount.deposit(in.nextDouble());
                        break;
                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        myAccount.withdraw(in.nextDouble());
                        break;
                    case 3:
                        System.out.println("CURRENT BALANCE: " + myAccount.getBalance());
                        break;
                    default:
                        System.out.println("Invalid option selected.");
                }

            } catch (InsufficientFundsException e) {
                // Handling expected business errors
                System.out.println("BUSINESS ERROR: " + e.getMessage());
            } catch (InvalidTransactionException e) {
                // Handling validation/security errors
                System.out.println("VALIDATION ERROR: " + e.getMessage());
            } catch (InputMismatchException e) {
                // IMPORTANT: Fixes the infinite loop if user enters text
                System.out.println("INPUT ERROR: Please enter numeric values only.");
                in.nextLine(); // Clears the "bad" input from the scanner buffer
            } catch (Exception e) {
                System.out.println("UNEXPECTED ERROR: " + e.getMessage());
            }

            System.out.print("\nWould you like another transaction? (y/n): ");
            answer = in.next();

        } while (answer.equalsIgnoreCase("y"));

        System.out.println("Thank you for using our system. Final Balance: " + myAccount.getBalance());
        in.close(); // Best practice: Close your resources
    }
}