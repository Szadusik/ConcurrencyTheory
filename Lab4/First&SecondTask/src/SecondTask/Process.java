package SecondTask;

import java.util.concurrent.atomic.AtomicLong;

public class Process extends Thread{
    private final int portion;
    private final boolean takes;
    public IQueue buffer;
    public Process(boolean takes, int portion, IQueue buffer){
        this.takes = takes;
        this.portion = portion;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        AtomicLong start = new AtomicLong();
        AtomicLong end = new AtomicLong();
        if(takes){
            start.set(System.nanoTime());
            try {
                buffer.put(portion);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            end.set(System.nanoTime());
            System.out.println(end.get() - start.get()+ " T");
        }
        else{
            start.set(System.nanoTime());
            try {
                buffer.take(portion);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            end.set(System.nanoTime());
            System.out.println(end.get() - start.get() + " P");
        }
    }
}
