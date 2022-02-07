import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Format : [Number of threads, Tasks amount, Taken time]");
        int[] threads = new int[]{1,4,8};
        int[] slicesAmount = new int[]{0,1,10};

        for (int threadCount : threads) {
            for (int taskMultiplier : slicesAmount) {
                int taskCount = threadCount * taskMultiplier;
                for (int i = 0; i < 10; i++) {
                    long time = new MandelbrotChoice(threadCount,taskMultiplier).getExecutionTime();
                    System.out.format("%d;%d;%d\n", threadCount, taskCount, time);
                }
            }
        }
    }
}