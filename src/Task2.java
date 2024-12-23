public class Task2 {

    private static int counter = 0;

    private synchronized static void incrementCounter() {
        counter++;
    }

    public static void main(String[] args) {
        // Create three threads
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    incrementCounter(); // Increment counter 100 times
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    incrementCounter(); // Increment counter 100 times
                }
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    incrementCounter(); // Increment counter 100 times
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final value of counter: " + counter);
    }
}
