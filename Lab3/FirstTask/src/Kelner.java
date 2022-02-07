import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Kelner {
    final Lock lock = new ReentrantLock();
    final Condition tableState = lock.newCondition();
    private final Boolean[] notWaiting;
    private final Condition[] pairs;
    private int onTable;
    public Kelner(int n){
        this.onTable = 0;
        pairs = new Condition[n];
        notWaiting = new Boolean[n];
        for(int i = 0; i< n; i++){
            pairs[i] = lock.newCondition();
            notWaiting[i] = false;
        }
    }
    public void chceStolik(int j) throws InterruptedException {
        lock.lock();
        try{
            while (!this.notWaiting[j]){
                this.pairs[j].await();
                this.notWaiting[j] = true;
            }
            while(onTable !=0){
                this.tableState.await();
            }
            this.onTable = 2;
            this.pairs[j].await();

        } finally {
            lock.unlock();
        }
    }

    public void zwalniam(){
        lock.lock();
        try{
            this.onTable-=1;
            while(this.onTable==0){
                tableState.await();
            }
            for(Boolean el : notWaiting)
                el = false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
