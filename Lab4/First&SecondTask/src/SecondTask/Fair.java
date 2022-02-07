package SecondTask;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Fair implements IQueue {
    private int buffer = 0;
    private final int size;

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition puttingLeader = this.lock.newCondition();
    private final Condition putting = this.lock.newCondition();
    private final Condition takingLeader = this.lock.newCondition();
    private final Condition taking = this.lock.newCondition();

    public Fair(int size) {
        this.size = size;
    }

    public void put(int n) throws InterruptedException {
        this.lock.lock();
        try {
            while (this.lock.hasWaiters(this.puttingLeader)) {
                this.putting.await();
            }
            while (this.buffer + n > this.size) {
                this.puttingLeader.await();
            }

            this.buffer += n;
            //System.out.format("Putting portion : %d, State of buffer: %d\n", n, this.buffer);
            this.putting.signal();
            this.takingLeader.signal();
        }
        finally {
            this.lock.unlock();
        }
    }

    public void take(int n) throws InterruptedException {
        this.lock.lock();
        try {
            while (this.lock.hasWaiters(this.takingLeader)) {
                this.taking.await();
            }
            while (n > this.buffer) {
                this.takingLeader.await();
            }

            this.buffer -= n;
            //System.out.format("Taking portion : %d, State of buffer: %d\n", n, this.buffer);

            this.taking.signal();
            this.puttingLeader.signal();
        }
        finally {
            this.lock.unlock();
        }
    }
}
