package SecondTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n=100000;
        //Task2.FairQueue fair = new Task2.FairQueue(n);
        Unfair unfair = new Unfair(n);
        testQueue(unfair,n);
    }

    public static void testQueue(IQueue queue, int n) throws InterruptedException{
        Random rand = new Random();
        List<Thread> threads = new ArrayList<>();
        for (int j = 0; j < 1000; j++) {
            int finalPortion = rand.nextInt(n/2 - 1) + 1;
            threads.add(new Process(false,finalPortion,queue));
            threads.add(new Process(true,finalPortion,queue));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }
}
