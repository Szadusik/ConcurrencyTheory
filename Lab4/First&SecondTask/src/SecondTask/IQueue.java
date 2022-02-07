package SecondTask;

public interface IQueue {

    //Put into buffer
    void put(int n) throws InterruptedException;

    //Take from buffer
    void take(int n) throws InterruptedException;

}
