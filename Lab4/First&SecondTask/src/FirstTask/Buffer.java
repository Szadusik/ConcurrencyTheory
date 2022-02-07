package FirstTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Buffer {

    final private List<Process> clients = new ArrayList<Process>();
    final private ArrayList<Integer> buffer = new ArrayList<Integer>();
    final public int m;

    public Buffer(int size, int m) {
        int i;
        this.m = m;
        for(i = 0; i < size; i++)
            buffer.add(-1);

        clients.add(new Process(this, 0,0));

        for(i = 0; i < m; i++)
            clients.add(new Process(this, -1 - i,i+1));

        clients.add(new Process(this, -clients.size(),m));

        for(i = 0; i < clients.size() - 1; i++) {
            Semaphore sem = new Semaphore(0);
            clients.get(i).attachTail(sem);
            clients.get(i + 1).attachHead(sem);
        }

        Semaphore sem = new Semaphore(1);
        clients.get(clients.size() - 1).attachTail(sem);
        clients.get(0).attachHead(sem);
    }

    public int getBufferSize() {
        return buffer.size();
    }

    public void consume(int index) {
        if(index >= 0)
            buffer.set(index, -1);
    }

    public void proceed(int index) {
        if(index >= 0)
            buffer.set(index, buffer.get(index) + 1);
    }

    public void start() throws InterruptedException {
        for(Process wanderer : clients) {
            forever thread = new forever(wanderer);
            thread.start();
        }
        for(;;) {
            String res = "Buffer : ";
            for(Integer val : buffer) {
                res += val.toString() + " ";
            }
            System.out.println(res);
            sleep(200);
        }
    }
}
