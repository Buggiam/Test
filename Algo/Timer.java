public class Timer {

    private String task;
    private long startTime;

    public void start(String task) {
        this.task = task;
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
        System.out.printf("Performed [%s] in %.3fms.%n", task, timePassedMs, timePassed);

        startTime = 0;
    }
}