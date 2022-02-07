import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Kelner {
    private final Lock lock = new ReentrantLock();
    private final Condition onTable = this.lock.newCondition();
    private final Map<Integer, Condition> awaiting;
    private int placesOccupied;

    public Kelner() {
        this.awaiting = new HashMap<>();
        this.placesOccupied = 0;
    }

    public void chce_stolik(int pairId) throws InterruptedException {
        this.lock.lock();
        try {
            while (this.placesOccupied > 0) {
                this.onTable.await();
            }
            Condition partnerCondition = this.awaiting.remove(pairId);
            if (partnerCondition == null) {
                Condition myselfCondition = this.lock.newCondition();
                this.awaiting.put(pairId, myselfCondition);
                while (this.awaiting.containsKey(pairId)) {
                    myselfCondition.await();
                }
            }
            else {
                this.placesOccupied = 2;
                partnerCondition.signal();
            }
        }
        finally {
            this.lock.unlock();
        }
    }

    public void zwalniam() {
        this.lock.lock();
        try {
            if (--this.placesOccupied == 0) {
                this.onTable.signalAll();
            }
        }
        finally {
            this.lock.unlock();
        }
    }
}
