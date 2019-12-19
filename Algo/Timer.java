public class Timer {

    private long startTime;

    public void start(String task) {
        System.out.printf("Performing %s...%n", task);
        startTime = System.nanoTime();
    }

    public void start() {
        start("Unnamed Task");
    }

    public void stop() {
        long timePassed = System.nanoTime() - startTime;

        if (startTime == 0) {
            System.out.println("Timer error: No task started.");
            return;
        }
        
        double timePassedMs = ((double) timePassed) / 1000000;
        System.out.printf("  Completed in %.3fms.%n", timePassedMs, timePassed);

        startTime = 0;
    }
}