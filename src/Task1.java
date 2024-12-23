class SharedCounter {
    private int count = 1;
    private final int max = 10;

    public synchronized void printNumber() {
        while (count <= max) {
            System.out.println("Number: " + count);
            count++;  // Increment the count after printing the number
            notify();  // Notify the other thread
            try {
                if (count <= max) {
                    wait();  // Wait for the square to be printed
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void printSquare() {
        while (count <= max) {
            System.out.println("Square: " + (count - 1) * (count - 1));
            notify();  // Notify the other thread
            try {
                if (count <= max) {
                    wait();  // Wait for the number to be printed
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class PrintNumbersThread extends Thread {
    private SharedCounter sharedCounter;

    public PrintNumbersThread(SharedCounter sharedCounter) {
        this.sharedCounter = sharedCounter;
    }

    @Override
    public void run() {
        sharedCounter.printNumber();
    }
}

class PrintSquaresThread extends Thread {
    private SharedCounter sharedCounter;

    public PrintSquaresThread(SharedCounter sharedCounter) {
        this.sharedCounter = sharedCounter;
    }

    @Override
    public void run() {
        sharedCounter.printSquare();
    }
}

public class Task1 {
    public static void main(String[] args) {
        SharedCounter sharedCounter = new SharedCounter();

        PrintNumbersThread numbersThread = new PrintNumbersThread(sharedCounter);
        PrintSquaresThread squaresThread = new PrintSquaresThread(sharedCounter);

        numbersThread.start();
        squaresThread.start();

        try {
            numbersThread.join();
            squaresThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
