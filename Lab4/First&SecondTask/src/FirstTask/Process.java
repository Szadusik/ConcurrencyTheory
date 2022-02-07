package FirstTask;

import java.util.concurrent.Semaphore;
public class Process {
    private final Buffer buffer;
    private final Semaphore[] semaphores = new Semaphore[2];
    private int ID;
    private int currPosition = -1;
    
    public Process(Buffer buffer, int current, int ID) {
        this.buffer = buffer;
        currPosition = current;
        this.ID = ID;
    }

    public void attachTail(Semaphore sem) {
        semaphores[0] = sem;
    }

    public void attachHead(Semaphore sem) {
        semaphores[1] = sem;
    }

    public void move() throws InterruptedException {
        semaphores[1].acquire();
        semaphores[0].release();

        currPosition++;
        currPosition %= buffer.getBufferSize();
        switch (this.ID){
            case 0:
                buffer.proceed(currPosition);
                break;
            default:
                if(this.ID == buffer.m)
                    buffer.consume(currPosition);
                else
                    buffer.proceed(currPosition);
                break;
        }
    }
}
