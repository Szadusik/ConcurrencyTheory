import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor_Drukarek {
    final Lock lock = new ReentrantLock();
    final Condition taken  = lock.newCondition();
    private int notavailable;
    ArrayList<Boolean> prints = new ArrayList<>();
    public Monitor_Drukarek(int n){
        int i=0;
        this.notavailable = 0;
        while(i<n){
            prints.add(true);
            i++;
        }
    }

    public int reserve() throws InterruptedException { //take
        lock.lock();
        try{
            while(notavailable == prints.size()){
                taken.await();
            }
            int printer;
            printer = prints.indexOf(true);
            prints.set(printer,false);
            this.notavailable++;
            return printer;

        } finally {
            lock.unlock();
        }
    }

    public void release(int which){ //put
        lock.lock();
        try{
            prints.set(which,true);
            this.notavailable--;
            taken.signal();
        }
        finally {
            lock.unlock();
        }
    }
}
