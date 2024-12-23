import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class BankAccount {
    // Thread-safe account balance using AtomicInteger
    private AtomicInteger balance;

    // Constructor to initialize balance
    public BankAccount(int initialBalance) {
        this.balance = new AtomicInteger(initialBalance);
    }

    // Deposit method: Adds money to the account
    public void deposit(int amount) {
        balance.addAndGet(amount); // Atomic operation to ensure thread safety
    }

    // Withdraw method: Deducts money from the account
    public boolean withdraw(int amount) {
        // Only allow withdrawal if sufficient balance exists
        int currentBalance;
        do {
            currentBalance = balance.get();
            if (currentBalance < amount) {
                return false; // Insufficient funds
            }
        } while (!balance.compareAndSet(currentBalance, currentBalance - amount)); // Atomic compare-and-set
        return true;
    }

    // Get the current balance
    public int getBalance() {
        return balance.get();
    }
}

class Client implements Runnable {
    private BankAccount account;
    private Random rand;

    // Constructor to initialize the bank account for the client
    public Client(BankAccount account) {
        this.account = account;
        this.rand = new Random();
    }

    @Override
    public void run() {
        // Simulate random transactions: deposit or withdrawal
        for (int i = 0; i < 10; i++) {
            int amount = rand.nextInt(100) + 1; // Random amount between 1 and 100
            if (rand.nextBoolean()) {
                account.deposit(amount); // Deposit random amount
                System.out.println(Thread.currentThread().getName() + " deposited: " + amount);
            } else {
                if (account.withdraw(amount)) {
                    System.out.println(Thread.currentThread().getName() + " withdrew: " + amount);
                } else {
                    System.out.println(Thread.currentThread().getName() + " failed to withdraw (Insufficient funds).");
                }
            }
            try {
                Thread.sleep(50); // Simulate some delay in operations
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Task4 {
    public static void main(String[] args) {
        // Create a bank account with an initial balance of 1000
        BankAccount account = new BankAccount(1000);

        // Create and start 5 client threads
        for (int i = 0; i < 5; i++) {
            Thread clientThread = new Thread(new Client(account), "Client-" + (i + 1));
            clientThread.start();
        }

        // Allow time for threads to complete their transactions
        try {
            Thread.sleep(5000); // Simulate time for transactions to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Output the final balance after all transactions
        System.out.println("Final account balance: " + account.getBalance());
    }
}
