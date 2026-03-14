public class LogWriter {

    private static LogWriter instance;

    // Constructor
    private LogWriter() {}

    public static synchronized LogWriter getInstance() {
        if (instance == null){
            instance = new LogWriter();
        }
        return instance;
    }
    // other useful methods here
    public synchronized String lockAndWrite() {

        try {
            Thread.sleep(500); // Pause for 2 seconds
        } catch (InterruptedException e) {
            // Handle the exception if another thread interrupts this thread while sleeping
            e.printStackTrace();
        }
        return "Writing ..";
    }
    // other useful methods here
    public synchronized String unlock() {
        return "Unlocked";
    }
}