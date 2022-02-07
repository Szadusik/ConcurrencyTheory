package SecondTask;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Unfair implements IQueue {
    private int buffer = 0;
    private final int size;
    private final Lock lock = new ReentrantLock();
    private final Condition putting = this.lock.newCondition();
    private final Condition taking = this.lock.newCondition();

    public Unfair(int size) {
        this.size = size;
    }

    public void put(int n) throws InterruptedException {
        this.lock.lock();
        try {
            while (this.buffer + n > this.size) {
                this.putting.await();
            }
            this.buffer += n;
            //System.out.format("Putting %d, buffer: %d\n", n, this.buffer);
            this.taking.signalAll();
        }
        finally {
            this.lock.unlock();
        }
    }

    public void take(int n) throws InterruptedException {
        this.lock.lock();
        try {
            while (n > this.buffer) {
                this.taking.await();
            }
            this.buffer -= n;
            //System.out.format("Taking %d, buffer: %d\n", n, this.buffer);
            this.putting.signalAll();
        }
        finally {
            this.lock.unlock();
        }
    }
}
