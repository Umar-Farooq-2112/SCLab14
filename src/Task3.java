import java.util.concurrent.CopyOnWriteArrayList;

public class Task3 {

    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> sharedList = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 5; i++) {
            Thread writer = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        sharedList.add((int) (Math.random() * 100));
                        System.out.println(Thread.currentThread().getName() + " added value to the list.");
                    }
                }
            });
            writer.start();
        }

        for (int i = 0; i < 5; i++) {
            Thread reader = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        if (!sharedList.isEmpty()) {
                            System.out.println(Thread.currentThread().getName() + " read: " + sharedList.get((int) (Math.random() * sharedList.size())));
                        }
                    }
                }
            });
            reader.start();
        }

        try {
            Thread.sleep(5000); // 5 seconds for testing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final list: " + sharedList);
    }
}
