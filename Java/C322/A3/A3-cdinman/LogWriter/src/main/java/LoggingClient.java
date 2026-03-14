public class LoggingClient {
    public static void main(String[] args) {

        // Create multiple threads to simulate multithreading issue
        Runnable task = () -> {
            LogWriter writer = LogWriter.getInstance();
            System.out.println(writer.lockAndWrite());
            System.out.println(writer.unlock());
        };

        Thread[] threads = new Thread[20];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }

        // Join threads to ensure main thread waits for completion
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}